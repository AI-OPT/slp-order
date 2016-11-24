package com.ai.slp.order.api.ofc.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.api.ofc.params.OfcCodeRequst;
import com.ai.slp.order.api.ofc.params.OrdOdProdVo;
import com.ai.slp.order.api.ofc.params.OrderOfcVo;

/**
 * Ofc服务
 */
@Path("/ofcservice")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IOfcSV {

	/**
	 * 保存订单信息
	 * 
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode Ofc_001
	 * @RestRelativeURL ofcservice/insertOrdOrder
	 */
	@POST
	@Path("/insertOrdOrder")
	public void insertOrdOrder(OrderOfcVo request) throws SystemException;

	/**
	 * 保存订单商品表
	 * 
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode Ofc_002
	 * @RestRelativeURL ofcservice/insertOrdOdProd
	 */
	@POST
	@Path("/insertOrdOdProd")
	public void insertOrdOdProd(OrdOdProdVo request) throws SystemException;

	/**
	 * 解析ofc订单数据编码
	 * 
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode Ofc_003
	 * @RestRelativeURL ofcservice/parseOfcCode
	 */
	@POST
	@Path("/parseOfcCode")
	public String parseOfcCode(OfcCodeRequst request) throws BusinessException, SystemException;

}
