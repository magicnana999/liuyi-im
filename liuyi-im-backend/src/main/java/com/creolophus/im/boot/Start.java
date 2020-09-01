package com.creolophus.im.boot;

import com.creolophus.im.common.api.LiuYiApiContextValidator;
import com.creolophus.im.service.MessageService;
import com.creolophus.liuyi.common.api.ApiContextValidator;
import com.creolophus.liuyi.common.api.WebStart;
import com.creolophus.liuyi.common.cloud.CustomRequestInterceptor;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @date 2018/11/9 下午4:49
 */

@SpringBootApplication(scanBasePackages = "com.creolophus")
@EnableFeignClients(basePackages = "com.creolophus.im.feign")
@EnableSwagger2
@EnableScheduling
@EnableAsync
public class Start extends WebStart {

    private static final Logger logger = LoggerFactory.getLogger(Start.class);

    @Override
    @Bean
    public ApiContextValidator apiContextValidator() {
        return new LiuYiApiContextValidator();
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        super.onApplicationEvent(event);
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        final MessageService messageService = applicationContext.getBean(MessageService.class);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                messageService.shutdown();
            }
        });
    }

    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }

    @Bean
    public RequestInterceptor requestInterceptor(ApiContextValidator apiContextValidator) {
        LiuYiApiContextValidator liuYiApiContextValidator = (LiuYiApiContextValidator) apiContextValidator;
        return new CustomRequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String appKey = liuYiApiContextValidator.getAppKeyByContext();
                template.header(LiuYiApiContextValidator.HEADER_APP_KEY, appKey);
                if(logger.isDebugEnabled()) {
                    logger.debug("feign header [{}:{}]", LiuYiApiContextValidator.HEADER_APP_KEY, appKey);
                }
            }
        };
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}