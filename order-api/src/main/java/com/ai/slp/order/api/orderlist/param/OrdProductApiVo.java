package com.ai.slp.order.api.orderlist.param;

import com.ai.opt.base.vo.BaseInfo;

/**
 * api商品信息展示对象
 * @author caofz
 *
 */
public class OrdProductApiVo extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	/**
     * 销售品ID
     */
    private String prodId;
    
    /**
     * 销售品名称
     */
    private String prodName;
    
    /**
     * 购买数量
     */
    private Long buySum; 
    
    /**
     * 总费用
     */
    private Long totalFee;
    
    /**
     * 优惠费用
     */
    private Long discountFee;
    
    /**
     * 减免金额
     */
    private Long operDiscountFee;
    
    /**
     * 减免原因
     */
    private String operDiscountDesc;
    /**
     * 应收费用
     */
    private Long adjustFee;
    
    /**
     * 运营商(商品附加信息)
     */
    private String basicOrgId;
    
    /**
     * 运营商
     */
    private String basicOrgName;

    /**
     * 省份
     */
    private String provinceName;

    /**
     * 充值面额(商品附加信息)
     */
    private String chargeFee;
    
    /**
     * 商品图片
     */
    private ProductImage productImage;
    
    /**
     * 单品ID
     */
    private String skuId;
    
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public Long getBuySum() {
		return buySum;
	}

	public void setBuySum(Long buySum) {
		this.buySum = buySum;
	}

	public String getBasicOrgId() {
		return basicOrgId;
	}

	public void setBasicOrgId(String basicOrgId) {
		this.basicOrgId = basicOrgId;
	}

	public String getBasicOrgName() {
		return basicOrgName;
	}

	public void setBasicOrgName(String basicOrgName) {
		this.basicOrgName = basicOrgName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getChargeFee() {
		return chargeFee;
	}

	public void setChargeFee(String chargeFee) {
		this.chargeFee = chargeFee;
	}

	public ProductImage getProductImage() {
		return productImage;
	}

	public void setProductImage(ProductImage productImage) {
		this.productImage = productImage;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public Long getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}

	public Long getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(Long discountFee) {
		this.discountFee = discountFee;
	}

	public Long getOperDiscountFee() {
		return operDiscountFee;
	}

	public void setOperDiscountFee(Long operDiscountFee) {
		this.operDiscountFee = operDiscountFee;
	}

	public String getOperDiscountDesc() {
		return operDiscountDesc;
	}

	public void setOperDiscountDesc(String operDiscountDesc) {
		this.operDiscountDesc = operDiscountDesc;
	}

	public Long getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(Long adjustFee) {
		this.adjustFee = adjustFee;
	}
    
}
