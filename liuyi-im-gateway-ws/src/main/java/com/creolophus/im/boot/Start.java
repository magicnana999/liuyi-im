package com.creolophus.im.boot;

import com.creolophus.im.common.api.LiuYiApiContextValidator;
import com.creolophus.im.netty.serializer.CommandSerializer;
import com.creolophus.im.netty.serializer.FastJSONSerializer;
import com.creolophus.im.scheduler.HeartbeatSchedule;
import com.creolophus.im.websocket.WebsocketServerInstance;
import com.creolophus.liuyi.common.api.WebStart;
import com.creolophus.liuyi.common.cloud.CustomRequestInterceptor;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
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

    @Bean
    public CommandSerializer commandSerializer() {
        return new FastJSONSerializer();
    }

    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        super.onApplicationEvent(event);
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();

        WebsocketServerInstance.setConfigurableApplicationContext(applicationContext);

        final HeartbeatSchedule heartbeatSchedule = applicationContext.getBean(HeartbeatSchedule.class);
        heartbeatSchedule.heartbeat();

    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        final String appKey = "websocket_gateway";
        return new CustomRequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                template.header(LiuYiApiContextValidator.HEADER_APP_KEY, appKey);
                if(logger.isDebugEnabled()) {
                    logger.debug("feign header [{}:{}]", LiuYiApiContextValidator.HEADER_APP_KEY, appKey);
                }
            }
        };
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public WebSocketContextValidator webSocketContextValidator() {
        return new WebSocketContextValidator();
    }


}