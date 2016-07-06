package com.ai.slp.order.api.shopcart;

import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.order.api.shopcart.interfaces.IShopCartSV;
import com.ai.slp.order.api.shopcart.param.CartProd;
import com.ai.slp.order.api.shopcart.param.CartProdOptRes;
import com.ai.slp.order.api.shopcart.param.UserInfo;
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

    @Test
    public void queryPointsOfCartTest(){
        IShopCartSV shopCartSV = DubboConsumerFactory.getService(IShopCartSV.class);
        UserInfo userInfo = new UserInfo();
        userInfo.setTenantId("SLP");
        userInfo.setUserId("000000000000000480");
        CartProdOptRes optRes = shopCartSV.queryPointsOfCart(userInfo);
        ResponseHeader header = optRes.getResponseHeader();
        if (header!=null && !header.isSuccess()){
            System.out.println(header.getResultCode()+":"+header.getResultMessage());
        }else {
            System.out.println("查询正常,内容如下:\r\n"+optRes.toString());
        }
    }
}
