package com.curtisnewbie.module.tracing.filter;

import com.curtisnewbie.module.tracing.common.MdcUtil;
import com.curtisnewbie.module.tracing.common.TracingConstants;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Filter for provider that put traceId into the invocation as attachment
 *
 * @author yongjie.zhuang
 */
@Activate(group = CommonConstants.CONSUMER)
public class DubboConsumerTracingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(DubboConsumerTracingFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // put traceId into attachment
        final String traceId = MdcUtil.getTraceId();
        if (traceId != null)
            RpcContext.getContext().setAttachment(TracingConstants.TRACE_ID, traceId);
        else
            logger.debug("Unable to retrieve traceId from MDC, can't put it into attachment");

        return invoker.invoke(invocation);
    }
}
