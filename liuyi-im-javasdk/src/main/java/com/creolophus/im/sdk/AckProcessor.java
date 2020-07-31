package com.creolophus.im.sdk;

import java.util.function.BiConsumer;

/**
 * @author magicnana
 * @date 2020/7/29 下午6:23
 */
public class AckProcessor<T> {

    private T request;
    private BiConsumer<T,T> biConsumer;

    public AckProcessor(T request, BiConsumer<T,T> biConsumer){
        this.request = request;
        this.biConsumer = biConsumer;
    }

    public T getRequest() {
        return request;
    }

    public void ack(T ack){
        biConsumer.accept(this.getRequest(),ack);
    }
}
