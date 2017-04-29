package com.ai.slp.order.search.bo;

public class ProdInfo {
	private String prodname; //商品名称
	private long buysum; //商品数量
    private long saleprice; //商品单价
    private long couponfee;//优惠券
    private long jffee;//积分
    private long givejf;//赠送积分
    private String cusserviceflag;//售后标识
    private String state; //商品状态  1：销售;  2：退货;  3：换货; 4：预售
    private String prodcode;//商品编码
    private String skuid;//单品id
    private long proddetalid; //订单商品明细id
    private String vfsid; //图片id
    private String pictype;  //图片类型
    private String imageurl; //售后申请图片id
    private String prodextendinfo; //售后申请图片类型
    private String skustorageid; //库存ID
    
    //elasticSearch暂时没存入的信息
	private long totalfee; //费用
	private long discountfee;//优惠额
	private long adjustfee;//实支付
    
	public String getProdname() {
		return prodname;
	}
	public void setProdname(String prodname) {
		this.prodname = prodname;
	}
	public long getBuysum() {
		return buysum;
	}
	public void setBuysum(long buysum) {
		this.buysum = buysum;
	}
	public long getSaleprice() {
		return saleprice;
	}
	public void setSaleprice(long saleprice) {
		this.saleprice = saleprice;
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
	public long getGivejf() {
		return givejf;
	}
	public void setGivejf(long givejf) {
		this.givejf = givejf;
	}
	public String getCusserviceflag() {
		return cusserviceflag;
	}
	public void setCusserviceflag(String cusserviceflag) {
		this.cusserviceflag = cusserviceflag;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getProdcode() {
		return prodcode;
	}
	public void setProdcode(String prodcode) {
		this.prodcode = prodcode;
	}
	public String getSkuid() {
		return skuid;
	}
	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}
	public long getProddetalid() {
		return proddetalid;
	}
	public void setProddetalid(long proddetalid) {
		this.proddetalid = proddetalid;
	}
	public String getVfsid() {
		return vfsid;
	}
	public void setVfsid(String vfsid) {
		this.vfsid = vfsid;
	}
	public String getPictype() {
		return pictype;
	}
	public void setPictype(String pictype) {
		this.pictype = pictype;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public long getTotalfee() {
		return totalfee;
	}
	public void setTotalfee(long totalfee) {
		this.totalfee = totalfee;
	}
	public long getDiscountfee() {
		return discountfee;
	}
	public void setDiscountfee(long discountfee) {
		this.discountfee = discountfee;
	}
	public long getAdjustfee() {
		return adjustfee;
	}
	public void setAdjustfee(long adjustfee) {
		this.adjustfee = adjustfee;
	}
	public String getProdextendinfo() {
		return prodextendinfo;
	}
	public void setProdextendinfo(String prodextendinfo) {
		this.prodextendinfo = prodextendinfo;
	}
	public String getSkustorageid() {
		return skustorageid;
	}
	public void setSkustorageid(String skustorageid) {
		this.skustorageid = skustorageid;
	}
}
