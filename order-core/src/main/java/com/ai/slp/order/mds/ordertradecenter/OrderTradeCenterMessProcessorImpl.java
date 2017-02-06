package com.ai.slp.order.mds.ordertradecenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.vo.MessageAndMetadata;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.service.business.interfaces.IOrdOrderTradeBusiSV;
import com.alibaba.fastjson.JSON;

/**
 * 购物车消息处理
 * Created by jackieliu on 16/5/19.
 */
public class OrderTradeCenterMessProcessorImpl implements IMessageProcessor {
    private static Logger logger = LoggerFactory.getLogger(OrderTradeCenterMessProcessorImpl.class);

    IOrdOrderTradeBusiSV ordOrderTradeBusiSV;

    public OrderTradeCenterMessProcessorImpl(IOrdOrderTradeBusiSV ordOrderTradeBusiSV){
        this.ordOrderTradeBusiSV = ordOrderTradeBusiSV;
    }

    @Override
    public void process(MessageAndMetadata message) throws Exception {
        if (null == message)
            return;
        String content = new String(message.getMessage(), "UTF-8");
        logger.info("--Topic:{}\r\n----key:{}\r\n----content:{}"
                , message.getTopic(),new String(message.getKey(), "UTF-8"),content);
        //转换对象
        OrderTradeCenterRequest request = JSON.parseObject(content,OrderTradeCenterRequest.class);
        if (request==null)
            return;
        this.ordOrderTradeBusiSV.apply(request);        
    }

}
