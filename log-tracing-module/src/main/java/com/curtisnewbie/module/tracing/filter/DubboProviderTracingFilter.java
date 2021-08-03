package com.curtisnewbie.module.tracing.filter;

import com.curtisnewbie.module.tracing.common.MdcUtil;
import com.curtisnewbie.module.tracing.common.TracingConstants;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filter for consumer that saves traceId into MDC
 *
 * @author yongjie.zhuang
 */
@Activate(group = CommonConstants.PROVIDER)
public class DubboProviderTracingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(DubboProviderTracingFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        try {
            // retrieve traceId from attachment
            final String traceId = RpcContext.getServerContext().getAttachment(TracingConstants.TRACE_ID);

            if (traceId != null)
                MdcUtil.setTraceId(traceId);
            else
                logger.info("Unable to retrieve traceId from attachment, can't put it into MDC");
        } catch (Exception e) {
            // catch all exception to avoid interrupting invocation
            logger.warn("Error: ", e);
        } finally {
            MdcUtil.removeTraceId();
        }

        // do invocation
        return invoker.invoke(invocation);
    }
}
