package com.ai.slp.order.api.synchronize.params;

import com.ai.opt.base.vo.BaseInfo;

public class OrderSynchronizeVo extends BaseInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单费用信息
	 */
	private OrdOdFeeTotalVo ordOdFeeTotalVo;

	/**
	 * 订单发票信息
	 */
	private OrdOdInvoiceVo ordOdInvoice;

	/**
	 * 订单物流信息
	 */
	private OrdOdLogisticVo ordOdLogistic;

	/**
	 * 订单商品信息
	 */
	private OrdOdProdVo ordOdProdVo;

	/**
	 * 订单信息
	 */
	private OrdOrderVo ordOrderVo;

	public OrdOdFeeTotalVo getOrdOdFeeTotalVo() {
		return ordOdFeeTotalVo;
	}

	public void setOrdOdFeeTotalVo(OrdOdFeeTotalVo ordOdFeeTotalVo) {
		this.ordOdFeeTotalVo = ordOdFeeTotalVo;
	}

	public OrdOdInvoiceVo getOrdOdInvoice() {
		return ordOdInvoice;
	}

	public void setOrdOdInvoice(OrdOdInvoiceVo ordOdInvoice) {
		this.ordOdInvoice = ordOdInvoice;
	}

	public OrdOdLogisticVo getOrdOdLogistic() {
		return ordOdLogistic;
	}

	public void setOrdOdLogistic(OrdOdLogisticVo ordOdLogistic) {
		this.ordOdLogistic = ordOdLogistic;
	}

	public OrdOdProdVo getOrdOdProdVo() {
		return ordOdProdVo;
	}

	public void setOrdOdProdVo(OrdOdProdVo ordOdProdVo) {
		this.ordOdProdVo = ordOdProdVo;
	}

	public OrdOrderVo getOrdOrderVo() {
		return ordOrderVo;
	}

	public void setOrdOrderVo(OrdOrderVo ordOrderVo) {
		this.ordOrderVo = ordOrderVo;
	}

}
