package com.ai.slp.order.api.aftersaleorder.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.aftersaleorder.param.OrderOFCBackRequest;
import com.ai.slp.order.api.aftersaleorder.param.OrderReturnRequest;

/**
 * 售后订单处理
 * @date 2016年8月10日 
 * @author caofz
 */
@Path("/aftersaleorder")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IOrderAfterSaleSV {
	
	
	/**
	 * 订单退货
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode ORDER_BACK_001
	 * @RestRelativeURL  aftersaleorder/back
	 */
	@POST
	@Path("/back")
	public BaseResponse back(OrderReturnRequest request) throws BusinessException,SystemException;
	
	/**
	 * 订单换货
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode ORDER_EXCHANGE_001
	 * @RestRelativeURL aftersaleorder/exchange
	 */
	@POST
	@Path("/exchange")
	public BaseResponse exchange(OrderReturnRequest request) throws BusinessException,SystemException;
	
	
	/**
	 * 订单退款
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode ORDER_REFUND_001
	 * @RestRelativeURL aftersaleorder/refund
	 */
	@POST
	@Path("/refund")
	public BaseResponse refund(OrderReturnRequest request) throws BusinessException,SystemException;
	
	
	/**
	 * 售后订单状态通知(OFC)
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode ORDER_BACKSATE_OFC_001
	 * @RestRelativeURL aftersaleorder/backStateOFC
	 */
	@POST
	@Path("/backStateOFC")
	public BaseResponse backStateOFC(OrderOFCBackRequest request) throws BusinessException,SystemException;
	
	
}
