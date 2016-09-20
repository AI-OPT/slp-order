package com.ai.slp.order.api.ordertradecenter.param;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ai.opt.base.vo.BaseInfo;

public class OrdProductDetailInfo extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
    
    
    /**
     * 销售商id
     */
  //  @NotBlank(message="销售商id不能为空")
    private Long supplierId;
    
    /**
     * 运费
     */
    private long freight;
    
    /**
     * 商品信息
     */
 //   @NotNull(message = "商品信息列表不能为空")
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
}
