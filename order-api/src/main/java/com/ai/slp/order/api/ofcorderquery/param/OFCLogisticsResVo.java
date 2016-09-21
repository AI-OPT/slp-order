package com.ai.slp.order.api.ofcorderquery.param;

import java.io.Serializable;

public class OFCLogisticsResVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 发货单号
	 */
	private String shipNo;
	
	/**
	 * 物流商
	 */
	private String logisticsName;
	/**
	 * 物流单号
	 */
	private String logisticsNo;
	/**
	 * 物流费用
	 */
	private long logisticsFee;
	/**
	 * 发货仓库
	 */
	private String wareHouseName;
	/**
	 * 收件人
	 */
	private String receiver;
	public String getShipNo() {
		return shipNo;
	}
	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}
	public String getLogisticsName() {
		return logisticsName;
	}
	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}
	public String getLogisticsNo() {
		return logisticsNo;
	}
	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}
	public long getLogisticsFee() {
		return logisticsFee;
	}
	public void setLogisticsFee(long logisticsFee) {
		this.logisticsFee = logisticsFee;
	}
	public String getWareHouseName() {
		return wareHouseName;
	}
	public void setWareHouseName(String wareHouseName) {
		this.wareHouseName = wareHouseName;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
}
