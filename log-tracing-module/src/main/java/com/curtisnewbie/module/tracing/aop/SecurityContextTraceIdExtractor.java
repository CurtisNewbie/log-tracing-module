package com.curtisnewbie.module.tracing.aop;

import com.curtisnewbie.module.tracing.common.TracingConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Extract trace_id using {@link SecurityContextHolder}
 * <p>
 * It uses {@link SecurityContextHolder} to retrieve the {@link Authentication} object, as well as the {@link
 * java.security.Principal} object. If the user is not authenticated, these object may be null, then log tracing will
 * not work for these calls. In this case, it will simply return null for {@link #extractTraceId()}.
 * </p>
 * <p>
 * Otherwise, if the user is authenticated, it will try to extract value of field named {@link
 * TracingConstants#AUTHENTICATION_PRINCIPAL_TRACE_ID_FIELD} in the {@link java.security.Principal} object, and use this field as traceId.
 * </p>
 * <pre>
 *     Snippet:
 *     {@code
 *          Map<String, String> map = objectMapper.convertValue(auth.getPrincipal(), Map.class);
 *          if (map.containsKey(TracingConstants.TRACE_ENTRY_FIELD)) {
 *              traceId = String.valueOf(map.get(TracingConstants.TRACE_ENTRY_FIELD));
 *          }
 *      }
 * </pre>
 *
 * @author yongjie.zhuang
 */
@Component
public class SecurityContextTraceIdExtractor implements TraceIdExtractor {

    private static final Logger logger = LoggerFactory.getLogger(SecurityContextTraceIdExtractor.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Nullable
    public String extractTraceId() {
        String traceId = null;
        // put principal info in it if there is one
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() != null) {
            // convert principal object to a map to get the id, avoid importing unneeded dependency
            Map<String, String> map = objectMapper.convertValue(auth.getPrincipal(), Map.class);
            if (map.containsKey(TracingConstants.AUTHENTICATION_PRINCIPAL_TRACE_ID_FIELD)) {
                traceId = String.valueOf(map.get(TracingConstants.AUTHENTICATION_PRINCIPAL_TRACE_ID_FIELD));
            } else {
                logger.warn("Principal object doesn't contain field {}, unable to extract traceId", TracingConstants.AUTHENTICATION_PRINCIPAL_TRACE_ID_FIELD);
            }
        } else {
            logger.warn("Authentication or Principal object is null, unable to extract traceId");
        }
        return traceId;
    }
}
