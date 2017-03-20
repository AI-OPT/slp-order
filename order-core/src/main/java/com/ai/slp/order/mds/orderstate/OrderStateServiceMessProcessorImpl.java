package com.ai.slp.order.mds.orderstate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.base.exception.BusinessException;
import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.vo.MessageAndMetadata;
import com.ai.slp.order.api.orderstate.param.WaitSellReceiveSureRequest;
import com.ai.slp.order.service.business.interfaces.IOrderStateBusiSV;
import com.alibaba.fastjson.JSON;

/**
 * 买家退货填写物流消息处理
 */
public class OrderStateServiceMessProcessorImpl implements IMessageProcessor {
    private static Logger logger = LoggerFactory.getLogger(OrderStateServiceMessProcessorImpl.class);

    private IOrderStateBusiSV orderStateBusiSV;

    public OrderStateServiceMessProcessorImpl(IOrderStateBusiSV orderStateBusiSV){
        this.orderStateBusiSV = orderStateBusiSV;
    }

    @Override
    public void process(MessageAndMetadata message) throws Exception {
        if (null == message)
            return;
        String content = new String(message.getMessage(), "UTF-8");
        logger.info("--Topic:{}\r\n----key:{}\r\n----content:{}"
                , message.getTopic(),new String(message.getKey(), "UTF-8"),content);
        //转换对象
        WaitSellReceiveSureRequest request = JSON.parseObject(content,WaitSellReceiveSureRequest.class);
        if (request==null)
            return;
        try {
			//this.orderStateBusiSV.updateWaitSellRecieveSureState(request);
        } catch (BusinessException e) {
			e.printStackTrace();
			logger.error("消息处理出现异常:"+e.getMessage());
		}                
    }

}
