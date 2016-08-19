package com.ai.slp.order.api.deliveryorderprint.param;


import java.util.List;

import com.ai.opt.base.vo.BaseResponse;

/**
 * 订单提货单展示的参数
 * @date 2016年8月10日 
 * @author caofz
 */
public class DeliveryOrderPrintResponse extends BaseResponse{

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
     * 总的数量
     */
    private long sum;
    
    /**
     * 打印的商品信息集合
     */
    private List<DeliveryProdPrintVo> deliveryProdPrintVos;

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
	
	public long getSum() {
		return sum;
	}

	public void setSum(long sum) {
		this.sum = sum;
	}

	public List<DeliveryProdPrintVo> getDeliveryProdPrintVos() {
		return deliveryProdPrintVos;
	}

	public void setDeliveryProdPrintVos(List<DeliveryProdPrintVo> deliveryProdPrintVos) {
		this.deliveryProdPrintVos = deliveryProdPrintVos;
	}
}