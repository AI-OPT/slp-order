package com.ai.slp.order.dao.mapper.bo;

import java.sql.Timestamp;

public class OrdOdDeliverInfo {
    private long deliverInfoId;

    private long orderId;

    private String horOrderId;

    private Timestamp updateTime;

    private String printInfo;

    public long getDeliverInfoId() {
        return deliverInfoId;
    }

    public void setDeliverInfoId(long deliverInfoId) {
        this.deliverInfoId = deliverInfoId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getHorOrderId() {
        return horOrderId;
    }

    public void setHorOrderId(String horOrderId) {
        this.horOrderId = horOrderId == null ? null : horOrderId.trim();
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getPrintInfo() {
        return printInfo;
    }

    public void setPrintInfo(String printInfo) {
        this.printInfo = printInfo == null ? null : printInfo.trim();
    }
}