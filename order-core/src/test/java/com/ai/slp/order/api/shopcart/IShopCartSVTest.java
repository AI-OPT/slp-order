package com.ai.slp.order.api.shopcart;

import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.slp.order.api.shopcart.interfaces.IShopCartSV;
import com.ai.slp.order.api.shopcart.param.CartProd;
import com.ai.slp.order.api.shopcart.param.UserInfo;
import com.ai.slp.order.constants.ShopCartConstants;
import com.ai.slp.order.util.IPassMcsUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jackieliu on 16/6/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:context/core-context.xml")
public class IShopCartSVTest {
    @Autowired
    IShopCartSV shopCartSV;

    @Test
    public void addProdTest(){
        CartProd cartProd = new CartProd();
        cartProd.setTenantId("SLP");
        cartProd.setUserId("000000000000000480");
        cartProd.setSkuId("6");
        cartProd.setBuyNum(5l);
        shopCartSV.addProd(cartProd);
    }

    @Test
    public void showCartList(){
        UserInfo userInfo = new UserInfo();
        userInfo.setTenantId("SLP");
        userInfo.setUserId("000000000000000994");
        shopCartSV.queryCartOfUser(userInfo);
    }
    
    @Test
    public void updateProdNumTest(){
    	CartProd cartProd = new CartProd();
    	cartProd.setTenantId("SLP");
    	cartProd.setUserId("000000000000000480");
    	cartProd.setBuyNum(6l);
    	shopCartSV.updateProdNum(cartProd);
    }

    @Test
    public void deleteCartCache(){
        //删除购物车缓存
        ICacheClient iCacheClient = MCSClientFactory.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
        String cartUserId = IPassMcsUtils.genShopCartUserId("SLP","000000000000000480");
        iCacheClient.del(cartUserId);
    }
}
