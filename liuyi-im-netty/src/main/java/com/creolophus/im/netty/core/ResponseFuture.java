/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.creolophus.im.netty.core;

import com.creolophus.im.protocol.Command;
import io.netty.channel.Channel;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public class ResponseFuture {
    private final String commandSeq;
    private final Channel processChannel;
    private final long timeoutMillis;
    private final BiConsumer<Command/*request*/, Command/*ack*/> consumer;
    private final long beginTimestamp = System.currentTimeMillis();
    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    private final SemaphoreReleaseOnlyOnce once;

    private volatile Command request;
    private volatile Command response;
    private volatile boolean sendOk = true;
    private volatile Throwable cause;

    public ResponseFuture(
            Channel channel,
            String commandSeq,
            long timeoutMillis,
            BiConsumer<Command/*request*/, Command/*ack*/> consumer,
            SemaphoreReleaseOnlyOnce once,
            Command request) {
        this.commandSeq = commandSeq;
        this.processChannel = channel;
        this.timeoutMillis = timeoutMillis;
        this.consumer = consumer;
        this.once = once;
        this.request = request;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public String getCommandSeq() {
        return commandSeq;
    }

    public BiConsumer<Command, Command> getConsumer() {
        return consumer;
    }

    public Channel getProcessChannel() {
        return processChannel;
    }

    public boolean isSendOk() {
        return sendOk;
    }

    public void setSendOk(boolean sendOk) {
        this.sendOk = sendOk;
    }

    public boolean isTimeout() {
        long diff = System.currentTimeMillis() - this.beginTimestamp;
        return diff > this.timeoutMillis;
    }

    public void putResponse(final Command response) {
        this.response = response;
        if(this.consumer != null) {
            this.consumer.accept(request, response);
        } else {
            this.countDownLatch.countDown();
        }
    }

    public void release() {
        if(this.once != null) {
            this.once.release();
        }
    }

    public Command waitResponse(final long timeoutMillis) throws InterruptedException {
        this.countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
        return this.response;
    }
}
