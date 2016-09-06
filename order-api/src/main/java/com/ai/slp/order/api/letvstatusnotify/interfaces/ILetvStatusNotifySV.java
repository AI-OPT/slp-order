package com.ai.slp.order.api.letvstatusnotify.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.letvstatusnotify.param.LetvStatusNotifyRequest;

/**
 * 订单状态通知(api)
 * @author caofz
 *
 */
@Path("/letvservice")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
public interface ILetvStatusNotifySV {
	
	
	/**
	 * 发货打印查看
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode ORDER_LETV_001
	 * @RestRelativeURL letvservice/statusnotify
	 */
	@POST
	@Path("/statusnotify")
	BaseResponse statusnotify(LetvStatusNotifyRequest request) throws SystemException,BusinessException;
	
}
