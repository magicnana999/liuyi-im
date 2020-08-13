package com.creolophus.im.common.swagger;

import com.creolophus.liuyi.common.api.GlobalSetting;
import com.creolophus.liuyi.common.swagger.SwaggerDocket;
import springfox.documentation.service.Parameter;

/**
 * @author magicnana
 * @date 2019/12/20 下午1:48
 */
public class FrontSwaggerDocket extends SwaggerDocket {

    public static Parameter token() {
        return header(GlobalSetting.HEADER_TOKEN_KEY, true, GlobalSetting.HEADER_TOKEN_PRE + " be7fcee88904a31d40064240ac13d931");
    }

}
