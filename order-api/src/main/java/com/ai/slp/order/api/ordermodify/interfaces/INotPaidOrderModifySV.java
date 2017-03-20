package com.ai.slp.order.api.ordermodify.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.ordermodify.param.OrderModifyRequest;

/**
 * 未支付的订单金额修改服务
 * @date 2016年8月8日 
 * @author caofz
 */
@Path("/notpayorder")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface INotPaidOrderModifySV {
	 
	
	 /**
	  * 未支付订单修改
	  * @param request
	  * @return
	  * @throws BusinessException
	  * @throws SystemException
	  * @author caofz
	  * @ApiDocMethod
	  * @ApiCode ORDER_MODIFY_001
	  * @RestRelativeURL notpayorder/modify
	  */
	 @POST
	 @Path("/modify")
	 public BaseResponse modify(OrderModifyRequest request)throws BusinessException,SystemException; 

}
