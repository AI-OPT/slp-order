package com.ai.slp.order.service.business.impl;

import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.slp.order.api.shopcart.param.CartProdOptRes;
import com.ai.slp.order.constants.MallIPassConstants;
import com.ai.slp.order.constants.ShopCartConstants;
import com.ai.slp.order.service.business.interfaces.IShopCartBusiSV;
import com.ai.slp.order.util.IPassMcsUtils;
import com.ai.slp.order.vo.ShopCartCachePointsVo;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jackieliu on 16/5/17.
 */
@Service
@Transactional
public class ShopCartBusiSVImpl implements IShopCartBusiSV {
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
            //添加缓存
        }
        //查询概览信息
        String cartPrefix = iCacheClient.hget(IPassMcsUtils.genShopCartUserId(tenantId,userId)
                ,ShopCartConstants.CacheParams.CART_POINTS);
        CartProdOptRes cartProdOptRes = new CartProdOptRes();
        ShopCartCachePointsVo pointsVo = JSON.parseObject(cartPrefix, ShopCartCachePointsVo.class);
        BeanUtils.copyProperties(cartProdOptRes,pointsVo);
        //从数据库中查询
        return cartProdOptRes;
    }
}
