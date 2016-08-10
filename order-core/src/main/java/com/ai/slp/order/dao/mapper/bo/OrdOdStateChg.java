package com.ai.slp.order.dao.mapper.bo;

import java.sql.Timestamp;

public class OrdOdStateChg {
    private long stateChgId;

    private long orderId;

    private String tenantId;

    private String orgState;

    private String newState;

    private String chgDesc;

    private String orgId;

    private String operId;

    private String operName;

    private Timestamp stateChgTime;

    public long getStateChgId() {
        return stateChgId;
    }

    public void setStateChgId(long stateChgId) {
        this.stateChgId = stateChgId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    public String getOrgState() {
        return orgState;
    }

    public void setOrgState(String orgState) {
        this.orgState = orgState == null ? null : orgState.trim();
    }

    public String getNewState() {
        return newState;
    }

    public void setNewState(String newState) {
        this.newState = newState == null ? null : newState.trim();
    }

    public String getChgDesc() {
        return chgDesc;
    }

    public void setChgDesc(String chgDesc) {
        this.chgDesc = chgDesc == null ? null : chgDesc.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId == null ? null : operId.trim();
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName == null ? null : operName.trim();
    }

    public Timestamp getStateChgTime() {
        return stateChgTime;
    }

    public void setStateChgTime(Timestamp stateChgTime) {
        this.stateChgTime = stateChgTime;
    }
}