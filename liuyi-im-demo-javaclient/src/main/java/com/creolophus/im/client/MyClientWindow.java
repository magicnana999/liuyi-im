package com.creolophus.im.client;

import com.alibaba.fastjson.JSON;
import com.creolophus.im.coder.MessageCoderSelector;
import com.creolophus.im.netty.core.NettyClientChannelEventListener;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.UserTest;
import com.creolophus.im.sdk.ImClientFactory;
import com.creolophus.im.sdk.LiuyiImClient;
import com.creolophus.im.sdk.MessageReceiver;
import com.creolophus.im.type.PushMessageAck;
import com.creolophus.im.type.PushMessageMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.SocketAddress;
import java.util.function.BiConsumer;

/**
 * @author magicnana
 * @date 2020/7/30 下午5:44
 */
public class MyClientWindow extends JFrame implements NettyClientChannelEventListener, MessageReceiver {

    private static final long serialVersionUID = 1L;
    private static UserTest.狗男女 currentUser;
    private JPanel contentPane;
    private JTextArea txt;
    //    private JTextField txtip;
    private JTextField txtSend;

    private JComboBox<UserTest.狗男女> currentUserBox;


    private LiuyiImClient liuyiImClient;

    private MessageCoderSelector messageCoderSelector = MessageCoderSelector.PROTOBUF;


    public MyClientWindow() {
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        txt = new JTextArea();
        txt.setText("准备...");

        currentUserBox = new JComboBox<>();//创建一个下拉列表框c1
        currentUserBox.addItem(UserTest.张无忌);
        currentUserBox.addItem(UserTest.赵敏);       // 创建4个下拉选项
        currentUserBox.addItem(UserTest.周芷若);
        currentUserBox.addItem(UserTest.小昭);


        liuyiImClient = ImClientFactory.getNettyClient(MyClientWindow.this, MyClientWindow.this, messageCoderSelector);
        liuyiImClient.start();


//        txtip = new JTextField();
//        txtip.setText("120.254.12.102");
//        txtip.setColumns(10);

        JButton btnConnect = new JButton("登录");
        btnConnect.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                ConnectionManager.getChatManager().connect(txtip.getText());
                currentUser = (UserTest.狗男女) currentUserBox.getSelectedItem();

//                liuyiImClient = ImClientFactory.getSocketImClient(new Configration(),MyClientWindow.this);
                liuyiImClient.login(currentUser.token, new BiConsumer<Command, Command>() {
                    @Override
                    public void accept(Command msg, Command ack) {
                        System.out.println("登录成功");
                    }
                });

                btnConnect.setText("点击断开连接,左侧变成消息接收人");

                btnConnect.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        liuyiImClient.close();
                        MyClientWindow.this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        System.exit(0);
                    }
                });

                btnConnect.removeMouseListener(this);


            }
        });


        txtSend = new JTextField();
        txtSend.setText("hello");
        txtSend.setColumns(10);

        JButton btnSend = new JButton("send");
        btnSend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                ConnectionManager.getChatManager().send(txtSend.getText());
                sendMessage();
                txtSend.setText("");
            }
        });
        GroupLayout gl_contentPane = new GroupLayout(contentPane);

        gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                  .addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
                                                          .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                                                            .addGroup(gl_contentPane.createSequentialGroup()
                                                                                              .addComponent(txtSend, GroupLayout.DEFAULT_SIZE, 325,
                                                                                                            Short.MAX_VALUE)
                                                                                              .addPreferredGap(ComponentPlacement.RELATED)
                                                                                              .addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 109,
                                                                                                            GroupLayout.PREFERRED_SIZE))
                                                                            .addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
                                                                                    .addComponent(currentUserBox, GroupLayout.PREFERRED_SIZE, 294,
                                                                                                  GroupLayout.PREFERRED_SIZE)
                                                                                    .addPreferredGap(ComponentPlacement.RELATED)
                                                                                    .addComponent(btnConnect, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                                                                            .addComponent(txt, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
                                                          .addContainerGap()));

        gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                                .addGroup(gl_contentPane.createSequentialGroup()
                                                                  .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                                                                    .addComponent(currentUserBox, GroupLayout.PREFERRED_SIZE,
                                                                                                  GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                    .addComponent(btnConnect))
                                                                  .addPreferredGap(ComponentPlacement.RELATED)
                                                                  .addComponent(txt, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                                                                  .addPreferredGap(ComponentPlacement.RELATED)
                                                                  .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                                                                    .addComponent(btnSend)
                                                                                    .addComponent(txtSend, GroupLayout.PREFERRED_SIZE,
                                                                                                  GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))));

        contentPane.setLayout(gl_contentPane);
    }

    /* 客户端发送的内容添加到中间的txt控件中 */
    public void appendText(String in) {
        txt.append("\n" + in);
        System.out.println("画 UI" + in);
    }

    @Override
    public void onClose(ChannelHandlerContext ctx, ChannelPromise promise) {

    }

    @Override
    public void onConnect(
            ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {

    }

    @Override
    public void onDisconnect(ChannelHandlerContext ctx, ChannelPromise promise) {

    }

    @Override
    public void onExceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

    }

    @Override
    public void onUserEventTriggered(ChannelHandlerContext ctx, Object evt) {

    }

    @Override
    public PushMessageAck receivePushMessage(Command command) {
        System.out.println("收到推送" + JSON.toJSONString(command));
        PushMessageMsg pushMessageMsg = messageCoderSelector.getMessageCoder().decode(command.getBody(), PushMessageMsg.class);
        UserTest.狗男女 target = UserTest.valueOf(pushMessageMsg.getSenderId());
        appendText(target.toString() + ": " + pushMessageMsg.getMessageBody());
        PushMessageAck pushMessageAck = new PushMessageAck();
        pushMessageAck.setGroupId(pushMessageMsg.getGroupId());
        pushMessageAck.setMessageId(pushMessageMsg.getMessageId());
        pushMessageAck.setReceiverId(pushMessageMsg.getReceiverId());
        pushMessageAck.setSenderId(pushMessageMsg.getSenderId());
        return pushMessageAck;
    }


    public void sendMessage() {
        UserTest.狗男女 target = (UserTest.狗男女) currentUserBox.getSelectedItem();

        liuyiImClient.sendMessage(1, txtSend.getText(), target.userId, new BiConsumer<Command, Command>() {
            @Override
            public void accept(Command command, Command command2) {
                System.out.println("发送消息成功");
            }
        });
        appendText("我: " + txtSend.getText());
    }

}
