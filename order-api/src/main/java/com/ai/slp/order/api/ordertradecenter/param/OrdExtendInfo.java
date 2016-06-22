package com.ai.slp.order.api.ordertradecenter.param;

import java.io.Serializable;

public class OrdExtendInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 拓展信息
     */
    private String infoJson;

    /**
     * 批量标识
     */
    private String batchFlag;

    public String getInfoJson() {
        return infoJson;
    }

    public void setInfoJson(String infoJson) {
        this.infoJson = infoJson;
    }

    public String getBatchFlag() {
        return batchFlag;
    }

    public void setBatchFlag(String batchFlag) {
        this.batchFlag = batchFlag;
    }

}
