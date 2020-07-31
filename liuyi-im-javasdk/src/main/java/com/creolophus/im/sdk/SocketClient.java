package com.creolophus.im.sdk;

import com.alibaba.fastjson.JSON;
import com.creolophus.im.protocol.Command;
import sun.swing.StringUIClientPropertyKey;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.*;
import java.util.function.BiConsumer;

/**
 * @author magicnana
 * @date 2020/7/29 下午5:41
 */
public class SocketClient {

    private static final int tSize = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService es = new ThreadPoolExecutor(tSize, tSize, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    private static final ConcurrentHashMap<String, AckProcessor> requestTable = new ConcurrentHashMap<>(128);
    private static final LinkedBlockingQueue<Command> commandQueue = new LinkedBlockingQueue<>(128);

    private volatile int writeSize = 0;
    private volatile int readSize = 0;

    protected Configration configration;

    private SocketChannel channel;
    private Selector selector;

    private PushProcessor pushProcessor;

    private boolean isConnect = false;

    protected boolean isConnect(){
        return isConnect;
    }

    protected void close() throws IOException {
        channel.close();
    }

    SocketClient(Configration configration,PushProcessor pushProcessor) {
        try {
            this.configration = configration;
            this.pushProcessor = pushProcessor;

            channel = SocketChannel.open();
            channel.configureBlocking(false);
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_CONNECT);
            channel.connect(new InetSocketAddress(configration.getIp(), configration.getPort()));

            selector.select();
            SelectionKey key = selector.selectedKeys().iterator().next();

            if(key.isConnectable()) {
                SocketChannel client = (SocketChannel) key.channel();
                if(client.isConnectionPending()) {
                    client.finishConnect();
                }
                client.configureBlocking(false);
            }

            isConnect = true;
            accept();

        } catch (Throwable e) {
            throw new RuntimeException("无法启动 SocketClient", e);
        }

    }

    /**
     * 这只是一个 demo,不要纠结:我写的很low
     */
    private void accept(){
        Thread thread = new Thread(() -> {
            while(true){
                try{
                    selector.select();
                    Iterator<SelectionKey> set = selector.selectedKeys().iterator();
                    while(set.hasNext()){
                        SelectionKey key = set.next();
                        if(key.isWritable()){
                            scheduleWriteTask(key);
                        }else{
                            if(key.isReadable()){

                                String str = read(key);
                                if(str!=null && str.length()>0){
                                    if(str.indexOf("}{")>0){
                                        str = str.replaceAll("\\}\\{", "}magic,nana{");
                                        String[] cs = str.split("magic,nana");
                                        for(String s:cs){
                                            try{
                                                Command command = JSON.parseObject(s,Command.class);
                                                if(command!=null){
                                                    scheduleReadTask(command);
                                                }
                                            }catch (Throwable e){
                                                e.printStackTrace();
                                            }

                                        }
                                    }else{
                                        Command command = JSON.parseObject(str,Command.class);
                                        if(command!=null){
                                            scheduleReadTask(command);
                                        }
                                    }
                                    selector.wakeup();
                                    channel.register(selector, SelectionKey.OP_READ);
                                }
                            }
                        }
                    }
                }catch (Throwable e){
                    e.printStackTrace();
                }
            }
        },"SocketClient-Acceptor");
        thread.start();
    }

    private String read(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
        int count = client.read(byteBuffer);
        if(count > 0) {
            String str = new String(byteBuffer.array(), 0, count);
            System.out.println("receive "+str);
            return str;
        } else {
            return null;
        }
    }

    private void scheduleReadTask(Command command) {
        es.submit(() -> {
                try {
                    System.out.println("读线程");
                    if(command.getHeader().getCode() == 0) {
                        Command ack = pushProcessor.receivePush(command);
                        SocketClient.this.sendAck(ack);
                    } else {
                        AckProcessor processor = requestTable.get(command.getHeader().getSeq());
                        if(processor != null) {
                            processor.ack(command);
                            requestTable.remove(command.getHeader().getSeq());
                        }
                    }
                } catch (IOException e) {
                    System.err.println("无法读取消息 "+e.getMessage());
                } catch (InterruptedException e) {
                    System.err.println("InterruptedException "+e.getMessage());
                }
        });
    }

    private void scheduleWriteTask(SelectionKey key) {
        writeSize++;

        if(writeSize>1){
            return;
        }



        es.submit(() -> {
            while(true){
                try {
                    System.out.println("写线程");
                    Command command = commandQueue.take();
                    write(key, command);
                    selector.wakeup();
                    channel.register(selector, SelectionKey.OP_READ);
                } catch (InterruptedException e) {
                    System.err.println("InterruptedException "+e.getMessage());
                } catch (ClosedChannelException e) {
                    System.err.println("无法写入消息 "+e.getMessage());
                } catch (IOException e) {
                    System.err.println("无法写入消息 "+e.getMessage());
                }
            }
        });
    }

    private void sendAck(Command ack) throws InterruptedException, ClosedChannelException {
        commandQueue.put(ack);
        selector.wakeup();
        channel.register(selector,SelectionKey.OP_WRITE );

    }

    protected void sendMessage(Command command, BiConsumer<Command, Command> consumer) throws InterruptedException, ClosedChannelException {
        commandQueue.put(command);
        requestTable.putIfAbsent(command.getHeader().getSeq(), new AckProcessor(command, consumer));
        selector.wakeup();
        channel.register(selector,SelectionKey.OP_WRITE );

    }

    private void write(SelectionKey key, Command command) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        String str = JSON.toJSONString(command);
        System.out.println("send "+str);
        channel.write(ByteBuffer.wrap(str.getBytes()));
    }


    public static void main(String[] args){
        String s = "{{},{}}{{},{}}";
        System.out.println(s.replaceAll("\\}\\{", "},{"));
    }

}
