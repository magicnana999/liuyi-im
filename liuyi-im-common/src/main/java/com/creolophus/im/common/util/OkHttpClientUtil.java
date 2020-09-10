/**
 *
 */
package com.creolophus.im.common.util;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.commons.httpclient.DefaultOkHttpClientConnectionPoolFactory;
import org.springframework.cloud.commons.httpclient.DefaultOkHttpClientFactory;

import java.util.concurrent.TimeUnit;

/**
 * 朝辞白帝彩云间 千行代码一日还
 * 两岸领导啼不住 地铁已到回龙观
 * @author magicnana
 * @date 2020/2/20 下午6:58
 */
public class OkHttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(OkHttpClientUtil.class);

    private static final OkHttpClient client = new DefaultOkHttpClientFactory(new OkHttpClient.Builder()).createBuilder(false)
            .connectTimeout(2000, TimeUnit.MILLISECONDS)
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .followRedirects(false).connectionPool(new DefaultOkHttpClientConnectionPoolFactory().create(200, 15 * 60L, TimeUnit.SECONDS))
            .build();


}
