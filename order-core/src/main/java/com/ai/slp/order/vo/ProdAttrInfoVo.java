package com.ai.slp.order.vo;

/**
 * 商品属性信息 Date: 2016年5月27日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
public class ProdAttrInfoVo {
    /**
     * 运营商
     */
    private String basicOrgId;

    /**
     * 省份
     */
    private String provinceCode;

    /**
     * 充值面额
     */
    private String chargeFee;

    public String getBasicOrgId() {
        return basicOrgId;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public String getChargeFee() {
        return chargeFee;
    }

    public void setBasicOrgId(String basicOrgId) {
        this.basicOrgId = basicOrgId;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public void setChargeFee(String chargeFee) {
        this.chargeFee = chargeFee;
    }

}
