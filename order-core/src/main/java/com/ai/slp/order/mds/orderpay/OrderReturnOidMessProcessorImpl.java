package com.ai.slp.order.mds.orderpay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.vo.MessageAndMetadata;
import com.ai.slp.order.api.orderpay.param.OrderOidRequest;
import com.ai.slp.order.service.business.interfaces.IOrderPayBusiSV;
import com.alibaba.fastjson.JSON;

/**
 * 用户消费积分状态消息处理
 */
public class OrderReturnOidMessProcessorImpl implements IMessageProcessor {
    private static Logger logger = LoggerFactory.getLogger(OrderReturnOidMessProcessorImpl.class);

    private IOrderPayBusiSV orderPayBusiSV;

    public OrderReturnOidMessProcessorImpl(IOrderPayBusiSV orderPayBusiSV){
        this.orderPayBusiSV = orderPayBusiSV;
    }

    @Override
    public void process(MessageAndMetadata message) throws Exception {
        if (null == message)
            return;
        String content = new String(message.getMessage(), "UTF-8");
        logger.info("--Topic:{}\r\n----key:{}\r\n----content:{}"
                , message.getTopic(),new String(message.getKey(), "UTF-8"),content);
        //转换对象
        OrderOidRequest request = JSON.parseObject(content,OrderOidRequest.class);
        if (request==null)
            return;
        this.orderPayBusiSV.returnOid(request);        
    }

}
