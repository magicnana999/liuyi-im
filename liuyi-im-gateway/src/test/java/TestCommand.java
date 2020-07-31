import com.creolophus.im.common.entity.Message;
import com.creolophus.im.protocol.*;
import com.creolophus.liuyi.common.id.ObjectID;
import com.creolophus.liuyi.common.util.JUnitPrint;
import com.creolophus.im.netty.serializer.FastJSONSerializer;
import org.junit.Test;

/**
 * @author magicnana
 * @date 2020/2/20 上午11:41
 */
public class TestCommand {

    @Test
    public void commands() {

        FastJSONSerializer fastJSONSerializer = new FastJSONSerializer();

        LoginInput client = new LoginInput();
        client.setDeviceLabel("iPhone 7 Plus");
        client.setSdkName("liuyi-im-sdk");
        client.setSdkVersion("1.0.0");

        {
            //张无忌
            Command request = Command.newRequest(CommandType.LOGIN.getValue(), client).withToken(UserTest.张无忌.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            //赵敏
            Command request = Command.newRequest(CommandType.LOGIN.getValue(), client).withToken(UserTest.赵敏.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            //周芷若
            Command request = Command.newRequest(CommandType.LOGIN.getValue(), client).withToken(UserTest.周芷若.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            //小昭
            Command request = Command.newRequest(CommandType.LOGIN.getValue(), client).withToken(UserTest.小昭.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }


        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("Hello,ZhaoMin(1000)");
            m.setTargetId(UserTest.赵敏.userId);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.张无忌.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("Hello,Zhangwuji(1001)");
            m.setTargetId(UserTest.张无忌.userId);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.赵敏.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("有钱吗?借点 (1002)");
            m.setTargetId(UserTest.赵敏.userId);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.张无忌.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("在吗?(1003)");
            m.setTargetId(UserTest.赵敏.userId);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.张无忌.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody(".....(1004)");
            m.setTargetId(UserTest.赵敏.userId);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.张无忌.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("算了,我找别人吧. (1005)");
            m.setTargetId(UserTest.赵敏.userId);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.张无忌.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("呃,刚没看见,好吧,正好我手头有点紧,我先去洗澡了. 撒拉黑~(1006)");
            m.setTargetId(UserTest.张无忌.userId);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.赵敏.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("刚那个谁跟我借钱,我没有啊 (1007)");
            m.setTargetId(UserTest.赵敏.userId);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.周芷若.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("是呀,我也没有啊~(1008)");
            m.setTargetId(UserTest.周芷若.userId);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.赵敏.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("他怎么老没钱啊~(1009)");
            m.setTargetId(UserTest.周芷若.userId);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.赵敏.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("功夫有个毛用,能买包?能买车?(1010)");
            m.setTargetId(UserTest.赵敏.userId);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m);
            m.setTargetId(UserTest.周芷若.userId);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("老这样也不行啊,那个谁是不是挺有钱的?(1011)");
            m.setTargetId(UserTest.周芷若.userId);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.赵敏.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.SINGLE.getValue());
            m.setMessageBody("嘿嘿~~(1012)");
            m.setTargetId(UserTest.赵敏.userId);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.周芷若.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.GROUP.getValue());
            m.setMessageBody("人呢?(1013)");
            m.setTargetId(1000L);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.张无忌.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.GROUP.getValue());
            m.setMessageBody("干哈?(1014)");
            m.setTargetId(1000L);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.赵敏.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.GROUP.getValue());
            m.setMessageBody("在呢 (1015)");
            m.setTargetId(1000L);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.周芷若.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.GROUP.getValue());
            m.setMessageBody("啥事?(1016)");
            m.setTargetId(1000L);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.小昭.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.GROUP.getValue());
            m.setMessageBody("我去洗个澡,待会再聊(1017)");
            m.setTargetId(1000L);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.赵敏.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.GROUP.getValue());
            m.setMessageBody("下雨了,我先收个衣服 (1018)");
            m.setTargetId(1000L);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.周芷若.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.GROUP.getValue());
            m.setMessageBody("等我楸个外卖(1019)");
            m.setTargetId(1000L);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.小昭.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }

        {
            SendMessageInput m = new SendMessageInput();
            m.setMessageType(Message.MessageType.GROUP.getValue());
            m.setMessageBody("借点钱啊,穷死啦(1020)");
            m.setTargetId(1000L);

            Command request = Command.newRequest(CommandType.SEND_MESSAGE.getValue(), m).withToken(UserTest.张无忌.token);
            System.out.println(new String(fastJSONSerializer.encode(request)));
        }
    }

    @Test
    public void testObjectID(){
        ObjectID objectID = new ObjectID();
        JUnitPrint.info(objectID.nextId().length()+"");
    }

}
