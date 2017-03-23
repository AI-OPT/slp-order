package com.ai.slp.order.service.atom.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.dao.mapper.interfaces.OrdOrderMapper;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;

@Component
public class OrdOrderAtomSVImpl implements IOrdOrderAtomSV {
	
	@Autowired
	private OrdOrderMapper ordOrderMapper;

    @Override
    public List<OrdOrder> selectByExample(OrdOrderCriteria example) {
        return ordOrderMapper.selectByExample(example);
    }

    @Override
    public int countByExample(OrdOrderCriteria example) {
        return ordOrderMapper.countByExample(example);
    }

    @Override
    public int insertSelective(OrdOrder record) {
        return ordOrderMapper.insertSelective(record);
    }

    @Override
    public OrdOrder selectByOrderId(String tenantId, long orderId) {
        OrdOrderCriteria example = new OrdOrderCriteria();
        example.createCriteria().andTenantIdEqualTo(tenantId).andOrderIdEqualTo(orderId);
        List<OrdOrder> ordOrders = ordOrderMapper.selectByExample(example);
        return ordOrders == null || ordOrders.isEmpty() ? null : ordOrders.get(0);
    }

    @Override
    public int updateById(OrdOrder ordOrder) {
        return ordOrderMapper.updateByPrimaryKey(ordOrder);
    }

	@Override
	public List<OrdOrder> selectChildOrder(String tenantId, long parentId) {
		 OrdOrderCriteria example = new OrdOrderCriteria();
	     example.createCriteria().andTenantIdEqualTo(tenantId).andParentOrderIdEqualTo(parentId);
	     return ordOrderMapper.selectByExample(example);
	}

	@Override
	public void updateStateByOrderId(String tenantId, Long orderId,String state) {
		OrdOrder record = new OrdOrder();
		record.setState(state);
		record.setOrderId(orderId);
		ordOrderMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<OrdOrder> selectByBatchNo(long orderId,String tenantId, long batchNo) {
		OrdOrderCriteria example = new OrdOrderCriteria();
        example.createCriteria().andTenantIdEqualTo(tenantId).andBatchNoEqualTo(batchNo).andOrderIdNotEqualTo(orderId)
        .andCusServiceFlagEqualTo(OrdersConstants.OrdOrder.cusServiceFlag.NO);
        return ordOrderMapper.selectByExample(example);
	}
	
	@Override
	public List<OrdOrder> selectMergeOrderByBatchNo(long orderId,String tenantId, long batchNo,String state) {
		OrdOrderCriteria example = new OrdOrderCriteria();
		example.createCriteria().andTenantIdEqualTo(tenantId).andBatchNoEqualTo(batchNo).andOrderIdNotEqualTo(orderId)
		.andCusServiceFlagEqualTo(OrdersConstants.OrdOrder.cusServiceFlag.NO).andStateEqualTo(OrdersConstants.OrdOrder.State.WAIT_SEND);
		return ordOrderMapper.selectByExample(example);
	}

	@Override
	public int updateByExampleSelective(OrdOrder record, OrdOrderCriteria example) {
		return ordOrderMapper.updateByExampleSelective(record, example);
	}
	
	@Override
	public List<OrdOrder> selectOrderByOrigOrderId(long externalOrderId, long orderId) {
		OrdOrderCriteria example=new OrdOrderCriteria();
		OrdOrderCriteria.Criteria criteria = example.createCriteria();
		criteria.andOrderIdEqualTo(externalOrderId);
		criteria.andOrigOrderIdEqualTo(orderId);
	    return ordOrderMapper.selectByExample(example);
	}

	@Override
	public List<OrdOrder> selectSaleOrder(String tenantId, long orderId) {
		// TODO Auto-generated method stub
		OrdOrderCriteria example=new OrdOrderCriteria();
		OrdOrderCriteria.Criteria criteria = example.createCriteria();
		criteria.andOrigOrderIdEqualTo(orderId);
		criteria.andTenantIdEqualTo(tenantId);
		return ordOrderMapper.selectByExample(example);
	}

	@Override
	public int updateByPrimaryKeySelective(OrdOrder record) {
		// TODO Auto-generated method stub
		return ordOrderMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<OrdOrder> selectOtherOrders(OrdOrder ordOrder) {
		OrdOrderCriteria example = new OrdOrderCriteria();
        OrdOrderCriteria.Criteria criteria = example.createCriteria();
        criteria.andTenantIdEqualTo(ordOrder.getTenantId()).andOrderIdNotEqualTo(ordOrder.getOrderId());
        criteria.andParentOrderIdEqualTo(ordOrder.getParentOrderId());
        criteria.andBusiCodeEqualTo(OrdersConstants.OrdOrder.BusiCode.NORMAL_ORDER);
		return ordOrderMapper.selectByExample(example);
	}

	@Override
	public List<OrdOrder> selectNotPayOrders(String tenantId, long orderId) {
		// TODO Auto-generated method stub
		OrdOrderCriteria example=new OrdOrderCriteria();
    	OrdOrderCriteria.Criteria criteria = example.createCriteria();
    	criteria.andTenantIdEqualTo(tenantId);
    	criteria.andBusiCodeEqualTo(OrdersConstants.OrdOrder.BusiCode.NORMAL_ORDER);
    	criteria.andStateEqualTo(OrdersConstants.OrdOrder.State.WAIT_PAY);
    	criteria.andOrderIdEqualTo(orderId);
		return ordOrderMapper.selectByExample(example);
	}

	@Override
	public List<OrdOrder> selectNotPayOrdersByTime(Timestamp time) {
		// TODO Auto-generated method stub
		OrdOrderCriteria example = new OrdOrderCriteria();
        OrdOrderCriteria.Criteria criteria = example.createCriteria();
        criteria.andOrderTimeLessThan(time);
        criteria.andStateEqualTo(OrdersConstants.OrdOrder.State.WAIT_PAY);
        criteria.andBusiCodeEqualTo(OrdersConstants.OrdOrder.BusiCode.NORMAL_ORDER);
        example.setLimitStart(0);
        example.setLimitEnd(100);
		return ordOrderMapper.selectByExample(example);
	}
	
}
