package com.ai.slp.order.api.deliveryorderprint.param;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.ai.opt.base.vo.BaseInfo;

public class DeliveryOrderPrintInfosRequest extends BaseInfo{
	
		private static final long serialVersionUID = 1L;

		/**
		 * 订单号
		 */
		@NotNull(message="订单id不能为空")
	    private Long orderId;
	    
	    /**
	     * 收货人姓名
	     */
		@NotBlank(message="订单id不能为空")
	    private String contactName;
	    
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

		public List<DeliveryProdPrintVo> getDeliveryProdPrintVos() {
			return deliveryProdPrintVos;
		}

		public void setDeliveryProdPrintVos(List<DeliveryProdPrintVo> deliveryProdPrintVos) {
			this.deliveryProdPrintVos = deliveryProdPrintVos;
		}
}
