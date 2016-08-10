package com.ai.slp.order.dao.mapper.bo;

import java.sql.Timestamp;

public class OrdRule {
    private String orderRuleId;

    private Integer monitorTime;

    private String timeType;

    private Integer orderSum;

    private Timestamp createTime;

    public String getOrderRuleId() {
        return orderRuleId;
    }

    public void setOrderRuleId(String orderRuleId) {
        this.orderRuleId = orderRuleId == null ? null : orderRuleId.trim();
    }

    public Integer getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(Integer monitorTime) {
        this.monitorTime = monitorTime;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType == null ? null : timeType.trim();
    }

    public Integer getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(Integer orderSum) {
        this.orderSum = orderSum;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}