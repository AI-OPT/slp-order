package com.ai.slp.order.api.freighttemplate.param;

import java.io.Serializable;

/**
 * 返回的运费模版明细信息
 * @date 2016年8月4日 
 * @author caofz
 */
public class FreightTemplateProdVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 对应区域id
	 */
	private String regionId;
	
	/**
	 * 运送指定地区
	 */
	private String transportAddress;
	
	/**
	 * 首次数量
	 */
	private Long firstNumber;
	
	/**
	 * 首次金额
	 */
	private Long firstNum;
	
	/**
	 * 续数
	 */
	private Long pieceNumber;
	
	/**
	 * 续费
	 */
	private Long pieceNum;
	
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

	public Long getFirstNumber() {
		return firstNumber;
	}

	public void setFirstNumber(Long firstNumber) {
		this.firstNumber = firstNumber;
	}

	public Long getFirstNum() {
		return firstNum;
	}

	public void setFirstNum(Long firstNum) {
		this.firstNum = firstNum;
	}

	public Long getPieceNumber() {
		return pieceNumber;
	}

	public void setPieceNumber(Long pieceNumber) {
		this.pieceNumber = pieceNumber;
	}

	public Long getPieceNum() {
		return pieceNum;
	}

	public void setPieceNum(Long pieceNum) {
		this.pieceNum = pieceNum;
	}
}
