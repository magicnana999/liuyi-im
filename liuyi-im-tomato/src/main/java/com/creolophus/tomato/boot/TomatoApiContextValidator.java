package com.creolophus.tomato.boot;

import com.creolophus.liuyi.common.api.ApiContext;
import com.creolophus.liuyi.common.api.ApiContextValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 暂未使用
 * @author magicnana
 * @date 2019/12/20 下午1:51
 */
public class TomatoApiContextValidator extends ApiContextValidator {


    public static final String HEADER_APP_KEY = "X-Liuyi-App-Key";
    public static final String IM_TOKEN = "IMTOKEN";
    public static final String IM_ID = "IMID";


    private static final Logger logger = LoggerFactory.getLogger(TomatoApiContextValidator.class);

    public Long getImId() {
        return ApiContext.getContext().getExt(IM_ID);
    }

    public void setImId(Long imId) {
        ApiContext.getContext().setExt(IM_ID, imId);
    }

    public String getImToken() {
        return ApiContext.getContext().getExt(IM_TOKEN);
    }

    public void setImToken(String imToken) {
        ApiContext.getContext().setExt(IM_TOKEN, imToken);
    }
}
