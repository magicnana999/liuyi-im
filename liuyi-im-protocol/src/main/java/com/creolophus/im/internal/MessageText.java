package com.creolophus.im.internal;

/**
 * @author magicnana
 * @date 2020/9/9 6:10 PM
 */
public class MessageText extends MessageBody{


    private String content;

    public MessageText(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static void main(String[] args){
        MessageText messageText = new MessageText("hesdfsdf");
        System.out.println(messageText.toJson());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"content\":\"").append(content).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
