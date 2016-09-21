package com.ai.slp.order.api.ofcorderquery.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.ofcorderquery.param.OFCOrderQueryRequest;
import com.ai.slp.order.api.ofcorderquery.param.OFCOrderQueryResponse;

/**
 * 
 */
@Path("/OFCOrder")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IOFCOrderQuerySV {
	
	/**
	 * 销售订单查询(OFC)
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode OFCORDER_QUERY_001
	 * @RestRelativeURL OFCOrder/query
	 */
	@POST
	@Path("/query")
	public OFCOrderQueryResponse query(OFCOrderQueryRequest request) throws BusinessException,SystemException;

}
