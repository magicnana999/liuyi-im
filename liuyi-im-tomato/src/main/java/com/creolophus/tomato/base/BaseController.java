package com.creolophus.tomato.base;

import com.creolophus.liuyi.common.api.ApiContext;
import com.creolophus.liuyi.common.base.AbstractController;
import com.creolophus.tomato.boot.TomatoApiContextValidator;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2019/12/20 下午2:47
 */
public class BaseController extends AbstractController {

    @Resource
    private TomatoApiContextValidator tomatoApiContextValidator;

    protected Long getImId() {
        return tomatoApiContextValidator.getImId();
    }

    protected String getImToken() {
        return tomatoApiContextValidator.getImToken();
    }

    protected String token() {
        return ApiContext.getContext().getToken();
    }
}
