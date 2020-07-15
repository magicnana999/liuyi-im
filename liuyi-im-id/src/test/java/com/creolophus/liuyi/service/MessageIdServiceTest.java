package com.creolophus.liuyi.service;

import com.alibaba.fastjson.JSON;
import com.creolophus.liuyi.test.BaseTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author magicnana
 * @date 2020/6/10 下午6:18
 */
public class MessageIdServiceTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(MessageIdServiceTest.class);

    @Resource
    public MessageIdService messageIdService;

    @Test
    public void createSectionUserIfNotExistTest(){
        messageIdService.createSectionUserIfNotExist(1L);
        messageIdService.createSectionUserIfNotExist(2L);
        messageIdService.createSectionUserIfNotExist(3L);
        messageIdService.createSectionUserIfNotExist(4L);
        messageIdService.createSectionUserIfNotExist(5L);
        messageIdService.createSectionUserIfNotExist(6L);
        messageIdService.createSectionUserIfNotExist(7L);
        messageIdService.createSectionUserIfNotExist(8L);
        messageIdService.createSectionUserIfNotExist(9L);
        messageIdService.createSectionUserIfNotExist(10L);
        messageIdService.createSectionUserIfNotExist(11L);
        messageIdService.createSectionUserIfNotExist(12L);
    }

    @Test
    public void nextIdTest(){
        System.out.println(messageIdService.nextId((long)101));
        System.out.println(messageIdService.nextId((long)101));
        System.out.println(messageIdService.nextId((long)101));
        System.out.println(messageIdService.nextId((long)101));
        System.out.println(messageIdService.nextId((long)101));
        System.out.println(messageIdService.nextId((long)101));
        System.out.println(messageIdService.nextId((long)101));
        System.out.println(messageIdService.nextId((long)101));
        System.out.println(messageIdService.nextId((long)101));
        System.out.println(messageIdService.nextId((long)101));
        System.out.println(messageIdService.nextId((long)101));

        System.out.println(messageIdService.nextId((long)102));
        System.out.println(messageIdService.nextId((long)102));
        System.out.println(messageIdService.nextId((long)102));
        System.out.println(messageIdService.nextId((long)102));
        System.out.println(messageIdService.nextId((long)102));

        System.out.println(messageIdService.nextId((long)103));
        System.out.println(messageIdService.nextId((long)103));
        System.out.println(messageIdService.nextId((long)103));
        System.out.println(messageIdService.nextId((long)103));
        System.out.println(messageIdService.nextId((long)103));
        System.out.println(messageIdService.nextId((long)103));
    }

    @Test
    public void nextIdTest2(){
        System.out.println(messageIdService.nextId((long)104));
        System.out.println(messageIdService.nextId((long)105));
        System.out.println(messageIdService.nextId((long)106));
        System.out.println(messageIdService.nextId((long)107));
        System.out.println(messageIdService.nextId((long)108));
        System.out.println(messageIdService.nextId((long)109));
        System.out.println(messageIdService.nextId((long)110));
        System.out.println(messageIdService.nextId((long)111));
    }

}