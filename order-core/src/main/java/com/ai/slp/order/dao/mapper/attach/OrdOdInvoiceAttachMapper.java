package com.ai.slp.order.dao.mapper.attach;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;

public interface OrdOdInvoiceAttachMapper {
	
	@SelectProvider(type = OrdOdInvoiceSqlProvider.class, method = "count")
	public int count(@Param("subFlag") String subFlag,
			@Param("orderId") Long orderId, @Param("tenantId") String tenantId,
			@Param("invoiceTitle") String invoiceTitle, @Param("invoiceStatu") String invoiceStatus);
	
	@SelectProvider(type = OrdOdInvoiceSqlProvider.class, method = "queryInvoice")
	public List<OrdOdInvoice> selectList(@Param("subFlag") String subFlag,@Param("pageCount") Integer pageCount, 
			@Param("pageSize") Integer pageSize, 
			@Param("orderId") Long orderId, @Param("tenantId") String tenantId,
			@Param("invoiceTitle") String invoiceTitle, @Param("invoiceStatus") String invoiceStatus);

}
