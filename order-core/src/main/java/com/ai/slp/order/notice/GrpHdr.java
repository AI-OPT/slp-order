package com.ai.slp.order.notice;

public class GrpHdr {
	/**
	 * 发起机构
	 */
	private String MerNo;
	/**
	 * 发送时间
	 */
	private String CreDtTm;
	/**
	 * 交易类型
	 */
	private String TranType;
	public String getMerNo() {
		return MerNo;
	}
	public void setMerNo(String merNo) {
		MerNo = merNo;
	}
	public String getCreDtTm() {
		return CreDtTm;
	}
	public void setCreDtTm(String creDtTm) {
		CreDtTm = creDtTm;
	}
	public String getTranType() {
		return TranType;
	}
	public void setTranType(String tranType) {
		TranType = tranType;
	}
	
	
	

}
