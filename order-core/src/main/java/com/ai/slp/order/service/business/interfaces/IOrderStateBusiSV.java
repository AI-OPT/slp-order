package com.ai.slp.order.service.business.interfaces;

import com.ai.slp.order.api.orderstate.param.WaitRebateRequest;
import com.ai.slp.order.api.orderstate.param.WaitRebateResponse;
import com.ai.slp.order.dao.mapper.bo.OrdOdLogistics;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;

public interface IOrderStateBusiSV {
	public void updateWaitSellRecieveSureState(OrdOrder ordOrder,
			OrdOdLogistics ordOdLogistics);

	public WaitRebateResponse updateWaitRebateState(WaitRebateRequest request);
}
