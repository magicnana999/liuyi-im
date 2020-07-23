package com.creolophus.im.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author magicnana
 * @date 2020/6/19 下午4:23
 */
@Service
public class GlobalAtomicGenerator {

    private static final AtomicLong messageId = new AtomicLong(1000);

    public Long nextId() {
        return messageId.getAndIncrement();
    }
}
