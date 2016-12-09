package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.dao.mapper.attach.BehindOrdOrderAttach;

/**
 * 
 * 多表查询订单信息
 * Date: 2016年6月30日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * @author caofz
 *
 */
public interface IOrdOrderAttachAtomSV {
	
	/**
	 * 运营后台查询订单数量
	 * @param subFlag
	 * @param orderListRequest
	 * @return
	 */
	public int behindQueryCount(BehindQueryOrderListRequest orderListRequest,String states);
	
	
	/**
	 * 运营后台查询订单信息
	 * @param orderListRequest
	 * @return
	 */
	public List<BehindOrdOrderAttach> behindQueryOrderBySearch(BehindQueryOrderListRequest orderListRequest,
			String states);
}
