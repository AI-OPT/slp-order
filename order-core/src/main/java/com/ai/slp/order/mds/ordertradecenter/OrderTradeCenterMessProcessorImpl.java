package com.ai.slp.order.mds.ordertradecenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.base.exception.BusinessException;
import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.vo.MessageAndMetadata;
import com.ai.slp.order.api.orderrule.interfaces.IOrderMonitorSV;
import com.ai.slp.order.api.orderrule.param.OrderMonitorRequest;
import com.alibaba.fastjson.JSON;

/**
 * 订单下单时预警订单消息处理(已废弃)
 */
public class OrderTradeCenterMessProcessorImpl implements IMessageProcessor {
    private static Logger logger = LoggerFactory.getLogger(OrderTradeCenterMessProcessorImpl.class);

    private IOrderMonitorSV orderMonitorSV;

    public OrderTradeCenterMessProcessorImpl(IOrderMonitorSV orderMonitorSV){
        this.orderMonitorSV = orderMonitorSV;
    }

    @Override
    public void process(MessageAndMetadata message) throws Exception {
        if (null == message)
            return;
        String content = new String(message.getMessage(), "UTF-8");
        logger.info("--Topic:{}\r\n----key:{}\r\n----content:{}"
                , message.getTopic(),new String(message.getKey(), "UTF-8"),content);
        //转换对象
        OrderMonitorRequest request = JSON.parseObject(content,OrderMonitorRequest.class);
        if (request==null)
            return;
        try {
			this.orderMonitorSV.afterSubmitOrder(request);
        } catch (BusinessException e) {
			e.printStackTrace();
			logger.error("消息处理出现异常:"+e.getMessage());
		}        
    }
 
}
