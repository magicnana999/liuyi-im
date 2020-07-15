package com.creolophus.liuyi.common.api;

import com.creolophus.liuyi.common.exception.UnauthorizedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;

/**
 * 暂未使用
 *
 * @author magicnana
 * @date 2019/12/20 下午1:51
 */
public class LiuYiApiContextValidator extends ApiContextValidator {


    public static final String HEADER_APP_KEY = "X-Liuyi-App-Key";
    public static final String INTERNAL_APP_KEY = "appKey";


    private static final Logger logger = LoggerFactory.getLogger(LiuYiApiContextValidator.class);

    public String getAppKeyByContext() {
        return ApiContext.getContext().getExt(INTERNAL_APP_KEY);
    }

    public void setAppKeyByContext(String appKey) {
        ApiContext.getContext().setExt(INTERNAL_APP_KEY, appKey);
    }

    public String getAppKeyByHeader(HttpServletRequest request) {
        return request.getHeader(HEADER_APP_KEY);
    }

    public String getExtByContext() {
        String appKey = getAppKeyByContext();
        appKey = StringUtils.isBlank(appKey) ? "-" : appKey;
        return appKey;
    }

    public void initAppKeyIfExist(HttpServletRequest request) {
        initAppKey_(request, false);
    }

    private void initAppKey_(HttpServletRequest request, boolean throwsExceptionIfNull) {
        String appKey = getAppKeyByHeader(request);
        if(StringUtils.isNotBlank(appKey)) {
            setAppKeyByContext(appKey);
            setAppKeyByMdc(appKey);
        } else {
            if(throwsExceptionIfNull) {
                throw new UnauthorizedException("missing require header AppKey");
            }
        }
    }

    public void setAppKeyByMdc(String appKey) {
        MDC.put(GlobalSetting.MDC_EXT, getExtByContext());
    }

    public void validateAppKey(HttpServletRequest request) {
        initAppKey_(request, true);
    }

    @Override
    public void initContext(HttpServletRequest request) {
        super.initContext(request);
        if(request!=null){
            initAppKeyIfExist(request);
        }
    }
}
