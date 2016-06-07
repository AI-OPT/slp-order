package com.ai.slp.order.api.shopcart;

import com.ai.slp.order.api.shopcart.interfaces.IShopCartSV;
import com.ai.slp.order.api.shopcart.param.CartProd;
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
        cartProd.setUserId("123");
        cartProd.setSkuId("1");
        cartProd.setBuyNum(100l);
        shopCartSV.addProd(cartProd);
    }
}
