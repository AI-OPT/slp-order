package com.ai.slp.order.api.ordertradecenter.param;

import java.util.List;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 订单提交请求参数 Date: 2016年5月13日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
public class OrderTradeCenterRequest extends BaseInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 订单基础信息
     */
    private OrdOrder ordOrder;

    /**
     * 商品信息
     */
    private List<OrdProduct> ordProductList;

    /**
     * 发票信息
     */
    private OrdInvoice ordInvoice;

    /**
     * 配送信息
     */
    private OrdLogistics ordLogistics;

    /**
     * 费用信息
     */
    private OrdFee ordFee;

    /**
     * 订单支付信息
     */
    private OrdBalacneIf ordBalacneIf;

    /**
     * 订单拓展信息
     */
    private OrdExtend ordExtend;

    public OrdOrder getOrdOrder() {
        return ordOrder;
    }

    public List<OrdProduct> getOrdProductList() {
        return ordProductList;
    }

    public OrdInvoice getOrdInvoice() {
        return ordInvoice;
    }

    public OrdLogistics getOrdLogistics() {
        return ordLogistics;
    }

    public OrdFee getOrdFee() {
        return ordFee;
    }

    public OrdBalacneIf getOrdBalacneIf() {
        return ordBalacneIf;
    }

    public OrdExtend getOrdExtend() {
        return ordExtend;
    }

    public void setOrdOrder(OrdOrder ordOrder) {
        this.ordOrder = ordOrder;
    }

    public void setOrdProductList(List<OrdProduct> ordProductList) {
        this.ordProductList = ordProductList;
    }

    public void setOrdInvoice(OrdInvoice ordInvoice) {
        this.ordInvoice = ordInvoice;
    }

    public void setOrdLogistics(OrdLogistics ordLogistics) {
        this.ordLogistics = ordLogistics;
    }

    public void setOrdFee(OrdFee ordFee) {
        this.ordFee = ordFee;
    }

    public void setOrdBalacneIf(OrdBalacneIf ordBalacneIf) {
        this.ordBalacneIf = ordBalacneIf;
    }

    public void setOrdExtend(OrdExtend ordExtend) {
        this.ordExtend = ordExtend;
    }

}
