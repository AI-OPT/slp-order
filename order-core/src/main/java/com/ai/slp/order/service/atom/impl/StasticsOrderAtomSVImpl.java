package com.ai.slp.order.service.atom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.api.stasticsorder.param.StasticsOrderRequest;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.dao.mapper.interfaces.OrdOrderMapper;
import com.ai.slp.order.service.atom.interfaces.IStasticsOrderAtomSV;

@Component
public class StasticsOrderAtomSVImpl implements IStasticsOrderAtomSV {
	@Autowired
	OrdOrderMapper ordOrderMapper;

	@Override
	public PageInfo<OrdOrder> getStasticOrdPage(StasticsOrderRequest request) {
		OrdOrderCriteria example = new OrdOrderCriteria();
		OrdOrderCriteria.Criteria param = example.createCriteria();
		if (!StringUtil.isBlank(request.getTenantId())) {
			param.andTenantIdEqualTo(request.getTenantId());
		}
		if (request.getOrderId() != null) {
			param.andOrderIdEqualTo(request.getOrderId());
		}
		//用户
		if(!StringUtil.isBlank(request.getUserId())){
			param.andUserIdEqualTo(request.getUserId());
		}
		if (request.getOrderTimeStart() != null && request.getOrderTimeEnd() != null) {
			param.andOrderTimeBetween(request.getOrderTimeStart(), request.getOrderTimeEnd());
		}
		if (request.getOrderTimeStart() != null && request.getOrderTimeEnd() == null) {
			param.andOrderTimeGreaterThanOrEqualTo(request.getOrderTimeStart());
		}
		if (request.getOrderTimeStart() == null && request.getOrderTimeEnd() != null) {
			param.andOrderTimeLessThanOrEqualTo(request.getOrderTimeEnd());
		}
		if (!StringUtil.isBlank(request.getState())) {
			param.andStateEqualTo(request.getState());
		}
		//销售商
		if(!StringUtil.isBlank(request.getSupplierId())){
			param.andSupplierIdEqualTo(request.getSupplierId());
		}
		// 统计查询条目数
		int count = ordOrderMapper.countByExample(example);

		example.setLimitStart((request.getPageNo() - 1) * request.getPageSize());
		example.setLimitEnd(request.getPageSize());

		PageInfo<OrdOrder> stasticPage = new PageInfo<OrdOrder>();
		stasticPage.setPageSize(request.getPageSize());
		stasticPage.setPageNo(request.getPageNo());
		stasticPage.setResult(ordOrderMapper.selectByExample(example));
		stasticPage.setCount(count);
		return stasticPage;
	}

}
