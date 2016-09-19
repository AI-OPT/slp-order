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
     * 订单其它信息
     */
    @NotNull(message = "信息列表不能为空")
    private List<OrdProductDetailInfo> ordProductDetailInfos;

    /**
     * 配送信息
     */
    private OrdLogisticsInfo ordLogisticsInfo;

    /**
     * 订单拓展信息
     */
    private OrdExtendInfo ordExtendInfo;
    
    public OrdBaseInfo getOrdBaseInfo() {
        return ordBaseInfo;
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

    public void setOrdLogisticsInfo(OrdLogisticsInfo ordLogisticsInfo) {
        this.ordLogisticsInfo = ordLogisticsInfo;
    }

    public void setOrdExtendInfo(OrdExtendInfo ordExtendInfo) {
        this.ordExtendInfo = ordExtendInfo;
    }

	public List<OrdProductDetailInfo> getOrdProductDetailInfos() {
		return ordProductDetailInfos;
	}

	public void setOrdProductDetailInfos(List<OrdProductDetailInfo> ordProductDetailInfos) {
		this.ordProductDetailInfos = ordProductDetailInfos;
	}
}
