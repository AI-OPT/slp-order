package com.ai.slp.order.mds.aftersaleorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.base.exception.BusinessException;
import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.vo.MessageAndMetadata;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;
import com.ai.slp.order.service.business.interfaces.IOrderAfterSaleBusiSV;
import com.alibaba.fastjson.JSON;

/**
 * 订单退货消息处理
 */
public class OrderAfterSaleBackMessProcessorImpl implements IMessageProcessor {
    private static Logger logger = LoggerFactory.getLogger(OrderAfterSaleBackMessProcessorImpl.class);

    private IOrderAfterSaleBusiSV orderAfterSaleBusiSV;

    public OrderAfterSaleBackMessProcessorImpl(IOrderAfterSaleBusiSV orderAfterSaleBusiSV){
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
        try {
			this.orderAfterSaleBusiSV.back(request);
        } catch (BusinessException e) {
			e.printStackTrace();
			logger.error("消息处理出现异常:"+e.getMessage());
		}                
    }

}
