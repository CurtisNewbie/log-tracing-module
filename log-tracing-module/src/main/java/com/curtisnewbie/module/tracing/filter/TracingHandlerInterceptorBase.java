package com.curtisnewbie.module.tracing.filter;

import com.curtisnewbie.module.tracing.common.MdcUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;
import java.util.Map;

/**
 * Base class for HandlerInterceptor that sets trace_id
 * <p>
 * Usage:
 * <pre>
 *    {@code
 *          public class TracingHandlerInterceptor extends TracingHandlerInterceptorBase implements HandlerInterceptor {
 *
 *          public TracingHandlerInterceptor() {
 *              super(TracingConstants.PRINCIPAL_TRACE_ID_FIELD);
 *          }
 *
 *          @Override
 *          public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
 *              Principal principal = request.getUserPrincipal();
 *              doPreHandle(principal);
 *
 *              return true;
 *          }
 *      }
 *    }
 * </pre>
 *
 * </p>
 *
 * @author yongjie.zhuang
 */
public abstract class TracingHandlerInterceptorBase {

    private static final Logger logger = LoggerFactory.getLogger(TracingHandlerInterceptorBase.class);
    private final String traceIdFieldName;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Create new HandlerInterceptor for tracing
     *
     * @param traceIdFieldName name of the field used as trace_id
     */
    public TracingHandlerInterceptorBase(String traceIdFieldName) {
        this.traceIdFieldName = traceIdFieldName;
    }

    /**
     * Do pre-handle, set trace_id is possible
     *
     * @param principal principal object
     */
    protected boolean doPreHandle(Principal principal) {
        if (principal != null) {
            boolean isTraceIdSet = false;

            isTraceIdSet = setTraceId(principal);

            if (!isTraceIdSet && principal instanceof UsernamePasswordAuthenticationToken) {
                setTraceId(((UsernamePasswordAuthenticationToken) principal).getPrincipal());
            }
        }

        return true;
    }

    private boolean setTraceId(Object obj) {
        Map<String, String> map = objectMapper.convertValue(obj, Map.class);
        if (map.containsKey(traceIdFieldName)) {
            MdcUtil.setTraceId(String.valueOf(map.get(traceIdFieldName)));
            return true;
        }
        return false;
    }
}
