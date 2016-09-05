package com.ai.slp.order.api.ordertradecenter.param;

import java.util.List;

import javax.validation.constraints.NotNull;

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
     * 订单基本信息
     */
    @NotNull(message = "订单信息不能为空")
    private OrdBaseInfo ordBaseInfo;

    /**
     * 商品信息
     */
    @NotNull(message = "商品信息列表不能为空")
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
     * 订单拓展信息
     */
    private OrdExtendInfo ordExtendInfo;
    
    /**
     * 订单费用明细信息
     */
    private List<OrdFeeTotalProdInfo> ordFeeTotalProdInfo;

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

    public void setOrdExtendInfo(OrdExtendInfo ordExtendInfo) {
        this.ordExtendInfo = ordExtendInfo;
    }

	public List<OrdFeeTotalProdInfo> getOrdFeeTotalProdInfo() {
		return ordFeeTotalProdInfo;
	}

	public void setOrdFeeTotalProdInfo(List<OrdFeeTotalProdInfo> ordFeeTotalProdInfo) {
		this.ordFeeTotalProdInfo = ordFeeTotalProdInfo;
	}
}
