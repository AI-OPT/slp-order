package com.ai.slp.order.service.atom.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.warmorder.param.OrderWarmRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.dao.mapper.interfaces.OrdOrderMapper;
import com.ai.slp.order.service.atom.interfaces.IOrdWarmAtomSV;
@Component
public class OrdWarmAtomSVImpl implements IOrdWarmAtomSV {
	 @Autowired
	 OrdOrderMapper ordOrderMapper;
	@Override
	public PageInfo<OrdOrder> selectWarmOrdPage(OrderWarmRequest request) {
		  OrdOrderCriteria example = new OrdOrderCriteria();
		  OrdOrderCriteria.Criteria param = example.createCriteria();
        if(!StringUtil.isBlank(request.getTenantId())){
        	param.andTenantIdEqualTo(request.getTenantId());
        }
        if (request.getOrderTimeStart() != null && request.getOrderTimeEnd() != null) {
        	param.andOrderTimeBetween(request.getOrderTimeStart(),request.getOrderTimeEnd());
        }
        if (request.getOrderTimeStart() != null && request.getOrderTimeEnd() == null) {
        	param.andOrderTimeGreaterThanOrEqualTo(request.getOrderTimeStart());
        }
        if (request.getOrderTimeStart() == null && request.getOrderTimeEnd() != null) {
        	param.andOrderTimeLessThanOrEqualTo(request.getOrderTimeEnd());
        }
        //预警订单查询的是待支付状态的订单
        param.andStateEqualTo(OrdersConstants.OrdOrder.State.WAIT_PAY);
        //预警条件IF_WARNING
        param.andIfWarningEqualTo(OrdersConstants.IfWarning.result.WARING);
        //统计查询条目数
        int count = ordOrderMapper.countByExample(example);
        
        example.setLimitStart((request.getPageNo()-1)*request.getPageSize());
        example.setLimitEnd(request.getPageSize());
        
        PageInfo<OrdOrder> warmPage = new PageInfo<OrdOrder>();
        warmPage.setPageSize(request.getPageSize());
        warmPage.setPageNo(request.getPageNo());
        warmPage.setResult(ordOrderMapper.selectByExample(example));
        warmPage.setCount(count);
        return warmPage;
    }
	@Override
	public OrdOrder selectWarmOrde(String tenantId, long orderid) {
		  OrdOrderCriteria example = new OrdOrderCriteria();
		  OrdOrderCriteria.Criteria param = example.createCriteria();
        if(!StringUtil.isBlank(tenantId)){
        	param.andTenantIdEqualTo(tenantId);
        }
        param.andOrderIdEqualTo(orderid);
        List<OrdOrder> list = ordOrderMapper.selectByExample(example);
        if(!CollectionUtil.isEmpty(list)){
        	return list.get(0);
        }
        return null;
	}

}
