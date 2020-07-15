import com.creolophus.liuyi.common.entity.Message;
import com.creolophus.liuyi.common.id.ObjectID;
import com.creolophus.liuyi.common.util.JUnitPrint;
import com.creolophus.liuyi.io.LoginInput;
import com.creolophus.liuyi.io.SendMessageIn;
import com.creolophus.liuyi.netty.protocol.Command;
import com.creolophus.liuyi.netty.protocol.CommandType;
import com.creolophus.liuyi.netty.serializer.FastJSONSerializer;
import org.junit.Test;

/**
 * @author magicnana
 * @date 2020/2/20 上午11:41
 */
public class TestCommand {

    private static final long 张无忌 = 11;
    private static final long 赵敏 = 12;
    private static final long 周芷若 = 13;
    private static final long 小昭 = 14;

    private static final String 老张 = "e912782f1ee0612220c17214aaeb2892";
    private static final String 老赵 = "15907024f9ecb4b4a5f088f94d25ce26";
    private static final String 老周 = "7be9166d6072cbeec51af0b618896fe3";
    private static final String 老小 = "054449cf09cdc407f558e2d64d4df595";

    @Test
    public void requestConnect() {

        FastJSONSerializer fastJSONSerializer = new FastJSONSerializer();

        LoginInput client = new LoginInput();
        client.setDeviceLabel("iPhone 7 Plus");
        client.setSdkName("liuyi-im-sdk");
        client.setSdkVersion("1.0.0");


        //张无忌
        Command request1 = Command.newCommand(CommandType.CONNECT.getValue(), client);
        request1.setToken(老张);
        System.out.println(new String(fastJSONSerializer.encode(request1)));

        //赵敏
        client.setDeviceLabel("iPhone 8 Plus");
        Command request2 = Command.newCommand(CommandType.CONNECT.getValue(), client);
        request2.setToken(老赵);
        System.out.println(new String(fastJSONSerializer.encode(request2)));

        //周芷若
        Command request3 = Command.newCommand(CommandType.CONNECT.getValue(), client);
        request3.setToken(老周);
        System.out.println(new String(fastJSONSerializer.encode(request3)));

        //小昭
        Command request4 = Command.newCommand(CommandType.CONNECT.getValue(), client);
        client.setDeviceLabel("iPhone 7 Plus");
        request4.setToken(老小);
        System.out.println(new String(fastJSONSerializer.encode(request4)));


        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("Hello,ZhaoMin(1000)");
            m.setTargetId(赵敏);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老张);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("Hello,Zhangwuji(1001)");
            m.setTargetId(张无忌);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老赵);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("有钱吗?借点 (1002)");
            m.setTargetId(赵敏);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老张);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("在吗?(1003)");
            m.setTargetId(赵敏);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老张);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody(".....(1004)");
            m.setTargetId(赵敏);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老张);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("算了,我找别人吧. (1005)");
            m.setTargetId(赵敏);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老张);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("呃,刚没看见,好吧,正好我手头有点紧,我先去洗澡了. 撒拉黑~(1006)");
            m.setTargetId(张无忌);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老赵);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("刚那个谁跟我借钱,我没有啊 (1007)");
            m.setTargetId(赵敏);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老周);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("是呀,我也没有啊~(1008)");
            m.setTargetId(周芷若);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老赵);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("他怎么老没钱啊~(1009)");
            m.setTargetId(周芷若);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老赵);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("功夫有个毛用,能买包?能买车?(1010)");
            m.setTargetId(赵敏);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老周);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("老这样也不行啊,那个谁是不是挺有钱的?(1011)");
            m.setTargetId(周芷若);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老赵);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("嘿嘿~~(1012)");
            m.setTargetId(赵敏);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老周);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.GROUP.getValue());
            m.setMessageBody("人呢?(1013)");
            m.setTargetId(1000L);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老张);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.GROUP.getValue());
            m.setMessageBody("干哈?(1014)");
            m.setTargetId(1000L);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老赵);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.GROUP.getValue());
            m.setMessageBody("在呢 (1015)");
            m.setTargetId(1000L);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老周);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.GROUP.getValue());
            m.setMessageBody("啥事?(1016)");
            m.setTargetId(1000L);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老小);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.GROUP.getValue());
            m.setMessageBody("我去洗个澡,待会再聊(1017)");
            m.setTargetId(1000L);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老赵);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.GROUP.getValue());
            m.setMessageBody("下雨了,我先收个衣服 (1018)");
            m.setTargetId(1000L);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老周);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.GROUP.getValue());
            m.setMessageBody("等我楸个外卖(1019)");
            m.setTargetId(1000L);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老小);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageIn m = new SendMessageIn();
            m.setMessageType(Message.MessageType.GROUP.getValue());
            m.setMessageBody("借点钱啊,穷死啦(1020)");
            m.setTargetId(1000L);

            Command request = Command.newCommand(CommandType.SEND_MESSAGE.getValue(), m);
            request.setToken(老张);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }
    }

    @Test
    public void testObjectID(){
        ObjectID objectID = new ObjectID();
        JUnitPrint.info(objectID.nextId().length()+"");
    }

}
