package com.ai.slp.order.mds.orderstatechg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.base.exception.BusinessException;
import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.vo.MessageAndMetadata;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.vo.OrderStateChgVo;
import com.alibaba.fastjson.JSON;

/**
 * 订单轨迹信息消息处理
 */
public class OrderStateChgServiceMessProcessorImpl implements IMessageProcessor {
    private static Logger logger = LoggerFactory.getLogger(OrderStateChgServiceMessProcessorImpl.class);

	private IOrderFrameCoreSV orderFrameCoreSV;

    public OrderStateChgServiceMessProcessorImpl(IOrderFrameCoreSV orderFrameCoreSV){
        this.orderFrameCoreSV = orderFrameCoreSV;
    }

    @Override
    public void process(MessageAndMetadata message) throws Exception {
        if (null == message)
            return;
        String content = new String(message.getMessage(), "UTF-8");
        logger.info("--Topic:{}\r\n----key:{}\r\n----content:{}"
                , message.getTopic(),new String(message.getKey(), "UTF-8"),content);
        //转换对象
        OrderStateChgVo request = JSON.parseObject(content,OrderStateChgVo.class);
        if (request==null)
            return;
        try {
			this.orderFrameCoreSV.ordOdStateChg(request);
        } catch (BusinessException e) {
			e.printStackTrace();
			logger.error("消息处理出现异常:"+e.getMessage());
		}                
    }

}
