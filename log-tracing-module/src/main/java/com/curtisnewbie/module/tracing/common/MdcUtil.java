package com.curtisnewbie.module.tracing.common;

import org.slf4j.MDC;

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
     * Set traceId used by log-tracing, this is used when the existing functionalities doesn't satisfy the need (e.g.,
     * TracingEntryAspect), and user wants to manually set the traceId.
     *
     * @param traceId traceId
     */
    public static void setTraceId(String traceId) {
        Objects.requireNonNull(traceId);
        MDC.put(TracingConstants.TRACE_ID, traceId);
    }

    /**
     * Get traceId used by log-tracing
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
