package com.ai.slp.order.api.orderstate.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.orderstate.param.WaitRebateRequest;
import com.ai.slp.order.api.orderstate.param.WaitRebateResponse;
import com.ai.slp.order.api.orderstate.param.WaitSellReceiveSureRequest;
import com.ai.slp.order.api.orderstate.param.WaitSellReceiveSureResponse;
/**
 * 修改订单状态接口
 *
 * Date: 2016年10月20日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * @author zhangzd
 */
@Path("/OrderStateService")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IOrderStateServiceSV {
	/**
	 * 待卖家收货确认状态修改 状态修改为 23
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author zhangzd
	 * @ApiDocMethod
	 * @ApiCode OrderStateService-0001
     * @RestRelativeURL OrderStateService/updateWaitSellRecieveSureState
     */
	@POST
	@Path("/updateWaitSellRecieveSureState")
	public WaitSellReceiveSureResponse updateWaitSellRecieveSureState(WaitSellReceiveSureRequest request)throws BusinessException,SystemException;
	/**
	 * 待退费状态修改 状态修改为 31
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author zhangzd
	 * @ApiDocMethod
	 * @ApiCode OrderStateService-0002
     * @RestRelativeURL OrderStateService/updateWaitRebateState
     */
	@POST
	@Path("/updateWaitRebateState")
	public WaitRebateResponse updateWaitRebateState(WaitRebateRequest request)throws BusinessException,SystemException;
	
}
