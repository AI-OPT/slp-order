package com.ai.slp.order.search.bo.prod;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class SKUInfo {
	/**
	 * 租户
	 */
	@Expose
	private String tenantid;
	/**
	 * sku标识
	 */
	@Expose
	private String skuid;
	/**
	 * sku名称
	 */
	@Expose
	private String skuname;

	// 叶子
	@Expose
	private String productcategoryid;
	/**
	 * 标识
	 */
	@Expose
	private String rootcategorid;
	/**
	 * 商品标识
	 */
	@Expose
	private String productid;
	/**
	 * 商品名称
	 */
	@Expose
	private String productname;
	/**
	 * 卖点
	 */
	@Expose
	private String productsellpoint = "";
	// 关键属性
	@Expose
	private List<AttrInfo> attrinfos;
	/**
	 * 销量
	 */
	@Expose
	private long salenum;
	/**
	 * 可销售数
	 */
	@Expose
	private long usablenum;
	/**
	 * 类目
	 */
	@Expose
	private List<CategoryInfo> categoryinfos;
	/**
	 * 图片
	 */
	@Expose
	private ImageInfo imageinfo;
	/**
	 * 价格
	 */
	@Expose
	private long price;
	// 改成三级权限
	@Expose
	private List<ProdAudiencesSes> audiences;

	// 销售地域
	@Expose
	private List<SaleAreaInfo> saleareainfos;
	//
	@Expose
	private String basicorgid;
	// 充值方式
	@Expose
	private String rechagetype;
	// 是否是全国销售
	@Expose
	private String salenationwide = "";
	// 图片信息
	@Expose
	private List<ImageInfo> thumbnail;
	/**
	 * 上架时间
	 */
	@Expose
	private long uptime;
	/**
	 * 操作时间
	 */
	@Expose
	private long opertime;
	/**
	 * 下架时间
	 */
	@Expose
	private long downtime;

	/**
	 * 创建时间
	 */
	@Expose
	private long createtime;

	/**
	 * 市场价格
	 */
	@Expose
	private long marketprice;

	/**
	 * 供应商id
	 */
	@Expose
	private String supplierid;

	/**
	 * 评论数
	 */
	@Expose
	private long commentnum;
	/**
	 * 状态
	 */
	@Expose
	private String state;

	/**
	 * 单位
	 */
	@Expose
	private String unit;

	/**
	 * 类目名称
	 */
	@Expose
	private String productcatname;

	/**
	 * 商品类型
	 */
	@Expose
	private String producttype;

	/**
	 * 属性值集合
	 */
	@Expose
	private List<ProdAttrInfo> prodattrinfos;
	/**
	 * 标准品状态
	 */
	@Expose
	private String standprodstate;
	/**
	 * 库存组状态
	 */
	@Expose
	private String storagegroupstate;

	/**
	 * 是否提供发票
	 * @return
	 * @author Gavin
	 * @UCUSER
	 */
	@Expose
	private String isinvoice;
	
	/**
	 * 上架类型
	 */
	@Expose
	private String upshelftype;
	
	/**
	 * 图文描述
	 */
	@Expose
	 private String prodetailcontent;
	/**
	 * 库存组id
	 */
	@Expose
	private String storagegroupid;
	
	/**
	 * 操作人id
	 */
	@Expose
	private String operid;
	
	
	 
	public String getStoragegroupid() {
		return storagegroupid;
	}

	public void setStoragegroupid(String storagegroupid) {
		this.storagegroupid = storagegroupid;
	}

	public String getOperid() {
		return operid;
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}


	public String getIsinvoice() {
		return isinvoice;
	}

	public void setIsinvoice(String isinvoice) {
		this.isinvoice = isinvoice;
	}

	public String getUpshelftype() {
		return upshelftype;
	}

	public void setUpshelftype(String upshelftype) {
		this.upshelftype = upshelftype;
	}

	public String getProdetailcontent() {
		return prodetailcontent;
	}

	public void setProdetailcontent(String prodetailcontent) {
		this.prodetailcontent = prodetailcontent;
	}

	public String getState() {
		return state;
	}

	public String getStoragegroupstate() {
		return storagegroupstate;
	}

	public void setStoragegroupstate(String storagegroupstate) {
		this.storagegroupstate = storagegroupstate;
	}

	public String getStandprodstate() {
		return standprodstate;
	}

	public void setStandprodstate(String standprodstate) {
		this.standprodstate = standprodstate;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public String getProductcatname() {
		return productcatname;
	}

	public void setProductcatname(String productcatname) {
		this.productcatname = productcatname;
	}

	public long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}

	public long getOpertime() {
		return opertime;
	}

	public void setOpertime(long opertime) {
		this.opertime = opertime;
	}

	public long getDowntime() {
		return downtime;
	}

	public void setDowntime(long downtime) {
		this.downtime = downtime;
	}

	public String getUnit() {
		return unit;
	}

	public List<ProdAttrInfo> getProdattrinfos() {
		return prodattrinfos;
	}

	public void setProdattrinfos(List<ProdAttrInfo> prodattrinfos) {
		this.prodattrinfos = prodattrinfos;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setState(String state) {
		this.state = state;
	}

	public SKUInfo() {
		super();
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public long getCommentnum() {
		return commentnum;
	}

	public void setCommentnum(long commentnum) {
		this.commentnum = commentnum;
	}

	public SKUInfo(String tenantid, String skuid, String skuname) {
		this.tenantid = tenantid;
		this.skuid = skuid;
		this.skuname = skuname;
		this.categoryinfos = new ArrayList<CategoryInfo>();
		this.audiences = new ArrayList<ProdAudiencesSes>();
		this.attrinfos = new ArrayList<AttrInfo>();
		this.saleareainfos = new ArrayList<SaleAreaInfo>();
		this.thumbnail = new ArrayList<ImageInfo>();
	}

	public long getMarketprice() {
		return marketprice;
	}

	public void setMarketprice(long marketprice) {
		this.marketprice = marketprice;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public void setProductsellpoint(String productsellpoint) {
		this.productsellpoint = productsellpoint;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductcategoryid(String productcategoryid) {
		this.productcategoryid = productcategoryid;
	}

	public String getProductcategoryid() {
		return productcategoryid;
	}

	public void addCategoryInfo(CategoryInfo categoryInfo) {
		this.categoryinfos.add(categoryInfo);
	}

	public void setSalenum(long salenum) {
		this.salenum = salenum;
	}

	public String getSkuid() {
		return skuid;
	}

	public void setImageinfo(ImageInfo imageinfo) {
		this.imageinfo = imageinfo;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public void addProductAudiences(ProdAudiencesSes prodAudiences) {
		audiences.add(prodAudiences);
	}

	public String getBasicorgid() {
		return basicorgid;
	}

	public void setBasicorgid(String basicorgid) {
		this.basicorgid = basicorgid;
	}

	public void setRechagetype(String rechagetype) {
		this.rechagetype = rechagetype;
	}

	public void setSalenationwide(String salenationwide) {
		if (salenationwide == null || salenationwide.length() == 0) {
			this.salenationwide = "N";
			return;
		}
		this.salenationwide = salenationwide;
	}

	public String getSalenationwide() {
		return salenationwide;
	}

	public List<SaleAreaInfo> getSaleareainfos() {
		return saleareainfos;
	}

	public String getRootcategorid() {
		return rootcategorid;
	}

	public void setRootcategorid(String rootcategorid) {
		this.rootcategorid = rootcategorid;
	}

	public void addThumbnail(ImageInfo imageInfo) {
		this.thumbnail.add(imageInfo);
	}

	public void setUptime(long uptime) {
		this.uptime = uptime;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	public String getSkuname() {
		return skuname;
	}

	public void setSkuname(String skuname) {
		this.skuname = skuname;
	}

	public List<CategoryInfo> getCategoryinfos() {
		return categoryinfos;
	}

	public long getUsablenum() {
		return usablenum;
	}

	public void setUsablenum(long usablenum) {
		this.usablenum = usablenum;
	}

	public void setCategoryinfos(List<CategoryInfo> categoryinfos) {
		this.categoryinfos = categoryinfos;
	}

	public List<ProdAudiencesSes> getAudiences() {
		return audiences;
	}

	public void setAudiences(List<ProdAudiencesSes> audiences) {
		this.audiences = audiences;
	}

	public List<ImageInfo> getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(List<ImageInfo> thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getProductname() {
		return productname;
	}

	public String getProductsellpoint() {
		return productsellpoint;
	}

	public List<AttrInfo> getAttrinfos() {
		return attrinfos;
	}

	public void setAttrinfos(List<AttrInfo> attrinfos) {
		this.attrinfos = attrinfos;
	}

	public long getSalenum() {
		return salenum;
	}

	public ImageInfo getImageinfo() {
		return imageinfo;
	}

	public String getRechagetype() {
		return rechagetype;
	}

	public long getUptime() {
		return uptime;
	}

	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}

	public void setSaleareainfos(List<SaleAreaInfo> saleareainfos) {
		this.saleareainfos = saleareainfos;
	}

}
