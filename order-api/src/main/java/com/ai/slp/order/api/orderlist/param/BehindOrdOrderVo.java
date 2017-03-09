package com.ai.slp.order.api.orderlist.param;

import java.util.List;

import com.ai.opt.base.vo.BaseInfo;

public class BehindOrdOrderVo extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
    /**
     * 业务订单ID
     */
    private Long orderid;
    
    /**
     * 父订单id
     */
//    private Long parentOrderId;
    private Long parentorderid;

    /**
     * 订单状态(后厂)
     */
    private String state;
    
    /**
     * 业务类型
     */
    private String busicode;

    /**
     * 订单状态展示
     */
    private String statename;
    
    /**
     * 商品SIZE
     */
    private long prodsize;
    
  //  private List<BehindOrdProductVo> productList;
    private List<BehindOrdProductVo> prodinfos;

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public Long getParentorderid() {
		return parentorderid;
	}

	public void setParentorderid(Long parentorderid) {
		this.parentorderid = parentorderid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBusicode() {
		return busicode;
	}

	public void setBusicode(String busicode) {
		this.busicode = busicode;
	}

	public String getStatename() {
		return statename;
	}

	public void setStatename(String statename) {
		this.statename = statename;
	}

	public long getProdsize() {
		return prodsize;
	}

	public void setProdsize(long prodsize) {
		this.prodsize = prodsize;
	}

	public List<BehindOrdProductVo> getProdinfos() {
		return prodinfos;
	}

	public void setProdinfos(List<BehindOrdProductVo> prodinfos) {
		this.prodinfos = prodinfos;
	}
}
