package com.creolophus.im.boot;

import com.creolophus.im.service.MessageIdService;
import com.creolophus.liuyi.common.api.WebStart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

/**
 * 朝辞白帝彩云间 千行代码一日还
 * 两岸领导啼不住 地铁已到回龙观
 *
 * @author magicnana
 * @date 2019/7/4 下午1:46
 */
@SpringBootApplication(scanBasePackages = "com.creolophus")
@EnableFeignClients(basePackages = "com.creolophus.im.feign")
@EnableSwagger2
public class Start extends WebStart {

    private static final Logger logger = LoggerFactory.getLogger(Start.class);

    @Resource
    private MessageIdService messageIdService;

    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }

//    @Entry
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent event) {
//        super.onApplicationEvent(event);
//        messageIdService.getSectionId();
//    }
}