package com.ai.slp.order.api.ordertradecenter.param;

import java.util.List;

import com.ai.opt.base.vo.BaseInfo;

public class OrdProductDetailInfo extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
    /**
     * 积分账户id
     */
    private String accountId;
    
    /**
     * 运费
     */
    private long freight;
    
    /**
     * 优惠费用
     */
    private long discountFee;
    
    /**
     * 买家留言
     */
    private String remark;
    
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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public long getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(long discountFee) {
		this.discountFee = discountFee;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
