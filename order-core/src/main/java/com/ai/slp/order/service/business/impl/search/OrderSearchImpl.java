package com.ai.slp.order.service.business.impl.search;


import java.util.List;

import com.ai.paas.ipaas.search.ISearchClient;
import com.ai.paas.ipaas.search.vo.Result;
import com.ai.paas.ipaas.search.vo.SearchCriteria;
import com.ai.paas.ipaas.search.vo.Sort;
import com.ai.slp.order.constants.SearchConstants;
import com.ai.slp.order.manager.ESClientManager;
import com.ai.slp.order.search.bo.OrderInfo;
import com.ai.slp.order.search.dto.OrderSearchCriteria;
import com.ai.slp.order.service.business.interfaces.search.IOrderSearch;

public class OrderSearchImpl implements IOrderSearch {

	@Override
	public Result search(OrderSearchCriteria criteria) {
		ISearchClient searchClient = ESClientManager.getSesClient(SearchConstants.SearchNameSpace);
		return searchClient.search(criteria.getSearchfieldVos(), criteria.getStartSize(), criteria.getMaxSearchSize(),
				criteria.getSortFields(), OrderInfo.class);
	}

	@Override
	public Result<OrderInfo> search(List<SearchCriteria> searchCriterias, int from, int offset,
			 List<Sort> sorts) {
		ISearchClient searchClient = ESClientManager.getSesClient(SearchConstants.SearchNameSpace);
		return searchClient.search(searchCriterias, from, offset, sorts, OrderInfo.class);
	}

	@Override
	public int countAll(List<SearchCriteria> searchCriterias) {
		ISearchClient searchClient = ESClientManager.getSesClient(SearchConstants.SearchNameSpace);
		Result<OrderInfo> result = searchClient.search(searchCriterias, 0, 10000, null, OrderInfo.class);
		return result==null?0:(int)result.getCount();
	}
}
