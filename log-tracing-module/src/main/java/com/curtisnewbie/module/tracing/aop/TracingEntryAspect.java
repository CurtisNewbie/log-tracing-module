package com.curtisnewbie.module.tracing.aop;

import com.curtisnewbie.module.tracing.common.MdcUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Aspect for entries of log tracing (controllers and the REST endpoints)
 * <p>
 * It works as follows:
 * <ol>
 *   <li>
 *      Intercept the call to methods annotated with {@link TracingEntry}, which should only be used at the web frontend
 *   </li>
 *   <li>
 *      Use the injected {@link TraceIdExtractor} to extract a traceId, and then set it to MDC (see {@link MdcUtil#setTraceId(String)}).
 *   </li>
 * </ol>
 * </p>
 *
 * @author yongjie.zhuang
 * @see TraceIdExtractor
 */
@Aspect
@Component
public class TracingEntryAspect {
    private static final Logger logger = LoggerFactory.getLogger(TracingEntryAspect.class);

    @Autowired
    private TraceIdExtractor extractor;

    @Around("@annotation(com.curtisnewbie.module.tracing.aop.TracingEntry)")
    public Object putMdcTracingInfo(ProceedingJoinPoint pjp) throws Throwable {
        try {
            // put the tracing info into MDC
            final String traceId = extractor.extractTraceId();
            if (traceId != null) {
                MdcUtil.setTraceId(traceId);
                logger.info("Put trace_id into MDC: {}", traceId);
            } else {
                logger.warn("Unable to extract trace_id, log tracing will not work");
            }
        } catch (Exception e) {
            // catch all kinds of exception to avoid breaking the business operation
            logger.error("Error occurred while adding MDC tracing info", e);
        }
        // proceed to the next step
        return pjp.proceed();
    }
}
