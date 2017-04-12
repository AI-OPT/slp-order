package com.ai.slp.order.search.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.paas.ipaas.search.vo.SearchCriteria;
import com.ai.paas.ipaas.search.vo.SearchOption;
import com.ai.slp.order.api.orderlist.param.BehindQueryOrderListRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.SearchFieldConfConstants;

/**
 * elasticsearch查询条件构建
 * @date 2017年3月13日 
 * @author caofz
 */
public class SearchCriteriaStructure {
	
	// 搜索引擎数据公共查询条件
	public static List<SearchCriteria> commonConditions(BehindQueryOrderListRequest request) {
		List<SearchCriteria> searchfieldVos = new ArrayList<SearchCriteria>();
		// 如果用户名称不为空
		if (!StringUtil.isBlank(request.getUserName())) {
			searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.USER_NAME, request.getUserName(),
					new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
		}
		// 如果订单id不为空
		if (request.getOrderId() != null) {
			searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.PORDER_ID, request.getOrderId().toString(),
					new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
		}
		// 如果渠道来源不为空
		if (!StringUtil.isBlank(request.getChlId())) {
			searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.CHL_ID, request.getChlId(),
					new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
		}
		// 如果是否需要物流不为空
		if (!StringUtil.isBlank(request.getDeliveryFlag())) {
			searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.DELIVERY_FLAG, request.getDeliveryFlag(),
					new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
		}
		// 如果仓库id不为空
		if (!StringUtil.isBlank(request.getRouteId())) {
			searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.ORD_EXTENDES_ROUTE_ID, request.getRouteId(),
					new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
		}
		// 如果收货人联系电话不为空
		if (!StringUtil.isBlank(request.getContactTel())) {
			searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.CONTACT_TEL, request.getContactTel(),
					new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
		}
		
		// 如果供应商id不为空
		if (!StringUtil.isBlank(request.getSupplierId())) {
			searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.SUPPLIER_ID, request.getSupplierId(),
					new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
		}
		// 如果商品名称不为空
		if (!StringUtil.isBlank(request.getProdName())) {
			searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.PROD_NAME, request.getProdName(),
					new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.term)));
		}
		// 如果父订单状态不为空
		if (!StringUtil.isBlank(request.getParentOrderState())) {
			searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.PARENT_ORDER_STATE, request.getParentOrderState(),
					new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
		}
		// 如果状态变化开始、结束时间不为空
		if (!StringUtil.isBlank(request.getOrderTimeBegin())  && !StringUtil.isBlank(request.getOrderTimeEnd())) {
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.setOption(new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.range));
			searchCriteria.setField(SearchFieldConfConstants.ORDER_TIME);
			searchCriteria.addFieldValue(request.getOrderTimeBegin());
			searchCriteria.addFieldValue(request.getOrderTimeEnd());
			searchfieldVos.add(searchCriteria);
		}
		// 下单开始时间不为空
		if(!StringUtil.isBlank(request.getOrderTimeBegin())  && StringUtil.isBlank(request.getOrderTimeEnd())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ");
			String end = sdf.format(new Date());
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.setOption(new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.range));
			searchCriteria.setField(SearchFieldConfConstants.ORDER_TIME);
			searchCriteria.addFieldValue(request.getOrderTimeBegin());
			searchCriteria.addFieldValue(end);
			searchfieldVos.add(searchCriteria);
		}
		// 下单结束时间不为空
		if(StringUtil.isBlank(request.getOrderTimeBegin())  && !StringUtil.isBlank(request.getOrderTimeEnd())){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ");
			String end = sdf.format(request.getOrderTimeEnd());
			Timestamp sTime = Timestamp.valueOf(OrdersConstants.START_TIME);
			String start = sdf.format(sTime);
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.setOption(new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.range));
			searchCriteria.setField(SearchFieldConfConstants.ORDER_TIME);
			searchCriteria.addFieldValue(start);
			searchCriteria.addFieldValue(end);
			searchfieldVos.add(searchCriteria);
		}
		// 状态集合不为空
		if (!CollectionUtil.isEmpty(request.getStateList())) {
			SearchCriteria searchCriteria = new SearchCriteria();
			SearchOption option = new SearchOption();
			option.setSearchLogic(SearchOption.SearchLogic.must);
			option.setSearchType(SearchOption.SearchType.term);
			searchCriteria.setFieldValue(request.getStateList());
			searchCriteria.setField(SearchFieldConfConstants.ORD_EXTENDES_STATE);
			searchCriteria.setOption(option);
			searchfieldVos.add(searchCriteria);
		}
		//订单业务标识 列表区分显示显示
		if (!CollectionUtil.isEmpty(request.getFlagList())) {
			SearchCriteria searchCriteria = new SearchCriteria();
			SearchOption option = new SearchOption();
			option.setSearchLogic(SearchOption.SearchLogic.must);
			option.setSearchType(SearchOption.SearchType.term);
			searchCriteria.setFieldValue(request.getFlagList());
			searchCriteria.setField(SearchFieldConfConstants.FLAG);
			searchCriteria.setOption(option);
			searchfieldVos.add(searchCriteria);
		}
		return searchfieldVos;
	}
	
	
	// 根据父订单id查询搜索引擎数据
	public static List<SearchCriteria> commonConditionsByOrderId(long orderId) {
		List<SearchCriteria> searchfieldVos = new ArrayList<SearchCriteria>();
		// 添加订单id查询条件
		searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.PORDER_ID,String.valueOf(orderId),
				new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
		return searchfieldVos;
	}
}
