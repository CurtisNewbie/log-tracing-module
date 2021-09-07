package com.curtisnewbie.module.tracing.filter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * Implementation that extracts traceId by getting the value of a specified field (using field name) from an object
 * </p>
 * <p>
 * This implementation first deserializes
 * </p>
 *
 * @author yongjie.zhuang
 */
public class FieldNameBasedExtractor implements TraceIdExtractor {

    private final String traceIdFieldName;
    private final Class<?> targetType;
    private JsonMapper jsonMapper = new JsonMapper();

    /**
     * @param traceIdFieldName field name that will be extracted as traceId
     * @param targetType       type of the object from which the traceId is extracted
     */
    public FieldNameBasedExtractor(String traceIdFieldName, Class<?> targetType) {
        this.traceIdFieldName = traceIdFieldName;
        this.targetType = targetType;
    }

    @Override
    public String extract(HttpServletRequest request) {
        if (request.getUserPrincipal() == null)
            return null;
        if (canExtract(request.getUserPrincipal())) {
            return extractFrom(request.getUserPrincipal());
        }
        if (request.getUserPrincipal() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
            if (canExtract(principal.getPrincipal()))
                return extractFrom(principal.getPrincipal());
        }
        return null;
    }

    private String extractFrom(Object o) {
        Map<String, String> map = jsonMapper.convertValue(o, Map.class);
        return map.get(traceIdFieldName);
    }

    private boolean canExtract(Object o) {
        return o != null && targetType.isAssignableFrom(o.getClass());
    }
}
