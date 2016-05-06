package com.ai.slp.order.api.orderDetailList.interfaces;

import com.ai.slp.order.api.orderDetailList.param.ProductOrderDetailListRequest;
import com.ai.slp.order.api.orderDetailList.param.ProductOrderDetailListParam;

public interface IProductOrderDetailListSV {

    @interface GetOrdOrderDetailInfo{}
    /**
     * 订单详情-代付款
     * @param orderDetailRequest
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public ProductOrderDetailListParam getPrePaymentOrdOrderDetailInfo(ProductOrderDetailListRequest orderDetailRequest);
    
    @interface GetAlreadyPaidOrdOrderDetailInfo{}
    /**
     * 订单详情-已付款
     * @param orderDetailRequest
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public ProductOrderDetailListParam getAlreadyPaidOrdOrderDetailInfo(ProductOrderDetailListRequest orderDetailRequest);
    
    @interface HasRechargePaidOrdOrderDetailInfo{}
    /**
     * 已充值
     * @param orderDetailRequest
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public ProductOrderDetailListParam  hasRechargePaidOrdOrderDetailInfo(ProductOrderDetailListRequest orderDetailRequest);
    @interface ExpiredPaidOrdOrderDetailInfo{}
    /**
     * 已过期
     * @param orderDetailRequest
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public ProductOrderDetailListParam expiredPaidOrdOrderDetailInfo(ProductOrderDetailListRequest orderDetailRequest);
    @interface NotRechargeOrdOrderDetailInfo{}
    /**
     * 未充值
     * @param orderDetailRequest
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public ProductOrderDetailListParam notRechargeOrdOrderDetailInfo(ProductOrderDetailListRequest orderDetailRequest);
    /**
     * 退款处理中
     * @param orderDetailRequest
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public ProductOrderDetailListParam RefundProcessingOrdOrderDetailInfo(ProductOrderDetailListRequest orderDetailRequest);
    /**
     * 退款成功
     * @param orderDetailRequest
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public ProductOrderDetailListParam RefundSuccessfulOrdOrderDetailInfo(ProductOrderDetailListRequest orderDetailRequest);
}
