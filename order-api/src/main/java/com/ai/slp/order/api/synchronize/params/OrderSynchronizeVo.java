package com.ai.slp.order.api.synchronize.params;

import java.util.List;

import com.ai.opt.base.vo.BaseInfo;

public class OrderSynchronizeVo extends BaseInfo {

	private static final long serialVersionUID = 1L;

	/**
	 * 订单id
	 */
	private long orderId;

	/**
	 * 订单费用信息
	 */
	private OrdOdFeeTotalVo ordOdFeeTotalVo;

	/**
	 * 订单发票信息
	 */
	private OrdOdInvoiceVo ordOdInvoiceVo;

	/**
	 * 订单物流信息
	 */
	private OrdOdLogisticVo ordOdLogisticVo;

	/**
	 * 订单商品信息列表
	 */
	private List<OrdOdProdVo> ordOdProdList;

	/**
	 * 订单信息
	 */
	private OrdOrderVo ordOrderVo;

	/**
	 * 订单支付信息
	 */
	private OrdBalanceIfVo ordBalanceIfVo;

	public List<OrdOdProdVo> getOrdOdProdList() {
		return ordOdProdList;
	}

	public void setOrdOdProdList(List<OrdOdProdVo> ordOdProdList) {
		this.ordOdProdList = ordOdProdList;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public OrdOdFeeTotalVo getOrdOdFeeTotalVo() {
		return ordOdFeeTotalVo;
	}

	public void setOrdOdFeeTotalVo(OrdOdFeeTotalVo ordOdFeeTotalVo) {
		this.ordOdFeeTotalVo = ordOdFeeTotalVo;
	}

	public OrdOdInvoiceVo getOrdOdInvoiceVo() {
		return ordOdInvoiceVo;
	}

	public void setOrdOdInvoiceVo(OrdOdInvoiceVo ordOdInvoiceVo) {
		this.ordOdInvoiceVo = ordOdInvoiceVo;
	}

	public OrdOdLogisticVo getOrdOdLogisticVo() {
		return ordOdLogisticVo;
	}

	public void setOrdOdLogisticVo(OrdOdLogisticVo ordOdLogisticVo) {
		this.ordOdLogisticVo = ordOdLogisticVo;
	}

	public OrdOrderVo getOrdOrderVo() {
		return ordOrderVo;
	}

	public void setOrdOrderVo(OrdOrderVo ordOrderVo) {
		this.ordOrderVo = ordOrderVo;
	}

	public OrdBalanceIfVo getOrdBalanceIfVo() {
		return ordBalanceIfVo;
	}

	public void setOrdBalanceIfVo(OrdBalanceIfVo ordBalanceIfVo) {
		this.ordBalanceIfVo = ordBalanceIfVo;
	}

}