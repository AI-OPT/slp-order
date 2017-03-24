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
	 *商品明细id 
	 */
	private Long prodDetalId;
	
	
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
     * 优惠扣减费用
     */
    private long couponFee;
    
    /**
     * 积分扣减费用
     */
    private long jfFee;
    
    /**
     * 商品是否售后标识
     */
    private String cusServiceFlag;
    
    /**
     * 商品赠送积分
     */
    private long giveJF;
    
    /**
     * 商品编码
     */
    private String prodCode;
    /**
     * 库存ID
     */
    private String skuStorageId;

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

	public ProductImage getProductImage() {
		return productImage;
	}

	public void setProductImage(ProductImage productImage) {
		this.productImage = productImage;
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
	
	public long getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(long couponFee) {
		this.couponFee = couponFee;
	}

	public long getJfFee() {
		return jfFee;
	}

	public void setJfFee(long jfFee) {
		this.jfFee = jfFee;
	}

	public Long getProdDetalId() {
		return prodDetalId;
	}

	public void setProdDetalId(Long prodDetalId) {
		this.prodDetalId = prodDetalId;
	}

	public String getCusServiceFlag() {
		return cusServiceFlag;
	}

	public void setCusServiceFlag(String cusServiceFlag) {
		this.cusServiceFlag = cusServiceFlag;
	}

	public long getGiveJF() {
		return giveJF;
	}

	public void setGiveJF(long giveJF) {
		this.giveJF = giveJF;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getSkuStorageId() {
		return skuStorageId;
	}

	public void setSkuStorageId(String skuStorageId) {
		this.skuStorageId = skuStorageId;
	}

	
	
}
