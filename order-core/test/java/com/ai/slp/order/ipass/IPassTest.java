package com.ai.slp.order.ipass;

import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.slp.order.constants.MallIPassConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jackieliu on 16/5/19.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/context/core-context.xml" })
public class IPassTest {

    @Test
    public void cacheTest(){
        ICacheClient iCacheClient = MCSClientFactory.getCacheClient(MallIPassConstants.SHOP_CART_MCS);
        String cacheKeyTest = "cacheKeyTest";
        iCacheClient.set(cacheKeyTest,"cacheValTest");
    }
}
