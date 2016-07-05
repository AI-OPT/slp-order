package com.ai.slp.order.api.shopcart;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.order.api.shopcart.interfaces.IShopCartSV;
import com.ai.slp.order.api.shopcart.param.CartProd;
import com.ai.slp.order.api.shopcart.param.CartProdOptRes;
import org.junit.Test;

/**
 * Created by jackieliu on 16/7/4.
 */
public class IShopCartDubboTest {

    @Test
    public void addProdTest(){
        IShopCartSV shopCartSV = DubboConsumerFactory.getService(IShopCartSV.class);
        CartProd cartProd = new CartProd();
        cartProd.setTenantId("SLP");
        cartProd.setUserId("000000000000000480");
        cartProd.setSkuId("2");
        cartProd.setBuyNum(2l);
        CartProdOptRes optRes = shopCartSV.addProd(cartProd);
    }
}
