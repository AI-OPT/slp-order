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
        // 业务标识
       /* public OrderSearchCriteriaBuilder flag(String flag) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.FLAG,
            		flag, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }*/
        //用户id
       /* public OrderSearchCriteriaBuilder userId(String userId) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.USER_ID,
            		userId, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }*/
        //用户类型
      /*  public OrderSearchCriteriaBuilder userType(String userType) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.USER_TYPE,
            		userType, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }*/
        //报价标识
      /*  public OrderSearchCriteriaBuilder subFlag(String subFlag) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.SUB_FLAG,
            		subFlag, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }*/
        //订单来源
        public OrderSearchCriteriaBuilder chlId(String chlId) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.CHL_ID,
            		chlId, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }
        //订单类型
    /*    public OrderSearchCriteriaBuilder orderType(String orderType) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.ORDER_TYPE,
            		orderType, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }*/
        //翻译主题（模糊）
     /*   public OrderSearchCriteriaBuilder translateName(String translateName) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.TRANSLATE_NAME,
            		translateName, new SearchOption(SearchOption.SearchLogic.should, SearchOption.SearchType.querystring)));
            return this;
        }*/
        //企业id
       /* public OrderSearchCriteriaBuilder corporaId(String corporaId) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.CORPORA_ID,
            		corporaId, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }*/
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
        //译员类型
      /*  public OrderSearchCriteriaBuilder interperType(String interperType) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.INTERPER_TYPE,
            		interperType, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }*/
        //lsp编号
        public OrderSearchCriteriaBuilder lspId(String lspId) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.LSP_ID,
            		lspId, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }
        //领取人（译员）id
        public OrderSearchCriteriaBuilder interperId(String interperId) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.INTERPER_ID,
            		interperId, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }
        //关键词
        public OrderSearchCriteriaBuilder keywords(String keywords) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.KEYWORDS,
            		keywords, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }
        //返工标识
        public OrderSearchCriteriaBuilder updateFlag(String updateFlag) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.UPDATE_FLAG,
            		updateFlag, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }
        //语言对ID 
        public OrderSearchCriteriaBuilder langungePaire(String langungePaire) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.LANGUNGE_ID,
            		langungePaire, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }
        //用途ID
        public OrderSearchCriteriaBuilder useCode(String useCode) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.USE_CODE,
            		useCode, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }
        //领域ID
        public OrderSearchCriteriaBuilder fieldCode(String fieldCode) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.FIELD_CODE,
            		fieldCode, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }
        //用户昵称（模糊）
        public OrderSearchCriteriaBuilder userName(String userName) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.USER_NAME,
            		userName, new SearchOption(SearchOption.SearchLogic.should, SearchOption.SearchType.querystring)));
            return this;
        }
        //lsp名称
        public OrderSearchCriteriaBuilder lspName(String lspName) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.LSP_NAME,
            		lspName, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }
        //支付方式
        public OrderSearchCriteriaBuilder payStyle(String payStyle) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.PAY_STYLE,
            		payStyle, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
            return this;
        }
        //报价人（费用操作工号）
        public OrderSearchCriteriaBuilder updateOperName(String updateOperName) {
            orderSearchCriteria.searchfieldVos.add(new SearchCriteria(SearchFieldConfConstants.UPDATE_OPER_NAME,
            		updateOperName, new SearchOption(SearchOption.SearchLogic.must, SearchOption.SearchType.querystring)));
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
