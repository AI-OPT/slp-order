package com.ai.slp.order.ipass;

import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.opt.sdk.components.mds.MDSClientFactory;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.paas.ipaas.mds.IMessageConsumer;
import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.IMessageSender;
import com.ai.paas.ipaas.mds.IMsgProcessorHandler;
import com.ai.paas.ipaas.mds.vo.MessageAndMetadata;
import com.ai.slp.order.constants.ShopCartConstants;
import com.ai.slp.order.service.atom.interfaces.IOrdOdCartProdAtomSV;
import com.ai.slp.order.service.business.impl.ShopCartMessProcessorImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieliu on 16/5/19.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/context/core-context.xml"})
public class IPassTest {

    @Test
    public void cacheTest(){
        ICacheClient iCacheClient = MCSClientFactory.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
        String cacheKeyTest = "cacheKeyTest";
//        iCacheClient.set(cacheKeyTest,"cacheValTest");
        iCacheClient.del(cacheKeyTest);
    }

    @Test
    public void sendMdsTest(){
        IMessageSender msgSender = MDSClientFactory.getSenderClient(ShopCartConstants.MdsParams.SHOP_CART_TOPIC);
        for(int i=0;i<5;i++){
            int part=i%2;
            msgSender.send("[opt-sdk-msg:"+i+"]This is a test message……", part);//第二个参数为分区键，如果不分区，传入0
        }
    }

    @Test
    public void recvMsgTest(){
        IMsgProcessorHandler msgProcessorHandler=new IMsgProcessorHandler() {

            @Override
            public IMessageProcessor[] createInstances(int paramInt) {
                List<IMessageProcessor> processors = new ArrayList<>();
                IMessageProcessor processor = null;
                for (int i = 0; i < paramInt; i++) {
                    processor = new MessageProcessorImpl();
                    processors.add(processor);
                }
                return processors.toArray(new IMessageProcessor[processors.size()]);
            }
        };
        IMessageConsumer msgConsumer= MDSClientFactory.getConsumerClient(
                ShopCartConstants.MdsParams.SHOP_CART_TOPIC, msgProcessorHandler);
        msgConsumer.start();
        synchronized (IPassTest.class) {
            while (true) {
                try {
                    IPassTest.class.wait();
                } catch (Exception e) {
                    System.out.println("MDS 消费错误，具体信息为：" + e.getMessage());
                }
            }
        }

    }

    class MessageProcessorImpl implements IMessageProcessor{
        @Override
        public void process(MessageAndMetadata message) throws Exception {
            // TODO Auto-generated method stub
            if (null != message) {
                System.out.println("------Topic:" + message.getTopic() + ",key:"
                        + new String(message.getKey(), "UTF-8") + ",content:"
                        + new String(message.getMessage(), "UTF-8"));
            }

        }

    }


}
