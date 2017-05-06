package com.ai.slp.order.search.bo.prod;

import com.google.gson.annotations.Expose;

/**
 * Created by xin on 16-5-20.
 */
public class SaleAreaInfo {
	/**
	 * 省份编码
	 */
    @Expose
    private String provcode;

    public SaleAreaInfo() {
        super();
    }

    public SaleAreaInfo(String provcode) {
        this.provcode = provcode;
    }

    public String getProvcode() {
        return provcode;
    }

    public void setProvcode(String provcode) {
        this.provcode = provcode;
    }
}
