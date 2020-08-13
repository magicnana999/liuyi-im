package com.creolophus.im.common.util;

import com.creolophus.liuyi.common.exception.ApiException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author magicnana
 * @date 2020/6/29 下午1:48
 */
public class Strings {

    public static boolean isBlank(CharSequence s) {
        return StringUtils.isBlank(s);
    }

    public static boolean isNotBlank(CharSequence s) {
        return StringUtils.isNotBlank(s);
    }

    public static CharSequence requireNonBlank(CharSequence s, String message) {
        if(StringUtils.isBlank(s)) {
            throw new BlankException(message);
        }
        return s;
    }

    public static CharSequence requireNonBlank(CharSequence s) {
        return requireNonBlank(s, "could not be blank");
    }

    public static class BlankException extends ApiException {

        public BlankException(String message) {
            super(message);
        }

        public BlankException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
