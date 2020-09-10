package com.creolophus.im.internal;

/**
 * @author magicnana
 * @date 2020/9/9 6:11 PM
 */
public class MessageImage extends MessageBody{


    private String thumbnail;
    private String original;
    private String ext;

    public MessageImage(String thumbnail, String original, String ext) {
        this.thumbnail = thumbnail;
        this.original = original;
        this.ext = ext;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
