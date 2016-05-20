package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;
import java.util.List;

public class OrdOrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单基本信息
     */
    private OrdBaseInfo ordBaseInfo;

    /**
     * 商品信息
     */
    private List<OrdProductInfo> ordProductInfoList;

    /**
     * 发票信息
     */
    private OrdInvoiceInfo ordInvoiceInfo;

    /**
     * 配送信息
     */
    private OrdLogisticsInfo ordLogisticsInfo;

    /**
     * 费用信息
     */
    private OrdFeeInfo ordFeeInfo;

    /**
     * 订单支付信息
     */
    private OrdBalacneIfInfo ordBalacneIfInfo;

    /**
     * 订单拓展信息
     */
    private OrdExtendInfo ordExtendInfo;

    public OrdBaseInfo getOrdBaseInfo() {
        return ordBaseInfo;
    }

    public List<OrdProductInfo> getOrdProductInfoList() {
        return ordProductInfoList;
    }

    public OrdInvoiceInfo getOrdInvoiceInfo() {
        return ordInvoiceInfo;
    }

    public OrdLogisticsInfo getOrdLogisticsInfo() {
        return ordLogisticsInfo;
    }

    public OrdFeeInfo getOrdFeeInfo() {
        return ordFeeInfo;
    }

    public OrdBalacneIfInfo getOrdBalacneIfInfo() {
        return ordBalacneIfInfo;
    }

    public OrdExtendInfo getOrdExtendInfo() {
        return ordExtendInfo;
    }

    public void setOrdBaseInfo(OrdBaseInfo ordBaseInfo) {
        this.ordBaseInfo = ordBaseInfo;
    }

    public void setOrdProductInfoList(List<OrdProductInfo> ordProductInfoList) {
        this.ordProductInfoList = ordProductInfoList;
    }

    public void setOrdInvoiceInfo(OrdInvoiceInfo ordInvoiceInfo) {
        this.ordInvoiceInfo = ordInvoiceInfo;
    }

    public void setOrdLogisticsInfo(OrdLogisticsInfo ordLogisticsInfo) {
        this.ordLogisticsInfo = ordLogisticsInfo;
    }

    public void setOrdFeeInfo(OrdFeeInfo ordFeeInfo) {
        this.ordFeeInfo = ordFeeInfo;
    }

    public void setOrdBalacneIfInfo(OrdBalacneIfInfo ordBalacneIfInfo) {
        this.ordBalacneIfInfo = ordBalacneIfInfo;
    }

    public void setOrdExtendInfo(OrdExtendInfo ordExtendInfo) {
        this.ordExtendInfo = ordExtendInfo;
    }

}
