package com.ai.slp.order.api.orderrule.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.orderrule.param.OrderRuleDetailResponse;
import com.ai.slp.order.api.orderrule.param.OrderRuleRequest;
import com.ai.slp.order.api.orderrule.param.OrderRuleResponse;
/**
 * 订单规则服务
 *
 * Date: 2016年8月3日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * @author zhangzd
 */
@Path("/orderRuleService")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IOrderRuleSV {
	/**
	 * 
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author zhangzd
	 * @ApiDocMethod
	 * @ApiCode orderRuleService-0001
     * @RestRelativeURL orderRuleService/orderRuleSetting
     */
	@POST
	@Path("/orderRuleSetting")
	public OrderRuleResponse orderRuleSetting(OrderRuleRequest request) throws BusinessException,SystemException;
	/**
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author zhangzd
	 * @ApiDocMethod
	 * @ApiCode orderRuleService-0002
     * @RestRelativeURL orderRuleService/findOrderRuleDetail
     */
	@POST
	@Path("/findOrderRuleDetail")
	public OrderRuleDetailResponse findOrderRuleDetail() throws BusinessException,SystemException;
}
