package com.ai.slp.order.api.warmorder.param;

import java.io.Serializable;
import java.util.List;

public class ProductListInfo implements Serializable {
	private static final long serialVersionUID = 1L;

    //业务订单ID(子订单id)
    private Long orderid;
    //父订单id
    private Long parentorderid;
    //订单状态(后台)
    private String state;
    //订单状态展示
    private String statename;
    //商品SIZE
    private long prodsize;
    
    private List<OrdProductVo> prodinfos;

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

	public List<OrdProductVo> getProdinfos() {
		return prodinfos;
	}

	public void setProdinfos(List<OrdProductVo> prodinfos) {
		this.prodinfos = prodinfos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
