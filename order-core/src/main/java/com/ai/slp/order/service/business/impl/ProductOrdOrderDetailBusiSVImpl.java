package com.ai.slp.order.service.business.impl;

import com.ai.slp.order.api.orderDetailList.param.ProductOrderDetailListParam;
import com.ai.slp.order.api.orderDetailList.param.ProductOrderDetailListRequest;
import com.ai.slp.order.service.business.interfaces.IProductOrdOrderDetailBusiSV;

public class ProductOrdOrderDetailBusiSVImpl implements IProductOrdOrderDetailBusiSV {

    @Override
    public ProductOrderDetailListParam getPrePaymentOrdOrderDetailInfo(
            ProductOrderDetailListRequest orderDetailRequest) {
        return null;
    }

    @Override
    public ProductOrderDetailListParam getAlreadyPaidOrdOrderDetailInfo(
            ProductOrderDetailListRequest orderDetailRequest) {
        return null;
    }

    @Override
    public ProductOrderDetailListParam hasRechargePaidOrdOrderDetailInfo(
            ProductOrderDetailListRequest orderDetailRequest) {
        return null;
    }

    @Override
    public ProductOrderDetailListParam expiredPaidOrdOrderDetailInfo(
            ProductOrderDetailListRequest orderDetailRequest) {
        return null;
    }

    @Override
    public ProductOrderDetailListParam notRechargeOrdOrderDetailInfo(
            ProductOrderDetailListRequest orderDetailRequest) {
        return null;
    }

    @Override
    public ProductOrderDetailListParam RefundProcessingOrdOrderDetailInfo(
            ProductOrderDetailListRequest orderDetailRequest) {
        return null;
    }

    @Override
    public ProductOrderDetailListParam RefundSuccessfulOrdOrderDetailInfo(
            ProductOrderDetailListRequest orderDetailRequest) {
        return null;
    }

}
