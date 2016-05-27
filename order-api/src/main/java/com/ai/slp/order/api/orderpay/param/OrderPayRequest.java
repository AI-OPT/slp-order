package com.ai.slp.order.api.orderpay.param;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 订单收费请求参数 Date: 2016年5月24日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
public class OrderPayRequest extends BaseInfo {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID集
     */
    @NotBlank(message = "订单ID不能为空")
    private List<Long> orderIds;

    /**
     * 收费金额
     */
    @NotBlank(message = "收费金额不能为空")
    private Long payFee;

    /**
     * 外部流水号
     */
    @NotBlank(message = "收费金额不能为空")
    private String externalId;

    /**
     * 支付方式
     */
    @NotBlank(message = "支付方式不能为空")
    private String payType;

    public List<Long> getOrderIds() {
        return orderIds;
    }

    public Long getPayFee() {
        return payFee;
    }

    public String getExternalId() {
        return externalId;
    }

    public String getPayType() {
        return payType;
    }

    public void setOrderIds(List<Long> orderIds) {
        this.orderIds = orderIds;
    }

    public void setPayFee(Long payFee) {
        this.payFee = payFee;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

}
