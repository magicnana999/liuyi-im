package com.creolophus.im.netty.sleuth;

/**
 * @author magicnana
 * @date 2020/7/15 下午2:31
 */
public class SleuthNettyAdapterLocal extends InheritableThreadLocal<SleuthNettyAdapter> {

    /**
     * 上下文信息
     */
    private static final SleuthNettyAdapterLocal WEB_CONTEXT_LOCAL = new SleuthNettyAdapterLocal() {
        @Override
        protected SleuthNettyAdapter initialValue() {
            return new SleuthNettyAdapter();
        }
    };

    /**
     * 获得当前线程对象
     *
     * @return
     */
    public static SleuthNettyAdapterLocal getInstance() {
        return WEB_CONTEXT_LOCAL;
    }
}
