package com.curtisnewbie.module.tracing.aop;

import org.springframework.lang.Nullable;

/**
 * Extract traceId
 *
 * @author yongjie.zhuang
 */
public interface TraceIdExtractor {

    /**
     * Extract trace_id
     *
     * @return trace_id may be null if not found
     */
    @Nullable
    String extractTraceId();
}
