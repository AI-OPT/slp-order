package com.ai.slp.order.api.invoiceprint.param;

import java.util.ArrayList;
import java.util.List;

import com.ai.opt.base.vo.BaseResponse;

public class InvoiceSumbitResponse extends BaseResponse{

	private static final long serialVersionUID = 1L;
	
	private List<InvoiceSumbitVo> invoiceSumbitVo=new ArrayList<InvoiceSumbitVo>();

	public List<InvoiceSumbitVo> getInvoiceSumbitVo() {
		return invoiceSumbitVo;
	}

	public void setInvoiceSumbitVo(List<InvoiceSumbitVo> invoiceSumbitVo) {
		this.invoiceSumbitVo = invoiceSumbitVo;
	}
}
