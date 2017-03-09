package com.ai.slp.order.service.business.interfaces.search;


import java.util.List;
import java.util.Map;

import com.ai.paas.ipaas.search.vo.Result;
import com.ai.paas.ipaas.search.vo.SearchCriteria;
import com.ai.paas.ipaas.search.vo.Sort;
import com.ai.slp.order.search.bo.OrderInfo;
import com.ai.slp.order.search.dto.OrderSearchCriteria;

public interface IOrderSearch {
    Result<Map<String, Object>> search(OrderSearchCriteria criteria);
    Result<OrderInfo> search(List<SearchCriteria>searchCriterias, int from,int offset, List<Sort> sorts);
    
    int countAll(List<SearchCriteria> searchCriterias);

}
