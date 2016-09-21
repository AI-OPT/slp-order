package com.ai.slp.order.api.notice.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
@Path("/refundnotice")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IRefundNoticeSV {
	
	/**
	 * 退款通知
	 * @param xmlbody
	 * @param signMsg
	 * @param header
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author zhanglh
	 * @ApiCode NOTICE_002
	 * @RestRelativeURL refundnotice/notice
	 */
	
	@POST
	@Path("/notice")
	public BaseResponse getRefundNotice(String xmlbody,String signMsg,String header) throws BusinessException,SystemException;
}
