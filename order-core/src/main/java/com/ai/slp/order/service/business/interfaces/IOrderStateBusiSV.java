package com.ai.slp.order.service.business.interfaces;

import com.ai.slp.order.api.orderstate.param.WaitRebateRequest;
import com.ai.slp.order.api.orderstate.param.WaitRebateResponse;
import com.ai.slp.order.api.orderstate.param.WaitSellReceiveSureRequest;
import com.ai.slp.order.api.orderstate.param.WaitSellReceiveSureResponse;

public interface IOrderStateBusiSV {
	public WaitSellReceiveSureResponse updateWaitSellRecieveSureState(WaitSellReceiveSureRequest request);

	public WaitRebateResponse updateWaitRebateState(WaitRebateRequest request);
}
