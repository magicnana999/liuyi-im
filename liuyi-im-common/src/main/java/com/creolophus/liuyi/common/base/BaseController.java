package com.creolophus.liuyi.common.base;

import com.creolophus.liuyi.common.api.ApiContext;

/**
 * @author magicnana
 * @date 2019/12/20 下午2:47
 */
public class BaseController extends AbstractController {

    protected String currentToken() {
        return ApiContext.getContext().getToken();
    }

    protected Long currentUserId(){ return ApiContext.getContext().getUserId();}

}
