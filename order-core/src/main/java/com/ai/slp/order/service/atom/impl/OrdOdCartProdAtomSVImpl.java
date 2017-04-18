package com.ai.slp.order.service.atom.impl;

import com.ai.slp.order.dao.mapper.attach.OrdOrderAttachMapper;
import com.ai.slp.order.dao.mapper.bo.OrdOdCartProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdCartProdCriteria;
import com.ai.slp.order.dao.mapper.interfaces.OrdOdCartProdMapper;
import com.ai.slp.order.service.atom.interfaces.IOrdOdCartProdAtomSV;
import com.ai.slp.order.util.DateUtils;
import com.ai.slp.order.util.SequenceUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 购物车原子操作
 * Created by jackieliu on 16/5/18.
 */
@Component
public class OrdOdCartProdAtomSVImpl implements IOrdOdCartProdAtomSV {
	
	
	@Autowired
	private OrdOdCartProdMapper ordOdCartProdMapper;
	@Autowired
	private OrdOrderAttachMapper ordOrderAttachMapper;

    /**
     * 查询用户的购物车商品明细信息
     *
     * @param tenantId
     * @param userId
     * @return
     */
    @Override
    public List<OrdOdCartProd> queryCartProdsOfUser(String tenantId, String userId) {
        OrdOdCartProdCriteria example = new OrdOdCartProdCriteria();
        example.createCriteria().andTenantIdEqualTo(tenantId)
                .andUserIdEqualTo(userId);
        return ordOdCartProdMapper.selectByExample(example);
    }

    /**
     * 添加购物车商品明细
     *
     * @param cartProd
     * @return
     */
    @Override
    public int installCartProd(OrdOdCartProd cartProd) {
        cartProd.setProdDetalId(SequenceUtil.genCartProdId());
        cartProd.setInsertTime(DateUtils.currTimeStamp());
        return ordOdCartProdMapper.insert(cartProd);
    }

    /**
     * 更新指定标识的购物车商品明细
     *
     * @param cartProd
     * @return
     */
    @Override
    public int updateCartProdById(OrdOdCartProd cartProd) {
        if (cartProd==null || cartProd.getProdDetalId()==null)
            return 0;

        return ordOdCartProdMapper.updateByPrimaryKeySelective(cartProd);
    }

    /**
     * 查询指定标识的购物车商品明细
     *
     * @param tenantId
     * @param cartProdId
     * @return
     */
    @Override
    public OrdOdCartProd queryByCartProdId(String tenantId, Long cartProdId) {
        OrdOdCartProd ordOdCartProd = ordOdCartProdMapper.selectByPrimaryKey(cartProdId);
        if (ordOdCartProd!=null && ordOdCartProd.getTenantId().equals(tenantId))
            return ordOdCartProd;
        return null;
    }

    /**
     * 删除用户中指定单品
     *
     * @param tenantId
     * @param userId
     * @param skuId
     * @return
     */
    @Override
    public int deleteByProdId(String tenantId, String userId, String skuId) {
        OrdOdCartProdCriteria example = new OrdOdCartProdCriteria();
        example.createCriteria().andTenantIdEqualTo(tenantId)
                .andUserIdEqualTo(userId)
                .andSkuIdEqualTo(skuId);
        return ordOdCartProdMapper.deleteByExample(example);
    }

    /**
     * 查询指定用户购物车中商品
     *
     * @param tenantId
     * @param userId
     * @param skuId
     * @return
     */
    @Override
    public OrdOdCartProd queryByProdOfCart(String tenantId, String userId, String skuId) {
        OrdOdCartProdCriteria example = new OrdOdCartProdCriteria();
        example.setLimitStart(0);
        example.setLimitEnd(100);
       // example.setOrderByClause("INSERT_TIME desc");
        example.createCriteria().andTenantIdEqualTo(tenantId)
                .andUserIdEqualTo(userId)
                .andSkuIdEqualTo(skuId);
        List<OrdOdCartProd> cartProdList = ordOdCartProdMapper.selectByExample(example);
        return (cartProdList==null || cartProdList.isEmpty())?null:cartProdList.get(0);
    }

	@Override
	public void updateCartProdSum(OrdOdCartProd cartProd0) {
		// TODO Auto-generated method stub
		ordOrderAttachMapper.updateCartProdSum(cartProd0);
	}
}
