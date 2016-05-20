package com.ai.slp.order.service.atom.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdCartProd;

import java.util.List;

/**
 * 购物车原子操作
 * Created by jackieliu on 16/5/18.
 */
public interface IOrdOdCartProdAtomSV {

    /**
     * 查询用户的购物车商品明细信息
     *
     * @param tenantId
     * @param userId
     * @return
     */
    public List<OrdOdCartProd> queryCartProdsOfUser(String tenantId,String userId);

    /**
     * 添加购物车商品明细
     * @param cartProd
     * @return
     */
    public int installCartProd(OrdOdCartProd cartProd);

    /**
     * 更新指定标识的购物车商品明细
     * @param cartProd
     * @return
     */
    public int updateCartProdById(OrdOdCartProd cartProd);

    /**
     * 查询指定标识的购物车商品明细
     * @param tenantId
     * @param cartProdId
     * @return
     */
    public OrdOdCartProd queryByCartProdId(String tenantId,Long cartProdId);

    /**
     * 删除用户中指定单品
     * @param tenantId
     * @param userId
     * @param skuId
     * @return
     */
    public int deleteByProdId(String tenantId,String userId,String skuId);

    /**
     * 查询指定用户购物车中商品
     * @param tenantId
     * @param userId
     * @param skuId
     * @return
     */
    public OrdOdCartProd queryByProdOfCart(String tenantId,String userId,String skuId);
}
