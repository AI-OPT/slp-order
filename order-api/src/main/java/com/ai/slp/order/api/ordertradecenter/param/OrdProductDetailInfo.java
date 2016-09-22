package com.ai.slp.order.api.ordertradecenter.param;

import java.util.List;

import com.ai.opt.base.vo.BaseInfo;

public class OrdProductDetailInfo extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
    /**
     * 销售商id
     */
    private Long supplierId;
    
    /**
     * 积分账户id
     */
    private String accountId;
    
    /**
     * 运费
     */
    private long freight;
    
    /**
     * 积分中心返回的id
     */
    private String downstreamOrderId;
    
    /**
     * 商品信息
     */
    private List<OrdProductInfo> ordProductInfoList;
    
    /**
     * 订单费用明细信息
     */
    private List<OrdFeeTotalProdInfo> ordFeeTotalProdInfo;
    
    /**
     * 发票信息
     */
    private OrdInvoiceInfo ordInvoiceInfo;
    

	public List<OrdProductInfo> getOrdProductInfoList() {
		return ordProductInfoList;
	}

	public void setOrdProductInfoList(List<OrdProductInfo> ordProductInfoList) {
		this.ordProductInfoList = ordProductInfoList;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public List<OrdFeeTotalProdInfo> getOrdFeeTotalProdInfo() {
		return ordFeeTotalProdInfo;
	}

	public void setOrdFeeTotalProdInfo(List<OrdFeeTotalProdInfo> ordFeeTotalProdInfo) {
		this.ordFeeTotalProdInfo = ordFeeTotalProdInfo;
	}

	public long getFreight() {
		return freight;
	}

	public void setFreight(long freight) {
		this.freight = freight;
	}

	public OrdInvoiceInfo getOrdInvoiceInfo() {
		return ordInvoiceInfo;
	}

	public void setOrdInvoiceInfo(OrdInvoiceInfo ordInvoiceInfo) {
		this.ordInvoiceInfo = ordInvoiceInfo;
	}

	public String getDownstreamOrderId() {
		return downstreamOrderId;
	}

	public void setDownstreamOrderId(String downstreamOrderId) {
		this.downstreamOrderId = downstreamOrderId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
