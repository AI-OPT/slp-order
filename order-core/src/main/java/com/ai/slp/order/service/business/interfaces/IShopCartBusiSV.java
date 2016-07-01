package com.ai.slp.order.service.business.interfaces;

import com.ai.opt.base.exception.BusinessException;
import com.ai.slp.order.api.shopcart.param.CartProd;
import com.ai.slp.order.api.shopcart.param.CartProdInfo;
import com.ai.slp.order.api.shopcart.param.CartProdOptRes;

import java.util.List;

/**
 * 购物车业务操作类型
 * Created by jackieliu on 16/5/17.
 */
public interface IShopCartBusiSV {
    /**
     * 查询用户购物车概览
     *
     * @param tenantId
     * @param userId
     * @return
     */
    public CartProdOptRes queryCartOptions(String tenantId, String userId);

    /**
     * 购物车添加商品
     * @param cartProd
     * @return
     */
    public CartProdOptRes addCartProd(CartProd cartProd) throws BusinessException;

    /**
     * 更新购物车中商品数量
     * @param cartProd
     * @return
     */
    public CartProdOptRes updateCartProd(CartProd cartProd);

    /**
     * 删除购物车中商品
     * @param tenantId
     * @param userId
     * @param skuIdList
     * @return
     */
    public CartProdOptRes deleteCartProd(String tenantId,String userId,List<String> skuIdList);

    /**
     * 查询用户购物车中商品的信息
     * @param tenantId
     * @param userId
     * @return
     */
    public List<CartProdInfo> queryCartProdOfUser(String tenantId, String userId);
}
