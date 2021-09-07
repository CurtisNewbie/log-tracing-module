package com.curtisnewbie.module.tracing.common;

import org.slf4j.MDC;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * Utility class for MDC
 *
 * @author yongjie.zhuang
 */
public class MdcUtil {

    private MdcUtil() {
    }

    /**
     * Set traceId used for log-tracing
     *
     * @param traceId traceId
     */
    public static void setTraceId(@Nullable String traceId) {
        MDC.put(TracingConstants.TRACE_ID, traceId);
    }

    /**
     * Get traceId used for log-tracing
     *
     * @return traceId (may be null)
     */
    public static String getTraceId() {
        return MDC.get(TracingConstants.TRACE_ID);
    }

    /**
     * Remove traceId
     */
    public static void removeTraceId() {
        MDC.remove(TracingConstants.TRACE_ID);
    }
}
