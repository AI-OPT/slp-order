package com.ai.slp.order.search.bo;

public class ProdInfo {
	private String prodname;
	private long buysum;
	
    private long saleprice;
    private long couponfee;
    private long jffee;
    private long givejf;
    private String cusserviceflag;
    private String state; //TODO 是否翻译
    private String prodcode;
    private String skuid;
    private long proddetalid;
    private String vfsid;
    private String pictype;  
    private String imageurl; 
    private String prodextendinfo; 
    private String skustorageid;
	private long totalfee; 
	private long discountfee;
	private long adjustfee;
//	private long operdiscountfee;
    
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
	/*public String getProdextendinfo() {
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
	}*/
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
