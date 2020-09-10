package com.creolophus.im.internal;

/**
 * @author magicnana
 * @date 2020/9/9 6:11 PM
 */
public class MessageUrl extends MessageBody{

    private String content;

    public MessageUrl(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"content\":\"").append(content).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
