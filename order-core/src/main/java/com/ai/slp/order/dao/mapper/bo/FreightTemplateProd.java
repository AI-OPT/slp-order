package com.ai.slp.order.dao.mapper.bo;

public class FreightTemplateProd {
    private String regionId;

    private String templateId;

    private String transportAddress;

    private long firstNumber;

    private long firstNum;

    private long pieceNumber;

    private long pieceNum;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId == null ? null : regionId.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getTransportAddress() {
        return transportAddress;
    }

    public void setTransportAddress(String transportAddress) {
        this.transportAddress = transportAddress == null ? null : transportAddress.trim();
    }

    public long getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(long firstNumber) {
        this.firstNumber = firstNumber;
    }

    public long getFirstNum() {
        return firstNum;
    }

    public void setFirstNum(long firstNum) {
        this.firstNum = firstNum;
    }

    public long getPieceNumber() {
        return pieceNumber;
    }

    public void setPieceNumber(long pieceNumber) {
        this.pieceNumber = pieceNumber;
    }

    public long getPieceNum() {
        return pieceNum;
    }

    public void setPieceNum(long pieceNum) {
        this.pieceNum = pieceNum;
    }
}