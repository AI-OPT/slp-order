package com.ai.slp.order.dao.mapper.bo;

import java.util.ArrayList;
import java.util.List;

public class OrdOdFeeOffsetCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OrdOdFeeOffsetCriteria() {
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

        public Criteria andFeeOffsetIdIsNull() {
            addCriterion("FEE_OFFSET_ID is null");
            return (Criteria) this;
        }

        public Criteria andFeeOffsetIdIsNotNull() {
            addCriterion("FEE_OFFSET_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFeeOffsetIdEqualTo(Long value) {
            addCriterion("FEE_OFFSET_ID =", value, "feeOffsetId");
            return (Criteria) this;
        }

        public Criteria andFeeOffsetIdNotEqualTo(Long value) {
            addCriterion("FEE_OFFSET_ID <>", value, "feeOffsetId");
            return (Criteria) this;
        }

        public Criteria andFeeOffsetIdGreaterThan(Long value) {
            addCriterion("FEE_OFFSET_ID >", value, "feeOffsetId");
            return (Criteria) this;
        }

        public Criteria andFeeOffsetIdGreaterThanOrEqualTo(Long value) {
            addCriterion("FEE_OFFSET_ID >=", value, "feeOffsetId");
            return (Criteria) this;
        }

        public Criteria andFeeOffsetIdLessThan(Long value) {
            addCriterion("FEE_OFFSET_ID <", value, "feeOffsetId");
            return (Criteria) this;
        }

        public Criteria andFeeOffsetIdLessThanOrEqualTo(Long value) {
            addCriterion("FEE_OFFSET_ID <=", value, "feeOffsetId");
            return (Criteria) this;
        }

        public Criteria andFeeOffsetIdIn(List<Long> values) {
            addCriterion("FEE_OFFSET_ID in", values, "feeOffsetId");
            return (Criteria) this;
        }

        public Criteria andFeeOffsetIdNotIn(List<Long> values) {
            addCriterion("FEE_OFFSET_ID not in", values, "feeOffsetId");
            return (Criteria) this;
        }

        public Criteria andFeeOffsetIdBetween(Long value1, Long value2) {
            addCriterion("FEE_OFFSET_ID between", value1, value2, "feeOffsetId");
            return (Criteria) this;
        }

        public Criteria andFeeOffsetIdNotBetween(Long value1, Long value2) {
            addCriterion("FEE_OFFSET_ID not between", value1, value2, "feeOffsetId");
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

        public Criteria andBalacneIfIdIsNull() {
            addCriterion("BALACNE_IF_ID is null");
            return (Criteria) this;
        }

        public Criteria andBalacneIfIdIsNotNull() {
            addCriterion("BALACNE_IF_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBalacneIfIdEqualTo(Long value) {
            addCriterion("BALACNE_IF_ID =", value, "balacneIfId");
            return (Criteria) this;
        }

        public Criteria andBalacneIfIdNotEqualTo(Long value) {
            addCriterion("BALACNE_IF_ID <>", value, "balacneIfId");
            return (Criteria) this;
        }

        public Criteria andBalacneIfIdGreaterThan(Long value) {
            addCriterion("BALACNE_IF_ID >", value, "balacneIfId");
            return (Criteria) this;
        }

        public Criteria andBalacneIfIdGreaterThanOrEqualTo(Long value) {
            addCriterion("BALACNE_IF_ID >=", value, "balacneIfId");
            return (Criteria) this;
        }

        public Criteria andBalacneIfIdLessThan(Long value) {
            addCriterion("BALACNE_IF_ID <", value, "balacneIfId");
            return (Criteria) this;
        }

        public Criteria andBalacneIfIdLessThanOrEqualTo(Long value) {
            addCriterion("BALACNE_IF_ID <=", value, "balacneIfId");
            return (Criteria) this;
        }

        public Criteria andBalacneIfIdIn(List<Long> values) {
            addCriterion("BALACNE_IF_ID in", values, "balacneIfId");
            return (Criteria) this;
        }

        public Criteria andBalacneIfIdNotIn(List<Long> values) {
            addCriterion("BALACNE_IF_ID not in", values, "balacneIfId");
            return (Criteria) this;
        }

        public Criteria andBalacneIfIdBetween(Long value1, Long value2) {
            addCriterion("BALACNE_IF_ID between", value1, value2, "balacneIfId");
            return (Criteria) this;
        }

        public Criteria andBalacneIfIdNotBetween(Long value1, Long value2) {
            addCriterion("BALACNE_IF_ID not between", value1, value2, "balacneIfId");
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

        public Criteria andOrderIdEqualTo(Long value) {
            addCriterion("ORDER_ID =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Long value) {
            addCriterion("ORDER_ID <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Long value) {
            addCriterion("ORDER_ID >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ORDER_ID >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Long value) {
            addCriterion("ORDER_ID <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Long value) {
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

        public Criteria andOrderIdBetween(Long value1, Long value2) {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Long value1, Long value2) {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
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

        public Criteria andProdDetalIdEqualTo(Long value) {
            addCriterion("PROD_DETAL_ID =", value, "prodDetalId");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdNotEqualTo(Long value) {
            addCriterion("PROD_DETAL_ID <>", value, "prodDetalId");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdGreaterThan(Long value) {
            addCriterion("PROD_DETAL_ID >", value, "prodDetalId");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdGreaterThanOrEqualTo(Long value) {
            addCriterion("PROD_DETAL_ID >=", value, "prodDetalId");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdLessThan(Long value) {
            addCriterion("PROD_DETAL_ID <", value, "prodDetalId");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdLessThanOrEqualTo(Long value) {
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

        public Criteria andProdDetalIdBetween(Long value1, Long value2) {
            addCriterion("PROD_DETAL_ID between", value1, value2, "prodDetalId");
            return (Criteria) this;
        }

        public Criteria andProdDetalIdNotBetween(Long value1, Long value2) {
            addCriterion("PROD_DETAL_ID not between", value1, value2, "prodDetalId");
            return (Criteria) this;
        }

        public Criteria andProdIdIsNull() {
            addCriterion("PROD_ID is null");
            return (Criteria) this;
        }

        public Criteria andProdIdIsNotNull() {
            addCriterion("PROD_ID is not null");
            return (Criteria) this;
        }

        public Criteria andProdIdEqualTo(String value) {
            addCriterion("PROD_ID =", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdNotEqualTo(String value) {
            addCriterion("PROD_ID <>", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdGreaterThan(String value) {
            addCriterion("PROD_ID >", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdGreaterThanOrEqualTo(String value) {
            addCriterion("PROD_ID >=", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdLessThan(String value) {
            addCriterion("PROD_ID <", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdLessThanOrEqualTo(String value) {
            addCriterion("PROD_ID <=", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdLike(String value) {
            addCriterion("PROD_ID like", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdNotLike(String value) {
            addCriterion("PROD_ID not like", value, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdIn(List<String> values) {
            addCriterion("PROD_ID in", values, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdNotIn(List<String> values) {
            addCriterion("PROD_ID not in", values, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdBetween(String value1, String value2) {
            addCriterion("PROD_ID between", value1, value2, "prodId");
            return (Criteria) this;
        }

        public Criteria andProdIdNotBetween(String value1, String value2) {
            addCriterion("PROD_ID not between", value1, value2, "prodId");
            return (Criteria) this;
        }

        public Criteria andOffsetFeeIsNull() {
            addCriterion("OFFSET_FEE is null");
            return (Criteria) this;
        }

        public Criteria andOffsetFeeIsNotNull() {
            addCriterion("OFFSET_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andOffsetFeeEqualTo(Long value) {
            addCriterion("OFFSET_FEE =", value, "offsetFee");
            return (Criteria) this;
        }

        public Criteria andOffsetFeeNotEqualTo(Long value) {
            addCriterion("OFFSET_FEE <>", value, "offsetFee");
            return (Criteria) this;
        }

        public Criteria andOffsetFeeGreaterThan(Long value) {
            addCriterion("OFFSET_FEE >", value, "offsetFee");
            return (Criteria) this;
        }

        public Criteria andOffsetFeeGreaterThanOrEqualTo(Long value) {
            addCriterion("OFFSET_FEE >=", value, "offsetFee");
            return (Criteria) this;
        }

        public Criteria andOffsetFeeLessThan(Long value) {
            addCriterion("OFFSET_FEE <", value, "offsetFee");
            return (Criteria) this;
        }

        public Criteria andOffsetFeeLessThanOrEqualTo(Long value) {
            addCriterion("OFFSET_FEE <=", value, "offsetFee");
            return (Criteria) this;
        }

        public Criteria andOffsetFeeIn(List<Long> values) {
            addCriterion("OFFSET_FEE in", values, "offsetFee");
            return (Criteria) this;
        }

        public Criteria andOffsetFeeNotIn(List<Long> values) {
            addCriterion("OFFSET_FEE not in", values, "offsetFee");
            return (Criteria) this;
        }

        public Criteria andOffsetFeeBetween(Long value1, Long value2) {
            addCriterion("OFFSET_FEE between", value1, value2, "offsetFee");
            return (Criteria) this;
        }

        public Criteria andOffsetFeeNotBetween(Long value1, Long value2) {
            addCriterion("OFFSET_FEE not between", value1, value2, "offsetFee");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
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