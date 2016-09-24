	package com.ai.slp.order.api.delivergoods.param;
	
	import java.util.Date;
	import java.util.List;
	
	import javax.validation.constraints.NotNull;
	
	
	import com.ai.opt.base.vo.BaseInfo;
	
	/**
	 * 发货单打印参数
	 * @author caofz
	 *
	 */
	public class DeliverGoodsPrintInfosRequest extends BaseInfo{
	
		private static final long serialVersionUID = 1L;
		
		/**
	     * 订单号
	     */
		@NotNull(message="订单id不能为空")
	    private Long orderId;
	    
		/**
	     * 发货日期
	     */
	    private Date invoiceDate;
	    
	    /**
	     * 打印的商品信息集合
	     */
	    private List<DeliverGoodsPrintVo> invoicePrintVos;
	    
		public Long getOrderId() {
			return orderId;
		}
	
		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}
	
		public Date getInvoiceDate() {
			return invoiceDate;
		}
	
		public void setInvoiceDate(Date invoiceDate) {
			this.invoiceDate = invoiceDate;
		}
	
		public List<DeliverGoodsPrintVo> getInvoicePrintVos() {
			return invoicePrintVos;
		}
	
		public void setInvoicePrintVos(List<DeliverGoodsPrintVo> invoicePrintVos) {
			this.invoicePrintVos = invoicePrintVos;
		}
		
	}
