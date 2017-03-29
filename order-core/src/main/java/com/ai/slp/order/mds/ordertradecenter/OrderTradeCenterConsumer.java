package com.ai.slp.order.mds.ordertradecenter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.sdk.components.mds.MDSClientFactory;
import com.ai.opt.sdk.components.mds.base.AbstractMdsConsumer;
import com.ai.paas.ipaas.mds.IMessageConsumer;
import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.IMsgProcessorHandler;
import com.ai.slp.order.api.orderrule.interfaces.IOrderMonitorSV;
import com.ai.slp.order.constants.OrdersConstants;

@Component
public class OrderTradeCenterConsumer extends AbstractMdsConsumer {
	private static Logger logger = LoggerFactory.getLogger(OrderTradeCenterConsumer.class);

    @Autowired
    private IOrderMonitorSV orderMonitorSV;
	
    @Override
	public void startMdsConsumer() throws Exception {
		logger.error("开始启动OrderTradeCenterMdsConsumer。。。。。");
		IMsgProcessorHandler msgProcessorHandler=new IMsgProcessorHandler() {
            @Override
            public IMessageProcessor[] createInstances(int paramInt) {
                List<IMessageProcessor> processors = new ArrayList<>();
                IMessageProcessor processor = null;
                for (int i = 0; i < paramInt; i++) {
                    processor = new OrderTradeCenterMessProcessorImpl(orderMonitorSV);
                    processors.add(processor);
                }
                return processors.toArray(new IMessageProcessor[processors.size()]);
            }
        };
        IMessageConsumer msgConsumer= MDSClientFactory.getConsumerClient(
                OrdersConstants.MDSNS.MDS_NS_ORDER_TOPIC, msgProcessorHandler);
        msgConsumer.start();
        logger.error("成功启动OrderTradeCenterMdsConsumer。。。。。");

	}

}
