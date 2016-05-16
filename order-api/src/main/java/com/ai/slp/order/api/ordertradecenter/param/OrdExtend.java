package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;

public class OrdExtend implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 拓展信息
     */
    private String infoJson;

    public String getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(String infoJson) {
        this.infoJson = infoJson;
    }

}
