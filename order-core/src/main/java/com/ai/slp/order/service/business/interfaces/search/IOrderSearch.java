package com.ai.slp.order.service.business.interfaces.search;


import java.util.List;
import java.util.Map;

import com.ai.paas.ipaas.search.vo.Result;
import com.ai.paas.ipaas.search.vo.SearchCriteria;
import com.ai.paas.ipaas.search.vo.Sort;
import com.ai.slp.order.search.bo.OrderInfo;
import com.ai.slp.order.search.dto.OrderSearchCriteria;

public interface IOrderSearch {
	/**
	 * 按条件搜索ses数据
	 * @param criteria
	 * @return
	 * @author caofz
	 * @ApiDocMethod
	 * @ApiCode 
	 * @RestRelativeURL
	 */
    Result<Map<String, Object>> search(OrderSearchCriteria criteria);
    
    /**
     * 按照条件,排序,页数查询es数据
     * @param searchCriterias
     * @param from
     * @param offset
     * @param sorts
     * @return
     * @author caofz
     * @ApiDocMethod
     * @ApiCode 
     * @RestRelativeURL
     */
    Result<OrderInfo> search(List<SearchCriteria>searchCriterias, int from,int offset, List<Sort> sorts);
    
    /**
     * 按条件查询个数
     * @param searchCriterias
     * @return
     * @author caofz
     * @ApiDocMethod
     * @ApiCode 
     * @RestRelativeURL
     */
    int countAll(List<SearchCriteria> searchCriterias);

}
