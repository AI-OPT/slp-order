package com.ai.slp.order.mds.aftersaleorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.vo.MessageAndMetadata;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;
import com.ai.slp.order.service.business.interfaces.IOrderAfterSaleBusiSV;
import com.alibaba.fastjson.JSON;

/**
 * 订单退款消息处理
 */
public class OrderAfterSaleRefundMessProcessorImpl implements IMessageProcessor {
    private static Logger logger = LoggerFactory.getLogger(OrderAfterSaleRefundMessProcessorImpl.class);

    private IOrderAfterSaleBusiSV orderAfterSaleBusiSV;

    public OrderAfterSaleRefundMessProcessorImpl(IOrderAfterSaleBusiSV orderAfterSaleBusiSV){
        this.orderAfterSaleBusiSV = orderAfterSaleBusiSV;
    }

    @Override
    public void process(MessageAndMetadata message) throws Exception {
        if (null == message)
            return;
        String content = new String(message.getMessage(), "UTF-8");
        logger.info("--Topic:{}\r\n----key:{}\r\n----content:{}"
                , message.getTopic(),new String(message.getKey(), "UTF-8"),content);
        //转换对象
        OrderReturnRequest request = JSON.parseObject(content,OrderReturnRequest.class);
        if (request==null)
            return;
        this.orderAfterSaleBusiSV.refund(request);        
    }

}
