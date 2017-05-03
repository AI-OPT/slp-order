package com.ai.slp.order.mds.aftersaleorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.base.exception.BusinessException;
import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.vo.MessageAndMetadata;
import com.ai.slp.order.api.aftersaleorder.param.OrderOFCBackRequest;
import com.ai.slp.order.service.business.interfaces.IOrderAfterSaleBusiSV;
import com.alibaba.fastjson.JSON;

/**
 * OFC售后订单状态消息处理(已废弃)
 */

public class OfcOrderAfterSaleMessProcessorImpl implements IMessageProcessor {
    private static Logger logger = LoggerFactory.getLogger(OfcOrderAfterSaleMessProcessorImpl.class);

    private IOrderAfterSaleBusiSV orderAfterSaleBusiSV;

    public OfcOrderAfterSaleMessProcessorImpl(IOrderAfterSaleBusiSV orderAfterSaleBusiSV){
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
        OrderOFCBackRequest request = JSON.parseObject(content,OrderOFCBackRequest.class);
        if (request==null)
            return;
        try {
			//this.orderAfterSaleBusiSV.backStateOFC(request);
	    } catch (BusinessException e) {
			e.printStackTrace();
			logger.error("消息处理出现异常:"+e.getMessage());
		}        
    }

}
