package com.ai.slp.order.search.dto;

import java.util.ArrayList;
import java.util.List;

import com.ai.paas.ipaas.search.vo.SearchCriteria;
import com.ai.paas.ipaas.search.vo.SearchOption;
import com.ai.paas.ipaas.search.vo.Sort;
import com.ai.slp.order.constants.SearchFieldConfConstants;

/**
 * 订单列表查询
 */
public final class OrderSearchCriteria {

    private List<SearchCriteria> searchfieldVos;
    private List<Sort> sortFields = new ArrayList<Sort>();
    private int maxSearchSize = 100;
    private int startSize = 0;

    private OrderSearchCriteria() {
        searchfieldVos = new ArrayList<SearchCriteria>();
    }

    public static class OrderSearchCriteriaBuilder {

        private OrderSearchCriteria orderSearchCriteria;
        SearchCriteria searchCriteria = new SearchCriteria();

        public OrderSearchCriteriaBuilder() {
            orderSearchCriteria = new OrderSearchCriteria();
        }
     			
        //订单来源
        public OrderSearchCriteriaBuilder chlId(String chlId) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.CHL_ID,
            		chlId, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }
        //订单后场状态
        public OrderSearchCriteriaBuilder state(String state) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.STATE,
            		state, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }
        //客户端显示状态
        public OrderSearchCriteriaBuilder displayFlag(String displayFlag) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.DISPLAY_FLAG,
            		displayFlag, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }
        //用户昵称（模糊）
        public OrderSearchCriteriaBuilder userName(String userName) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.USER_NAME,
            		userName, new SearchOption(SearchOption.SearchLogic.should, SearchOption.SearchType.querystring)));
            return this;
        }
        //支付方式
        public OrderSearchCriteriaBuilder payStyle(String payStyle) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.PAY_STYLE,
            		payStyle, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }
        // 排序
        public OrderSearchCriteriaBuilder addOrderBy(String orderByField, Sort.SortOrder order) {
            orderSearchCriteria.sortFields.add(new Sort(orderByField, order));
            return this;
        }
        // 排序，默认降序排，字段名称在Constants类中
        public OrderSearchCriteriaBuilder addOrderBy(String orderByField) {
            return addOrderBy(orderByField, Sort.SortOrder.DESC);
        }

        // 开始的个数
        public OrderSearchCriteriaBuilder startSize(int startSize) {
            orderSearchCriteria.startSize = startSize;
            return this;
        }
        // 最大查询个数
        public OrderSearchCriteriaBuilder maxSearchSize(int maxSearchSize) {
            orderSearchCriteria.maxSearchSize = maxSearchSize;
            return this;
        }

        public OrderSearchCriteria build() {
            return orderSearchCriteria;
        }

    }

    public List<SearchCriteria> getSearchfieldVos() {
        return searchfieldVos;
    }

    public List<Sort> getSortFields() {
        return sortFields;
    }

    public int getMaxSearchSize() {
        return maxSearchSize;
    }

    public int getStartSize() {
        return startSize;
    }
}
