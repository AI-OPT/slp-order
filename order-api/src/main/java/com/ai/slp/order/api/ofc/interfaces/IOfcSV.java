package com.ai.slp.order.api.ofc.interfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.slp.order.api.ofc.params.OrdOdFeeTotalVo;
import com.ai.slp.order.api.ofc.params.OrdOdLogisticsVo;
import com.ai.slp.order.api.ofc.params.OrdOdProdVo;
import com.ai.slp.order.api.ofc.params.OrdOrderOfcVo;

/**
 * Ofc服务
 */
@Path("/ofcservice")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IOfcSV {

	/**
	 * 保存订单信息
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
	public void insertOrdOrder(OrdOrderOfcVo request)throws BusinessException,SystemException;
	
	/**
	 * 保存订单出货表
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode Ofc_002
	 * @RestRelativeURL ofcservice/insertOrdOdLogistics
	 */
	@POST
	@Path("/insertOrdOdLogistics")
	public void insertOrdOdLogistics(OrdOdLogisticsVo request)throws BusinessException,SystemException;
	
	/**
	 * 保存订单费用表
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode Ofc_003
	 * @RestRelativeURL ofcservice/insertOrdOdFeeTotal
	 */
	@POST
	@Path("/insertOrdOdFeeTotal")
	public void insertOrdOdFeeTotal(OrdOdFeeTotalVo request)throws BusinessException,SystemException;
	
	/**
	 * 保存订单商品表
	 * @param request
	 * @return
	 * @throws BusinessException
	 * @throws SystemException
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode Ofc_004
	 * @RestRelativeURL ofcservice/insertOrdOdProd
	 */
	@POST
	@Path("/insertOrdOdProd")
	public void insertOrdOdProd(OrdOdProdVo request)throws BusinessException,SystemException;
	
	
}
