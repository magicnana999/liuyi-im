package com.creolophus.tomato.boot;

import com.creolophus.liuyi.common.api.ApiContextValidator;
import com.creolophus.liuyi.common.api.ApiResult;
import com.creolophus.liuyi.common.api.WebStart;
import com.creolophus.liuyi.common.security.JwtAuthenticationTokenFilter;
import com.creolophus.liuyi.common.security.LiuyiWebSecurityConfigurerAdapter;
import com.creolophus.tomato.service.UserDetailService;
import com.fasterxml.classmate.types.ResolvedObjectType;
import feign.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2018/11/9 下午4:49
 */

@SpringBootApplication(scanBasePackages = "com.creolophus")
@EnableFeignClients(basePackages = "com.creolophus.tomato.feign")
@EnableSwagger2
@EnableScheduling
@EnableAsync
public class Start extends WebStart {

    private static final Logger logger = LoggerFactory.getLogger(Start.class);
    private static String appKey = "1d0d0423ce82ced4851c62c5724265e4";

    @Override
    @Bean
    @ConditionalOnMissingBean
    public ApiContextValidator apiContextValidator() {
        return new TomatoApiContextValidator();
    }

    @Bean
    @ConditionalOnBean(ApiInfo.class)
    public Docket docket(ApiInfo apiInfo) {

        List<Parameter> pars = new ArrayList<Parameter>();
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        pars.add(parameterBuilder.name("Authorization")
                         .description("认证")
                         .defaultValue("Bearer ")
                         .modelRef(new ModelRef("string"))
                         .parameterType("header")
                         .required(true)
                         .build());
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.creolophus.tomato"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .additionalModels(ResolvedObjectType.create(ApiResult.class, null, null, null));
    }

    @Bean
    public LiuyiWebSecurityConfigurerAdapter liuyiWebSecurityConfigurerAdapter(
            UserDetailService userDetailService,
            AccessDeniedHandler accessDeniedHandler,
            AuthenticationEntryPoint authenticationEntryPoint,
            JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter) {

        return new LiuyiWebSecurityConfigurerAdapter(userDetailService, accessDeniedHandler, authenticationEntryPoint, jwtAuthenticationTokenFilter) {
            @Override
            public void configure(WebSecurity web) {
                super.configure(web);
                web.ignoring().antMatchers("/tomato/server/auth/**");
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            template.header(TomatoApiContextValidator.HEADER_APP_KEY, appKey);
            if(logger.isDebugEnabled()) {
                logger.debug("feign header [{}:{}]", TomatoApiContextValidator.HEADER_APP_KEY, appKey);
            }
        };
    }

}