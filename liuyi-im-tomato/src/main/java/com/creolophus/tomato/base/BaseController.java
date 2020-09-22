package com.creolophus.tomato.base;

import com.creolophus.liuyi.common.api.ApiContext;
import com.creolophus.liuyi.common.base.AbstractController;

/**
 * @author magicnana
 * @date 2019/12/20 下午2:47
 */
public class BaseController extends AbstractController {

    protected String token() {
        return ApiContext.getContext().getToken();
    }
}
