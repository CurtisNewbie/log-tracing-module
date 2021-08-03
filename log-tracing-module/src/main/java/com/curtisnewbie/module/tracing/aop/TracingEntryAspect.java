package com.curtisnewbie.module.tracing.aop;

import com.curtisnewbie.module.tracing.common.TracingConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Aspect for entries of log tracing (controllers and the REST endpoints)
 *
 * @author yongjie.zhuang
 */
@Aspect
@Component
public class TracingEntryAspect {
    private static final Logger logger = LoggerFactory.getLogger(TracingEntryAspect.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Around("@annotation(com.curtisnewbie.module.tracing.aop.TracingEntry)")
    public Object putMdcTracingInfo(ProceedingJoinPoint pjp) throws Throwable {
        try {
            // put principal info in it if there is one
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getPrincipal() != null) {
                // convert principal object to a map to get the id, avoid importing unneeded dependency
                Map<String, String> map = objectMapper.convertValue(auth.getPrincipal(), Map.class);
                if (map.containsKey(TracingConstants.TRACE_ENTRY_FIELD)) {

                    // put the tracing info into MDC
                    String tracingId = String.valueOf(map.get(TracingConstants.TRACE_ENTRY_FIELD));
                    MDC.put(TracingConstants.TRACE_ID, tracingId);
                    logger.info("Put tracing_id into MDC: {}", tracingId);
                } else {
                    logger.warn("Annotation {} is used, but principal doesn't contain field '{}', unable to do log tracing",
                            TracingEntry.class.getSimpleName(), TracingConstants.TRACE_ENTRY_FIELD);
                }
            } else {
                logger.warn("Annotation {} is used, but authentication or principal is missing, unable to do log tracing",
                        TracingEntry.class.getSimpleName());
            }
        } catch (Exception e) {
            // catch all kinds of exception to avoid breaking the business operation
            logger.error("Error occurred while adding MDC tracing info", e);
        }

        // proceed to the next step
        return pjp.proceed();
    }
}
