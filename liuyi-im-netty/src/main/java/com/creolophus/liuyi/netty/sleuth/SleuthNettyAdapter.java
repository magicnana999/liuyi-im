package com.creolophus.liuyi.netty.sleuth;

import brave.Tracer;
import com.creolophus.liuyi.common.api.MdcUtil;
import com.creolophus.liuyi.common.logger.TracerUtil;

import javax.annotation.Resource;

/**
 * @author magicnana
 * @date 2020/7/15 下午2:30
 */
public class SleuthNettyAdapter {

    private Tracer.SpanInScope span;

    public Tracer.SpanInScope getSpan() {
        return span;
    }

    public void setSpan(Tracer.SpanInScope span) {
        this.span = span;
    }

    public void begin(TracerUtil tracerUtil,String methodName) {
        if(getSpan() == null) {
            Tracer.SpanInScope span = tracerUtil.begin(SleuthNettyAdapter.class.getSimpleName(), methodName);
            setSpan(span);
        }
        MdcUtil.setMethod(methodName);
        MdcUtil.setExt("-");
        MdcUtil.setUri("-");
    }

    public void cleanContext() {
        if(span!=null){
            span.close();
            span = null;
        }
    }

    public static SleuthNettyAdapter getInstance() {
        SleuthNettyAdapter apiContext = SleuthNettyAdapterLocal.getInstance().get();
        return apiContext;
    }
}
