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
	private Long proddetalid;//--
	
	
	/**
     * 业务订单ID
     */
    private Long orderid;

    /**
     * 单品ID
     */
    private String skuid;
    
    /**
     * 商品名称
     */
    private String prodname;
    
    /**
     * 商品状态
     */
    private String state;
    
    /**
     * 购买数量
     */
    private Long buysum;
    
    /**
     * 销售单价
     */
    private Long saleprice;
    
    /**
     * 总费用
     */
    private Long totalfee;//--
    
    /**
     * 优惠费用 
     */
    private Long discountfee;//--
    
    /**
     * 减免费用
     */
    private Long operdiscountfee;//--
    
    /**
     * 应收费用
     */
    private Long adjustfee;//--
    
    /**
     * 商品图片
     */
    private ProductImage productimage;//--
    
    /**
     * 图片地址
     */
    private String imageurl;//--
    
    /**
     * 拓展信息值
     */
    private String prodextendinfo;//--
    
    /**
     * 优惠扣减费用
     */
    private long couponfee;
    
    /**
     * 积分扣减费用
     */
    private long jffee;
    
    /**
     * 商品是否售后标识
     */
    private String cusserviceflag; //--
    
    /**
     * 商品赠送积分
     */
    private long givejf;
    
    /**
     * 商品编码
     */
    private String prodcode;//--
    /**
     * 库存ID
     */
    private String skustorageid;//--
	public Long getProddetalid() {
		return proddetalid;
	}
	public void setProddetalid(Long proddetalid) {
		this.proddetalid = proddetalid;
	}
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public String getSkuid() {
		return skuid;
	}
	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}
	public String getProdname() {
		return prodname;
	}
	public void setProdname(String prodname) {
		this.prodname = prodname;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getBuysum() {
		return buysum;
	}
	public void setBuysum(Long buysum) {
		this.buysum = buysum;
	}
	public Long getSaleprice() {
		return saleprice;
	}
	public void setSaleprice(Long saleprice) {
		this.saleprice = saleprice;
	}
	public Long getTotalfee() {
		return totalfee;
	}
	public void setTotalfee(Long totalfee) {
		this.totalfee = totalfee;
	}
	public Long getDiscountfee() {
		return discountfee;
	}
	public void setDiscountfee(Long discountfee) {
		this.discountfee = discountfee;
	}
	public Long getOperdiscountfee() {
		return operdiscountfee;
	}
	public void setOperdiscountfee(Long operdiscountfee) {
		this.operdiscountfee = operdiscountfee;
	}
	public Long getAdjustfee() {
		return adjustfee;
	}
	public void setAdjustfee(Long adjustfee) {
		this.adjustfee = adjustfee;
	}
	public ProductImage getProductimage() {
		return productimage;
	}
	public void setProductimage(ProductImage productimage) {
		this.productimage = productimage;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getProdextendinfo() {
		return prodextendinfo;
	}
	public void setProdextendinfo(String prodextendinfo) {
		this.prodextendinfo = prodextendinfo;
	}
	public long getCouponfee() {
		return couponfee;
	}
	public void setCouponfee(long couponfee) {
		this.couponfee = couponfee;
	}
	public long getJffee() {
		return jffee;
	}
	public void setJffee(long jffee) {
		this.jffee = jffee;
	}
	public String getCusserviceflag() {
		return cusserviceflag;
	}
	public void setCusserviceflag(String cusserviceflag) {
		this.cusserviceflag = cusserviceflag;
	}
	public long getGivejf() {
		return givejf;
	}
	public void setGivejf(long givejf) {
		this.givejf = givejf;
	}
	public String getProdcode() {
		return prodcode;
	}
	public void setProdcode(String prodcode) {
		this.prodcode = prodcode;
	}
	public String getSkustorageid() {
		return skustorageid;
	}
	public void setSkustorageid(String skustorageid) {
		this.skustorageid = skustorageid;
	}
	
}
