package com.ai.slp.order.service.business.impl;

import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.slp.order.api.shopcart.param.CartProdOptRes;
import com.ai.slp.order.constants.MallIPassConstants;
import com.ai.slp.order.constants.ShopCartConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdCartProd;
import com.ai.slp.order.service.atom.interfaces.IOrdOdCartProdAtomSV;
import com.ai.slp.order.service.business.interfaces.IShopCartBusiSV;
import com.ai.slp.order.util.IPassMcsUtils;
import com.ai.slp.order.vo.ShopCartCachePointsVo;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jackieliu on 16/5/17.
 */
@Service
@Transactional
public class ShopCartBusiSVImpl implements IShopCartBusiSV {
    private static Logger logger = LoggerFactory.getLogger(ShopCartBusiSVImpl.class);
    @Autowired
    IOrdOdCartProdAtomSV cartProdAtomSV;
    /**
     * 查询用户购物车概览
     *
     * @param tenantId
     * @param userId
     * @return
     */
    @Override
    public CartProdOptRes queryCartOptions(String tenantId, String userId) {
        //查询缓存中是否存在
        ICacheClient iCacheClient = MCSClientFactory.getCacheClient(MallIPassConstants.SHOP_CART_MCS);
        String cartUserId = IPassMcsUtils.genShopCartUserId(tenantId,userId);
        //若不存在购物车信息
        if (!iCacheClient.exists(cartUserId)){
            //从数据库中查询,建立缓存
            addShopCartCache(tenantId,userId);
        }
        //查询概览信息
        String cartPrefix = iCacheClient.hget(IPassMcsUtils.genShopCartUserId(tenantId,userId)
                ,ShopCartConstants.CacheParams.CART_POINTS);
        CartProdOptRes cartProdOptRes = new CartProdOptRes();
        ShopCartCachePointsVo pointsVo = JSON.parseObject(cartPrefix, ShopCartCachePointsVo.class);
        BeanUtils.copyProperties(cartProdOptRes,pointsVo);
        return cartProdOptRes;
    }

    /**
     * 将数据库中数据加载到缓存中
     *
     * @param tenantId
     * @param userId
     */
    private void addShopCartCache(String tenantId,String userId){
        ICacheClient iCacheClient = MCSClientFactory.getCacheClient(MallIPassConstants.SHOP_CART_MCS);
        String cartUserId = IPassMcsUtils.genShopCartUserId(tenantId,userId);
        //查询用户购物车商品列表
        List<OrdOdCartProd> cartProdList = cartProdAtomSV.queryCartProdsOfUser(tenantId,userId);
        int prodTotal = 0;
        //循环建立购物车单品缓存
        for (OrdOdCartProd cartProd:cartProdList){
            iCacheClient.hset(cartUserId, cartProd.getSkuId(),JSON.toJSONString(cartProd));
            prodTotal += cartProd.getBuySum();
        }
        //添加概览信息
        ShopCartCachePointsVo cartProdPoints = new ShopCartCachePointsVo();
        cartProdPoints.setProdNum(cartProdList.size());
        cartProdPoints.setProdTotal(prodTotal);
        iCacheClient.hset(cartUserId, ShopCartConstants.CacheParams.CART_POINTS,JSON.toJSONString(cartProdPoints));
    }
}
