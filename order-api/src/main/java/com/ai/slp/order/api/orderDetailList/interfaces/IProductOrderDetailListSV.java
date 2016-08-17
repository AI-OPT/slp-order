package com.ai.slp.order.api.orderDetailList.interfaces;

import com.ai.slp.order.api.orderDetailList.param.ProductOrderDetailListRequest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ai.slp.order.api.orderDetailList.param.ProductOrderDetailListParam;

@Path("/prodorder")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public interface IProductOrderDetailListSV {

    //@interface GetOrdOrderDetailInfo{}
    /**
     * 订单详情-代付款
     * @param orderDetailRequest
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     * @RestRelativeURL prodorder/getPrePaymentOrdOrderDetailInfo
     */
	@POST
	@Path("/getPrePaymentOrdOrderDetailInfo")
    public ProductOrderDetailListParam getPrePaymentOrdOrderDetailInfo(ProductOrderDetailListRequest orderDetailRequest);
    
    //@interface GetAlreadyPaidOrdOrderDetailInfo{}
    /**
     * 订单详情-已付款
     * @param orderDetailRequest
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     * @RestRelativeURL prodorder/getAlreadyPaidOrdOrderDetailInfo
     */
	@POST
	@Path("/getAlreadyPaidOrdOrderDetailInfo")
    public ProductOrderDetailListParam getAlreadyPaidOrdOrderDetailInfo(ProductOrderDetailListRequest orderDetailRequest);
    
    //@interface HasRechargePaidOrdOrderDetailInfo{}
    /**
     * 已充值
     * @param orderDetailRequest
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     * @RestRelativeURL prodorder/hasRechargePaidOrdOrderDetailInfo
     */
	@POST
	@Path("/hasRechargePaidOrdOrderDetailInfo")
    public ProductOrderDetailListParam  hasRechargePaidOrdOrderDetailInfo(ProductOrderDetailListRequest orderDetailRequest);
    //@interface ExpiredPaidOrdOrderDetailInfo{}
    /**
     * 已过期
     * @param orderDetailRequest
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     * @RestRelativeURL prodorder/expiredPaidOrdOrderDetailInfo
     */
	@POST
	@Path("/expiredPaidOrdOrderDetailInfo")
    public ProductOrderDetailListParam expiredPaidOrdOrderDetailInfo(ProductOrderDetailListRequest orderDetailRequest);
    //@interface NotRechargeOrdOrderDetailInfo{}
    /**
     * 未充值
     * @param orderDetailRequest
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     * @RestRelativeURL prodorder/notRechargeOrdOrderDetailInfo
     */
	@POST
	@Path("/notRechargeOrdOrderDetailInfo")
    public ProductOrderDetailListParam notRechargeOrdOrderDetailInfo(ProductOrderDetailListRequest orderDetailRequest);
    /**
     * 退款处理中
     * @param orderDetailRequest
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     * @RestRelativeURL prodorder/RefundProcessingOrdOrderDetailInfo
     */
	@POST
	@Path("/RefundProcessingOrdOrderDetailInfo")
    public ProductOrderDetailListParam RefundProcessingOrdOrderDetailInfo(ProductOrderDetailListRequest orderDetailRequest);
    /**
     * 退款成功
     * @param orderDetailRequest
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     * @RestRelativeURL prodorder/RefundSuccessfulOrdOrderDetailInfo
     */
	@POST
	@Path("/RefundSuccessfulOrdOrderDetailInfo")
    public ProductOrderDetailListParam RefundSuccessfulOrdOrderDetailInfo(ProductOrderDetailListRequest orderDetailRequest);
}
