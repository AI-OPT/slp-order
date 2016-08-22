package com.ai.slp.order.api.invoiceprint.param;


import java.util.Date;
import java.util.List;

import com.ai.opt.base.vo.BaseResponse;

/**
 * 订单发货单展示的参数
 * @date 2016年8月10日 
 * @author caofz
 */
public class InvoicePrintResponse extends BaseResponse{

	private static final long serialVersionUID = 1L;
	
	 /**
	 * 订单号
	 */
    private Long orderId;
    
    /**
     * 收货人姓名
     */
    private String contactName;
    
    /**
     * 发货日期
     */
    private Date invoiceDate;
    
    /**
     * 物流商
     */
    private String expressOddNumber;
    
    /**
     * 收货人电话
     */
    private String contactTel;
    
    /**
     * 收件人省份
     */
    private String provinceCode;
    
    /**
     * 收件人地市
     */
    private String cityCode;
    
    /**
     * 收件人区县
     */
    private String countyCode;
    
    /**
     * 详细地址(自提地址)
     */
    private String address;
    
    /**
     * 仓库id
     */
    private String routeId;
    
    /**
     * 总数
     */
    private long sum;
    
    /**
     * 打印的商品信息集合
     */
    private List<InvoicePrintVo> invoicePrintVos;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<InvoicePrintVo> getInvoicePrintVos() {
		return invoicePrintVos;
	}

	public void setInvoicePrintVos(List<InvoicePrintVo> invoicePrintVos) {
		this.invoicePrintVos = invoicePrintVos;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getExpressOddNumber() {
		return expressOddNumber;
	}

	public void setExpressOddNumber(String expressOddNumber) {
		this.expressOddNumber = expressOddNumber;
	}

	public long getSum() {
		return sum;
	}

	public void setSum(long sum) {
		this.sum = sum;
	}
	
}