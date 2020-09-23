package com.creolophus.tomato.controller;

import com.creolophus.liuyi.common.api.ApiResult;
import com.creolophus.tomato.base.BaseController;
import com.creolophus.tomato.service.AuthService;
import com.creolophus.tomato.vo.UserToken;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/6/4 上午12:17
 */

@Validated
@RestController
@RequestMapping(value = "/liuyiim/tomato/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthController extends BaseController {


    //    @Autowired
//    private WebApplicationContext applicationContext;
    @Resource
    private AuthService authService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult login(
            @RequestParam("phone") String phone, @RequestParam("code") String code) {
        UserToken userToken = authService.login(phone, code);
        return new ApiResult(userToken);
    }

    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public ApiResult pushCode(
            @RequestParam("phone") String phone) {
        authService.pushRegCode(phone);
        return new ApiResult();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResult register(
            @RequestParam("phone") String phone,
            @RequestParam("name") String name,
            @RequestParam("portrait") String portrait,
            @RequestParam("password") String password) {
        UserToken userToken = authService.register(phone, name, portrait, password);
        return new ApiResult(userToken);
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ApiResult showCode(
            @RequestParam("phone") String phone) {
        String code = authService.getRegCode(phone);
        return new ApiResult(code);
    }

//    @PostConstruct
//    public void urls() {
//        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
//        // 获取url与类和方法的对应信息
//        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
//        for (RequestMappingInfo info : map.keySet()) {
//            // 获取url的Set集合，一个方法可能对应多个url
//            Set<String> patterns = info.getPatternsCondition().getPatterns();
//            for (String url : patterns) {
//                // 把结果存入静态变量中程序运行一次次方法之后就不用再次请求次方法
//                System.out.println(url);
//            }
//        }
//
//    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public ApiResult verifyToken(
            @RequestParam("token") String token) {
        UserToken user = authService.verifyToken(token);
        return new ApiResult(user);
    }


}
