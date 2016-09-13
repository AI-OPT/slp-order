package com.ai.slp.order.api.freighttemplate.param;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class FreightTemplateProdInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 运送指定地区
	 */
	private String transportAddress;
	
	/**
	 * 首次数量
	 */
	private long firstNumber;
	
	/**
	 * 首次金额
	 */
	private long firstNum;
	
	/**
	 * 续数
	 */
	private long pieceNumber;
	
	/**
	 * 续费
	 */
	private long pieceNum;
	
	/**
	 * 对应区域id
	 */
	@NotBlank(message="对应区域id不能为空")
	private String regionId;
	
	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getTransportAddress() {
		return transportAddress;
	}

	public void setTransportAddress(String transportAddress) {
		this.transportAddress = transportAddress;
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
