package com.ai.slp.order.api.deliveryorderprint.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintRequest;
import com.ai.slp.order.api.deliveryorderprint.param.DeliveryOrderPrintResponse;

/**
 * 提货单打印服务
 * @date 2016年8月10日 
 * @author caofz
 */
@Path("/deliveryorder")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IDeliveryOrderPrintSV {
	
	/**
	 * 合并打印
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode 
	 * @RestRelativeURL /deliveryorder/print
	 */
	@POST
	@Path("/print")
	public DeliveryOrderPrintResponse print(DeliveryOrderPrintRequest request) throws BusinessException,SystemException;
	
	/**
	 * 不合并打印
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode 
	 * @RestRelativeURL /deliveryorder/noMergePrint
	 */
	@POST
	@Path("/noMergePrint")
	public DeliveryOrderPrintResponse noMergePrint(DeliveryOrderPrintRequest request) throws BusinessException,SystemException;
}
