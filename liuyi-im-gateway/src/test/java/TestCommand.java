import com.creolophus.im.coder.GsonCoder;
import com.creolophus.im.coder.MessageCoder;
import com.creolophus.im.protocol.Command;
import com.creolophus.im.protocol.CommandType;
import com.creolophus.im.protocol.UserTest;
import com.creolophus.im.type.LoginMsg;
import com.creolophus.im.type.MessageType;
import com.creolophus.im.type.SendMessageMsg;
import com.creolophus.liuyi.common.id.ObjectID;
import com.creolophus.liuyi.common.util.JUnitPrint;
import org.junit.Test;

/**
 * @author magicnana
 * @date 2020/2/20 上午11:41
 */
public class TestCommand {

    @Test
    public void commands() {

        MessageCoder commandEncoder = new GsonCoder();

        LoginMsg client = new LoginMsg();
        client.setDeviceLabel("iPhone 7 Plus");
        client.setSdkName("liuyi-im-nettysdk");
        client.setSdkVersion("1.0.0");

        {
            //张无忌
            Command request = Command.newMsg(CommandType.LOGIN.value(), client).withToken(UserTest.张无忌.token);
            String str = new String(commandEncoder.encode(request).toString());
            System.out.println(str.getBytes().length + " " + str);
        }

        {
            //赵敏
            Command request = Command.newMsg(CommandType.LOGIN.value(), client).withToken(UserTest.赵敏.token);
            System.out.println(new String(commandEncoder.encode(request).toString()));
        }

        {
            //周芷若
            Command request = Command.newMsg(CommandType.LOGIN.value(), client).withToken(UserTest.周芷若.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            //小昭
            Command request = Command.newMsg(CommandType.LOGIN.value(), client).withToken(UserTest.小昭.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }


        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.SINGLE.value());
            m.setMessageBody("Hello,ZhaoMin(1000)");
            m.setTargetId(UserTest.赵敏.userId);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.张无忌.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.SINGLE.value());
            m.setMessageBody("Hello,Zhangwuji(1001)");
            m.setTargetId(UserTest.张无忌.userId);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.赵敏.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.SINGLE.value());
            m.setMessageBody("有钱吗?借点 (1002)");
            m.setTargetId(UserTest.赵敏.userId);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.张无忌.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.SINGLE.value());
            m.setMessageBody("在吗?(1003)");
            m.setTargetId(UserTest.赵敏.userId);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.张无忌.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.SINGLE.value());
            m.setMessageBody(".....(1004)");
            m.setTargetId(UserTest.赵敏.userId);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.张无忌.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.SINGLE.value());
            m.setMessageBody("算了,我找别人吧. (1005)");
            m.setTargetId(UserTest.赵敏.userId);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.张无忌.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.SINGLE.value());
            m.setMessageBody("呃,刚没看见,好吧,正好我手头有点紧,我先去洗澡了. 撒拉黑~(1006)");
            m.setTargetId(UserTest.张无忌.userId);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.赵敏.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.SINGLE.value());
            m.setMessageBody("刚那个谁跟我借钱,我没有啊 (1007)");
            m.setTargetId(UserTest.赵敏.userId);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.周芷若.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.SINGLE.value());
            m.setMessageBody("是呀,我也没有啊~(1008)");
            m.setTargetId(UserTest.周芷若.userId);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.赵敏.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.SINGLE.value());
            m.setMessageBody("他怎么老没钱啊~(1009)");
            m.setTargetId(UserTest.周芷若.userId);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.赵敏.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.SINGLE.value());
            m.setMessageBody("功夫有个毛用,能买包?能买车?(1010)");
            m.setTargetId(UserTest.赵敏.userId);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m);
            m.setTargetId(UserTest.周芷若.userId);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.SINGLE.value());
            m.setMessageBody("老这样也不行啊,那个谁是不是挺有钱的?(1011)");
            m.setTargetId(UserTest.周芷若.userId);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.赵敏.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.SINGLE.value());
            m.setMessageBody("嘿嘿~~(1012)");
            m.setTargetId(UserTest.赵敏.userId);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.周芷若.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.GROUP.value());
            m.setMessageBody("人呢?(1013)");
            m.setTargetId(1000L);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.张无忌.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.GROUP.value());
            m.setMessageBody("干哈?(1014)");
            m.setTargetId(1000L);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.赵敏.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.GROUP.value());
            m.setMessageBody("在呢 (1015)");
            m.setTargetId(1000L);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.周芷若.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.GROUP.value());
            m.setMessageBody("啥事?(1016)");
            m.setTargetId(1000L);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.小昭.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.GROUP.value());
            m.setMessageBody("我去洗个澡,待会再聊(1017)");
            m.setTargetId(1000L);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.赵敏.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.GROUP.value());
            m.setMessageBody("下雨了,我先收个衣服 (1018)");
            m.setTargetId(1000L);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.周芷若.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.GROUP.value());
            m.setMessageBody("等我楸个外卖(1019)");
            m.setTargetId(1000L);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.小昭.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }

        {
            SendMessageMsg m = new SendMessageMsg();
            m.setMessageType(MessageType.GROUP.value());
            m.setMessageBody("借点钱啊,穷死啦(1020)");
            m.setTargetId(1000L);

            Command request = Command.newMsg(CommandType.SEND_MESSAGE.value(), m).withToken(UserTest.张无忌.token);
            System.out.println(new String(commandEncoder.encode(request).array()));
        }
    }

    @Test
    public void testObjectID() {
        ObjectID objectID = new ObjectID();
        JUnitPrint.info(objectID.nextId().length() + "");
    }

}
