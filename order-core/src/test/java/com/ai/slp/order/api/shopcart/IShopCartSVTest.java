package com.ai.slp.order.api.shopcart;

import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.slp.order.api.shopcart.interfaces.IShopCartSV;
import com.ai.slp.order.api.shopcart.param.CartProd;
import com.ai.slp.order.api.shopcart.param.MultiCartProd;
import com.ai.slp.order.api.shopcart.param.UserInfo;
import com.ai.slp.order.constants.ShopCartConstants;
import com.ai.slp.order.util.IPassMcsUtils;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

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
        cartProd.setTenantId("changhong");
        cartProd.setUserId("3da3109cdb3f4d9e11");
        cartProd.setSkuId("0000000000000286");
        cartProd.setBuyNum(1l);
        System.out.println(JSON.toJSON(cartProd));
        shopCartSV.addProd(cartProd);
    }

    @Test
    public void showCartList(){
        UserInfo userInfo = new UserInfo();
        userInfo.setTenantId("changhong");
        userInfo.setUserId("3da3109cdb3f4d9e");
        shopCartSV.queryCartOfUser(userInfo);
    }
    
    @Test
    public void updateProdNumTest(){
    	CartProd cartProd = new CartProd();
    	cartProd.setTenantId("changhong");
    	cartProd.setUserId("3da3109cdb3f4d9e");
    	cartProd.setBuyNum(6l);
    	shopCartSV.updateProdNum(cartProd);
    }
    
    
    @Test
    public void deleteCartProdTest(){
    	MultiCartProd multiCartProd=new MultiCartProd();
    	multiCartProd.setTenantId("changhong");
    	multiCartProd.setUserId("3da3109cdb3f4d9e");
    	List<String> skuIdList=new ArrayList<String>();
    	skuIdList.add("0000000000000286");
    	multiCartProd.setSkuIdList(skuIdList);
    	shopCartSV.deleteMultiProd(multiCartProd);
    }

    @Test
    public void deleteCartCache(){
        //删除购物车缓存
        ICacheClient iCacheClient = MCSClientFactory.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
        String cartUserId = IPassMcsUtils.genShopCartUserId("changhong","3da3109cdb3f4d9e");
        iCacheClient.del(cartUserId);
    }
}
