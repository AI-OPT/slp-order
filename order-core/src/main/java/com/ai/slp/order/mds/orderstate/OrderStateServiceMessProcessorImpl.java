package com.ai.slp.order.mds.orderstate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.base.exception.BusinessException;
import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.vo.MessageAndMetadata;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.alibaba.fastjson.JSON;

/**
 * 买家退货填写物流消息处理
 */
public class OrderStateServiceMessProcessorImpl implements IMessageProcessor {
    private static Logger logger = LoggerFactory.getLogger(OrderStateServiceMessProcessorImpl.class);

    private IOrdOrderAtomSV ordOrderAtomSV;

    public OrderStateServiceMessProcessorImpl(IOrdOrderAtomSV ordOrderAtomSV){
        this.ordOrderAtomSV = ordOrderAtomSV;
    }

    @Override
    public void process(MessageAndMetadata message) throws Exception {
        if (null == message)
            return;
        String content = new String(message.getMessage(), "UTF-8");
        logger.info("--Topic:{}\r\n----key:{}\r\n----content:{}"
                , message.getTopic(),new String(message.getKey(), "UTF-8"),content);
        //转换对象
        OrdOrder ordOrder = JSON.parseObject(content,OrdOrder.class);
        if (ordOrder==null)
            return;
        try {
			this.ordOrderAtomSV.updateOrderState(ordOrder);
        } catch (BusinessException e) {
			e.printStackTrace();
			logger.error("消息处理出现异常:"+e.getMessage());
		}                
    }

}
