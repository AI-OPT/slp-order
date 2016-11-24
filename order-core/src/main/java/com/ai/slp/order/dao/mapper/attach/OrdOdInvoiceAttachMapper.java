package com.ai.slp.order.dao.mapper.attach;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;

import com.ai.slp.order.dao.mapper.bo.OrdOdInvoice;

public interface OrdOdInvoiceAttachMapper {
	
	@SelectProvider(type = OrdOdInvoiceSqlProvider.class, method = "count")
	public int count(@Param("subFlag") String subFlag,
			@Param("orderId") Long orderId, @Param("tenantId") String tenantId,
			@Param("invoiceTitle") String invoiceTitle, @Param("invoiceStatus") String invoiceStatus);
	
	
	@SelectProvider(type = OrdOdInvoiceSqlProvider.class, method = "queryInvoice")
	@Results({ @Result(id = true, property = "orderId", column = "order_id"),
         @Result(property = "tenantId", column = "tenant_id"),
         @Result(property = "invoiceType", column = "invoice_type"),
         @Result(property = "invoiceTitle", column = "invoice_title"),
         @Result(property = "invoiceContent", column = "invoice_content"),
         @Result(property = "invoiceStatus", column = "invoice_status"),
         @Result(property = "invoiceId", column = "invoice_id"),
         @Result(property = "invoiceNum", column = "invoice_num")
       })
	public List<OrdOdInvoice> selectList(@Param("subFlag") String subFlag,@Param("pageCount") Integer pageCount, 
			@Param("pageSize") Integer pageSize, 
			@Param("orderId") Long orderId, @Param("tenantId") String tenantId,
			@Param("invoiceTitle") String invoiceTitle, @Param("invoiceStatus") String invoiceStatus);

}
