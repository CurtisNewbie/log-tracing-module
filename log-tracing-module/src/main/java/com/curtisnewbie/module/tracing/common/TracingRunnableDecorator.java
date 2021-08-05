package com.curtisnewbie.module.tracing.common;

import org.slf4j.MDC;

import java.util.Map;

/**
 * Decorator of runnable for tracing (copying MDC between threads)
 *
 * @author yongjie.zhuang
 */
public final class TracingRunnableDecorator {

    private TracingRunnableDecorator() {
    }

    /**
     * Wrap the runnable inside a another runnable, meanwhile, copy the contextMap to this wrapped runnable for the
     * log-tracing to work
     *
     * @param r runnable
     * @return decorated runnable
     */
    public static Runnable decorate(Runnable r) {
        Map<String, String> m = MDC.getCopyOfContextMap();
        // wrap the runnable inside a another, copy the contextMap to this thread
        return () -> {
            MDC.setContextMap(m);
            r.run();
        };
    }

}