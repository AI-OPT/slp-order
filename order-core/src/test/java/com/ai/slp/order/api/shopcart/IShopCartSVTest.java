package com.ai.slp.order.api.shopcart;

import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.slp.order.api.shopcart.interfaces.IShopCartSV;
import com.ai.slp.order.api.shopcart.param.CartProd;
import com.ai.slp.order.api.shopcart.param.CartProdList;
import com.ai.slp.order.api.shopcart.param.CartProdOptRes;
import com.ai.slp.order.api.shopcart.param.MultiCartProd;
import com.ai.slp.order.api.shopcart.param.UserInfo;
import com.ai.slp.order.constants.ShopCartConstants;
import com.ai.slp.order.manager.CacheClientManager;
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
        cartProd.setUserId("1414");
        cartProd.setSkuId("0000000000000923");
        cartProd.setBuyNum(1l);
        System.out.println(JSON.toJSON(cartProd));
        CartProdOptRes optRes = shopCartSV.addProd(cartProd);
        System.out.println(JSON.toJSON(optRes));
    }

    @Test
    public void showCartList(){
        UserInfo userInfo = new UserInfo();
        userInfo.setTenantId("changhong");
        userInfo.setUserId("1313");
        CartProdList cartProdList = shopCartSV.queryCartOfUser(userInfo);
        System.out.println(JSON.toJSON(cartProdList));
    }
    
    @Test
    public void updateProdNumTest(){
    	CartProd cartProd = new CartProd();
    	cartProd.setTenantId("changhong");
    	cartProd.setUserId("121213");
    	cartProd.setBuyNum(1l);
    	cartProd.setSkuId("0000000000000923");
    	shopCartSV.updateProdNum(cartProd);
    }
    
    
    @Test
    public void deleteCartProdTest(){
    	MultiCartProd multiCartProd=new MultiCartProd();
    	multiCartProd.setTenantId("changhong");
    	multiCartProd.setUserId("1313");
    	List<String> skuIdList=new ArrayList<String>();
    	skuIdList.add("0000000000000923");
    	multiCartProd.setSkuIdList(skuIdList);
    	CartProdOptRes optRes = shopCartSV.deleteMultiProd(multiCartProd);
    	System.out.println(JSON.toJSON(optRes));
    }

    @Test
    public void deleteCartCache(){
        //删除购物车缓存
        ICacheClient iCacheClient = CacheClientManager.getCacheClient(ShopCartConstants.McsParams.SHOP_CART_MCS);
        String cartUserId = IPassMcsUtils.genShopCartUserId("changhong","3da3109cdb3f4d9e");
        iCacheClient.del(cartUserId);
    }
}
