package com.ai.slp.order.api.shopcart;

import com.ai.slp.order.api.shopcart.interfaces.IShopCartSV;
import com.ai.slp.order.api.shopcart.param.CartProd;
import com.ai.slp.order.api.shopcart.param.UserInfo;
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
        cartProd.setSkuId("1");
        cartProd.setBuyNum(90l);
        shopCartSV.addProd(cartProd);
    }

    @Test
    public void showCartList(){
        UserInfo userInfo = new UserInfo();
        userInfo.setTenantId("SLP");
        userInfo.setUserId("000000000000000994");
        shopCartSV.queryCartOfUser(userInfo);
    }
}
