package com.ai.slp.order.api.orderlist.param;

import com.ai.opt.base.vo.BaseInfo;

/**
 * 子订单展现信息对象
 * @author jiaxs
 *
 */
public class OrdProductVo extends BaseInfo{

	private static final long serialVersionUID = 1L;
	
	
	/**
     * 业务订单ID
     */
    private Long orderId;

    /**
     * 单品ID
     */
    private String skuId;
    
    /**
     * 商品名称
     */
    private String prodName;
    
    /**
     * 商品状态
     */
    private String state;
    
    /**
     * 购买数量
     */
    private Long buySum;
    
    /**
     * 销售单价
     */
    private Long salePrice;
    
    /**
     * 总费用
     */
    private Long totalFee;
    
    /**
     * 优惠费用 
     */
    private Long discountFee;
    
    /**
     * 减免费用
     */
    private Long operDiscountFee;
    
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
     * 省份(商品附加信息)
     */
    private String provinceCode;
    
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
     * 图片地址
     */
    private String imageUrl;
    
    /**
     * 拓展信息值
     */
    private String prodExtendInfo;
    
    /**
     * 积分
     */
    private Long integral;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public Long getBuySum() {
		return buySum;
	}

	public void setBuySum(Long buySum) {
		this.buySum = buySum;
	}

	public Long getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Long salePrice) {
		this.salePrice = salePrice;
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

	public Long getAdjustFee() {
		return adjustFee;
	}

	public void setAdjustFee(Long adjustFee) {
		this.adjustFee = adjustFee;
	}

	public String getBasicOrgId() {
		return basicOrgId;
	}

	public void setBasicOrgId(String basicOrgId) {
		this.basicOrgId = basicOrgId;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
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

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getOperDiscountFee() {
		return operDiscountFee;
	}

	public void setOperDiscountFee(Long operDiscountFee) {
		this.operDiscountFee = operDiscountFee;
	}

	public String getProdExtendInfo() {
		return prodExtendInfo;
	}

	public void setProdExtendInfo(String prodExtendInfo) {
		this.prodExtendInfo = prodExtendInfo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getIntegral() {
		return integral;
	}

	public void setIntegral(Long integral) {
		this.integral = integral;
	}
}
