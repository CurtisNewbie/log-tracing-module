package com.curtisnewbie.module.tracing.filter;

import com.curtisnewbie.module.tracing.common.MdcUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * <p>
 * A {@link HandlerInterceptor} for log tracing using MDC
 * </p>
 * Example:
 * <pre>
 * {@code
 * @Configuration
 * public class WebConfig implements WebMvcConfigurer {
 *
 *      @Override
 *      public void addInterceptors(InterceptorRegistry registry) {
 *          registry.addInterceptor(
 *              new TracingHandlerInterceptor(
 *                  new FieldNameBasedExtractor("username", UserVo.class)
 *              )
 *          );
 *      }
 * }
 * }
 * </pre>
 *
 * @author yongjie.zhuang
 */
public class TracingHandlerInterceptor implements HandlerInterceptor {

    private final TraceIdExtractor traceIdExtractor;

    /**
     * @param extractor the component that will be used to extract traceId
     */
    public TracingHandlerInterceptor(TraceIdExtractor extractor) {
        Objects.requireNonNull(extractor);
        this.traceIdExtractor = extractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MdcUtil.setTraceId(traceIdExtractor.extract(request));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        MdcUtil.removeTraceId();
    }
}
