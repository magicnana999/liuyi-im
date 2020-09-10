package com.creolophus.im.internal;

/**
 * @author magicnana
 * @date 2020/9/9 6:11 PM
 */
public class MessageVideo extends MessageBody{
    /**
     * 秒
     */
    private Integer length;
    private String cover;
    private String title;
    private String source;

    public MessageVideo(Integer length, String cover, String title, String source) {
        this.length = length;
        this.cover = cover;
        this.title = title;
        this.source = source;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
