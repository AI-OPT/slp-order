package com.ai.slp.order.mds.shopcart;

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
import com.ai.slp.order.constants.ShopCartConstants;
import com.ai.slp.order.service.atom.interfaces.IOrdOdCartProdAtomSV;

//@Component
public class ShopCartMdsConsumer extends AbstractMdsConsumer {
	private static Logger logger = LoggerFactory.getLogger(ShopCartMdsConsumer.class);

	@Autowired
	IOrdOdCartProdAtomSV cartProdAtomSV;
	@Override
	public void startMdsConsumer() throws Exception {
		logger.error("开始启动ShopCartMdsConsumer。。。。。");
		IMsgProcessorHandler msgProcessorHandler=new IMsgProcessorHandler() {
            @Override
            public IMessageProcessor[] createInstances(int paramInt) {
                List<IMessageProcessor> processors = new ArrayList<>();
                IMessageProcessor processor = null;
                for (int i = 0; i < paramInt; i++) {
                    processor = new ShopCartMessProcessorImpl(cartProdAtomSV);
                    processors.add(processor);
                }
                return processors.toArray(new IMessageProcessor[processors.size()]);
            }
        };
        IMessageConsumer msgConsumer= MDSClientFactory.getConsumerClient(
                ShopCartConstants.MdsParams.SHOP_CART_TOPIC, msgProcessorHandler);
        msgConsumer.start();
        logger.error("成功启动ShopCartMdsConsumer。。。。。");

	}

}
