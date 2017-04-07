package com.ai.slp.order.api.stasticsorder.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.stasticsorder.param.StasticOrderResponse;
import com.ai.slp.order.api.stasticsorder.param.StasticsOrderRequest;
@Path("/stasticOrder")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IStasticsOrderSV {
	/**
	 * 统计查询(已废弃)
	 * @param orderListRequest
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author zhanglh
	 * @ApiCode ORD_COUNT_001
     * @RestRelativeURL stasticOrder/stasticOrdQueryPage
	 */
	@POST
	@Path("/stasticOrdQueryPage")
	StasticOrderResponse queryStasticOrdPage(StasticsOrderRequest request) throws BusinessException, SystemException;
}
