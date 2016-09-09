package com.ai.slp.order.api.aftersaleorder.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.aftersaleorder.param.OrderJuageRequest;
import com.ai.slp.order.api.aftersaleorder.param.OrderJuageResponse;

@Path("/aftersaleorder")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IOrderAfterSaleJudgeSV {
	
	/**
	 * 判断该商品的业务类型
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode ORDER_JUDGE_001
	 * @RestRelativeURL aftersaleorder/judge
	 */
	@POST
	@Path("/judge")
	public OrderJuageResponse judge(OrderJuageRequest request) throws BusinessException,SystemException;

}
