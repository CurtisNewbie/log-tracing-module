package com.curtisnewbie.module.tracing.filter;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Responsible for extracting traceId from a {@link HttpServletRequest}
 * </p>
 *
 * @author yongjie.zhuang
 */
public interface TraceIdExtractor {

    /**
     * Extract the traceId
     */
    String extract(HttpServletRequest request);
}
