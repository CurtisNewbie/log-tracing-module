package com.curtisnewbie.module.tracing.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

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

    private final Class<?> targetType;
    private final Field traceIdField;

    /**
     * Construct FieldNameBasedExtractor
     *
     * @param traceIdFieldName field name that will be extracted as traceId
     * @param targetType       type of the object from which the traceId is extracted
     * @throws NoSuchFieldException when there is no such field on the targetType
     */
    public FieldNameBasedExtractor(String traceIdFieldName, Class<?> targetType) throws NoSuchFieldException {
        this.targetType = targetType;
        traceIdField = targetType.getDeclaredField(traceIdFieldName);
        traceIdField.setAccessible(true);
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
        try {
            Object value = traceIdField.get(o);
            if (value == null)
                return null;
            return value.toString();
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private boolean canExtract(Object o) {
        return o != null && targetType.isAssignableFrom(o.getClass());
    }
}
