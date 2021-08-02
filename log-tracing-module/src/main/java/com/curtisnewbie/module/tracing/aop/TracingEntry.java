package com.curtisnewbie.module.tracing.aop;

import java.lang.annotation.*;

/**
 * Mark a controller or method (endpoint) as an entry point for log tracing
 *
 * @author yongjie.zhuang
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TracingEntry {
}
