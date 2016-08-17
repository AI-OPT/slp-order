package com.ai.slp.order.dao.mapper.bo;

import java.util.ArrayList;
import java.util.List;

public class OrdOdProdExtendCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OrdOdProdExtendCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andProdDetalExtendIdIsNull() {
            addCriterion("PROD_DETAL_EXTEND_ID is null");
            return (Criteria) this;
        }

        public Criteria andProdDetalExtendIdIsNotNull() {
            addCriterion("PROD_DETAL_EXTEND_ID is not null");
            return (Criteria) this;
        }

        public Criteria andProdDetalExtendIdEqualTo(long value) {
            addCriterion("PROD_DETAL_EXTEND_ID =", value, "prodDetalExtendId");
            return (Criteria) this;
        }

        public Criteria andProdDetalExtendIdNotEqualTo(long value) {
            addCriterion("PROD_DETAL_EXTEND_ID <>", value, "prodDetalExtendId");
            return (Criteria) this;
        }

        public Criteria andProdDetalExtendIdGreaterThan(long value) {
            addCriterion("PROD_DETAL_EXTEND_ID >", value, "prodDetalExtendId");
            return (Criteria) this;
        }

        public Criteria andProdDetalExtendIdGreaterThanOrEqualTo(long value) {
            addCriterion("PROD_DETAL_EXTEND_ID >=", value, "prodDetalExtendId");
            return (Criteria) this;
        }

        public Criteria andProdDetalExtendIdLessThan(long value) {
            addCriterion("PROD_DETAL_EXTEND_ID <", value, "prodDetalExtendId");
            return (Criteria) this;
        }

        public Criteria andProdDetalExtendIdLessThanOrEqualTo(long value) {
            addCriterion("PROD_DETAL_EXTEND_ID <=", value, "prodDetalExtendId");
            return (Criteria) this;
        }

        public Criteria andProdDetalExtendIdIn(List<Long> values) {
            addCriterion("PROD_DETAL_EXTEND_ID in", values, "prodDetalExtendId");
            return (Criteria) this;
        }

        public Criteria andProdDetalExtendIdNotIn(List<Long> values) {
            addCriterion("PROD_DETAL_EXTEND_ID not in", values, "prodDetalExtendId");
            return (Criteria) this;
        }

        public Criteria andProdDetalExtendIdBetween(long value1, long value2) {
            addCriterion("PROD_DETAL_EXTEND_ID between", value1, value2, "prodDetalExtendId");
            return (Criteria) this;
        }

        public Criteria andProdDetalExtendIdNotBetween(long value1, long value2) {
            addCriterion("PROD_DETAL_EXTEND_ID not between", value1, value2, "prodDetalExtendId");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdIsNull() {
            addCriterion("PROD_DETAL_ID is null");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdIsNotNull() {
            addCriterion("PROD_DETAL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdEqualTo(long value) {
            addCriterion("PROD_DETAL_ID =", value, "prodDetalId");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdNotEqualTo(long value) {
            addCriterion("PROD_DETAL_ID <>", value, "prodDetalId");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdGreaterThan(long value) {
            addCriterion("PROD_DETAL_ID >", value, "prodDetalId");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdGreaterThanOrEqualTo(long value) {
            addCriterion("PROD_DETAL_ID >=", value, "prodDetalId");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdLessThan(long value) {
            addCriterion("PROD_DETAL_ID <", value, "prodDetalId");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdLessThanOrEqualTo(long value) {
            addCriterion("PROD_DETAL_ID <=", value, "prodDetalId");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdIn(List<Long> values) {
            addCriterion("PROD_DETAL_ID in", values, "prodDetalId");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdNotIn(List<Long> values) {
            addCriterion("PROD_DETAL_ID not in", values, "prodDetalId");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdBetween(long value1, long value2) {
            addCriterion("PROD_DETAL_ID between", value1, value2, "prodDetalId");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdNotBetween(long value1, long value2) {
            addCriterion("PROD_DETAL_ID not between", value1, value2, "prodDetalId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(long value) {
            addCriterion("ORDER_ID =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(long value) {
            addCriterion("ORDER_ID <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(long value) {
            addCriterion("ORDER_ID >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(long value) {
            addCriterion("ORDER_ID >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(long value) {
            addCriterion("ORDER_ID <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(long value) {
            addCriterion("ORDER_ID <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Long> values) {
            addCriterion("ORDER_ID in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Long> values) {
            addCriterion("ORDER_ID not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(long value1, long value2) {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(long value1, long value2) {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNull() {
            addCriterion("TENANT_ID is null");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNotNull() {
            addCriterion("TENANT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTenantIdEqualTo(String value) {
            addCriterion("TENANT_ID =", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotEqualTo(String value) {
            addCriterion("TENANT_ID <>", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThan(String value) {
            addCriterion("TENANT_ID >", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThanOrEqualTo(String value) {
            addCriterion("TENANT_ID >=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThan(String value) {
            addCriterion("TENANT_ID <", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThanOrEqualTo(String value) {
            addCriterion("TENANT_ID <=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLike(String value) {
            addCriterion("TENANT_ID like", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotLike(String value) {
            addCriterion("TENANT_ID not like", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdIn(List<String> values) {
            addCriterion("TENANT_ID in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotIn(List<String> values) {
            addCriterion("TENANT_ID not in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdBetween(String value1, String value2) {
            addCriterion("TENANT_ID between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotBetween(String value1, String value2) {
            addCriterion("TENANT_ID not between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andInfoJsonIsNull() {
            addCriterion("INFO_JSON is null");
            return (Criteria) this;
        }

        public Criteria andInfoJsonIsNotNull() {
            addCriterion("INFO_JSON is not null");
            return (Criteria) this;
        }

        public Criteria andInfoJsonEqualTo(String value) {
            addCriterion("INFO_JSON =", value, "infoJson");
            return (Criteria) this;
        }

        public Criteria andInfoJsonNotEqualTo(String value) {
            addCriterion("INFO_JSON <>", value, "infoJson");
            return (Criteria) this;
        }

        public Criteria andInfoJsonGreaterThan(String value) {
            addCriterion("INFO_JSON >", value, "infoJson");
            return (Criteria) this;
        }

        public Criteria andInfoJsonGreaterThanOrEqualTo(String value) {
            addCriterion("INFO_JSON >=", value, "infoJson");
            return (Criteria) this;
        }

        public Criteria andInfoJsonLessThan(String value) {
            addCriterion("INFO_JSON <", value, "infoJson");
            return (Criteria) this;
        }

        public Criteria andInfoJsonLessThanOrEqualTo(String value) {
            addCriterion("INFO_JSON <=", value, "infoJson");
            return (Criteria) this;
        }

        public Criteria andInfoJsonLike(String value) {
            addCriterion("INFO_JSON like", value, "infoJson");
            return (Criteria) this;
        }

        public Criteria andInfoJsonNotLike(String value) {
            addCriterion("INFO_JSON not like", value, "infoJson");
            return (Criteria) this;
        }

        public Criteria andInfoJsonIn(List<String> values) {
            addCriterion("INFO_JSON in", values, "infoJson");
            return (Criteria) this;
        }

        public Criteria andInfoJsonNotIn(List<String> values) {
            addCriterion("INFO_JSON not in", values, "infoJson");
            return (Criteria) this;
        }

        public Criteria andInfoJsonBetween(String value1, String value2) {
            addCriterion("INFO_JSON between", value1, value2, "infoJson");
            return (Criteria) this;
        }

        public Criteria andInfoJsonNotBetween(String value1, String value2) {
            addCriterion("INFO_JSON not between", value1, value2, "infoJson");
            return (Criteria) this;
        }

        public Criteria andBatchFlagIsNull() {
            addCriterion("BATCH_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andBatchFlagIsNotNull() {
            addCriterion("BATCH_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andBatchFlagEqualTo(String value) {
            addCriterion("BATCH_FLAG =", value, "batchFlag");
            return (Criteria) this;
        }

        public Criteria andBatchFlagNotEqualTo(String value) {
            addCriterion("BATCH_FLAG <>", value, "batchFlag");
            return (Criteria) this;
        }

        public Criteria andBatchFlagGreaterThan(String value) {
            addCriterion("BATCH_FLAG >", value, "batchFlag");
            return (Criteria) this;
        }

        public Criteria andBatchFlagGreaterThanOrEqualTo(String value) {
            addCriterion("BATCH_FLAG >=", value, "batchFlag");
            return (Criteria) this;
        }

        public Criteria andBatchFlagLessThan(String value) {
            addCriterion("BATCH_FLAG <", value, "batchFlag");
            return (Criteria) this;
        }

        public Criteria andBatchFlagLessThanOrEqualTo(String value) {
            addCriterion("BATCH_FLAG <=", value, "batchFlag");
            return (Criteria) this;
        }

        public Criteria andBatchFlagLike(String value) {
            addCriterion("BATCH_FLAG like", value, "batchFlag");
            return (Criteria) this;
        }

        public Criteria andBatchFlagNotLike(String value) {
            addCriterion("BATCH_FLAG not like", value, "batchFlag");
            return (Criteria) this;
        }

        public Criteria andBatchFlagIn(List<String> values) {
            addCriterion("BATCH_FLAG in", values, "batchFlag");
            return (Criteria) this;
        }

        public Criteria andBatchFlagNotIn(List<String> values) {
            addCriterion("BATCH_FLAG not in", values, "batchFlag");
            return (Criteria) this;
        }

        public Criteria andBatchFlagBetween(String value1, String value2) {
            addCriterion("BATCH_FLAG between", value1, value2, "batchFlag");
            return (Criteria) this;
        }

        public Criteria andBatchFlagNotBetween(String value1, String value2) {
            addCriterion("BATCH_FLAG not between", value1, value2, "batchFlag");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}