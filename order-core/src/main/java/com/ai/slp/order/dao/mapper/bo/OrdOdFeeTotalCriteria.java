package com.ai.slp.order.dao.mapper.bo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrdOdFeeTotalCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OrdOdFeeTotalCriteria() {
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

        public Criteria andPayFlagIsNull() {
            addCriterion("PAY_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andPayFlagIsNotNull() {
            addCriterion("PAY_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andPayFlagEqualTo(String value) {
            addCriterion("PAY_FLAG =", value, "payFlag");
            return (Criteria) this;
        }

        public Criteria andPayFlagNotEqualTo(String value) {
            addCriterion("PAY_FLAG <>", value, "payFlag");
            return (Criteria) this;
        }

        public Criteria andPayFlagGreaterThan(String value) {
            addCriterion("PAY_FLAG >", value, "payFlag");
            return (Criteria) this;
        }

        public Criteria andPayFlagGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_FLAG >=", value, "payFlag");
            return (Criteria) this;
        }

        public Criteria andPayFlagLessThan(String value) {
            addCriterion("PAY_FLAG <", value, "payFlag");
            return (Criteria) this;
        }

        public Criteria andPayFlagLessThanOrEqualTo(String value) {
            addCriterion("PAY_FLAG <=", value, "payFlag");
            return (Criteria) this;
        }

        public Criteria andPayFlagLike(String value) {
            addCriterion("PAY_FLAG like", value, "payFlag");
            return (Criteria) this;
        }

        public Criteria andPayFlagNotLike(String value) {
            addCriterion("PAY_FLAG not like", value, "payFlag");
            return (Criteria) this;
        }

        public Criteria andPayFlagIn(List<String> values) {
            addCriterion("PAY_FLAG in", values, "payFlag");
            return (Criteria) this;
        }

        public Criteria andPayFlagNotIn(List<String> values) {
            addCriterion("PAY_FLAG not in", values, "payFlag");
            return (Criteria) this;
        }

        public Criteria andPayFlagBetween(String value1, String value2) {
            addCriterion("PAY_FLAG between", value1, value2, "payFlag");
            return (Criteria) this;
        }

        public Criteria andPayFlagNotBetween(String value1, String value2) {
            addCriterion("PAY_FLAG not between", value1, value2, "payFlag");
            return (Criteria) this;
        }

        public Criteria andTotalFeeIsNull() {
            addCriterion("TOTAL_FEE is null");
            return (Criteria) this;
        }

        public Criteria andTotalFeeIsNotNull() {
            addCriterion("TOTAL_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andTotalFeeEqualTo(long value) {
            addCriterion("TOTAL_FEE =", value, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeNotEqualTo(long value) {
            addCriterion("TOTAL_FEE <>", value, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeGreaterThan(long value) {
            addCriterion("TOTAL_FEE >", value, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeGreaterThanOrEqualTo(long value) {
            addCriterion("TOTAL_FEE >=", value, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeLessThan(long value) {
            addCriterion("TOTAL_FEE <", value, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeLessThanOrEqualTo(long value) {
            addCriterion("TOTAL_FEE <=", value, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeIn(List<Long> values) {
            addCriterion("TOTAL_FEE in", values, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeNotIn(List<Long> values) {
            addCriterion("TOTAL_FEE not in", values, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeBetween(long value1, long value2) {
            addCriterion("TOTAL_FEE between", value1, value2, "totalFee");
            return (Criteria) this;
        }

        public Criteria andTotalFeeNotBetween(long value1, long value2) {
            addCriterion("TOTAL_FEE not between", value1, value2, "totalFee");
            return (Criteria) this;
        }

        public Criteria andDiscountFeeIsNull() {
            addCriterion("DISCOUNT_FEE is null");
            return (Criteria) this;
        }

        public Criteria andDiscountFeeIsNotNull() {
            addCriterion("DISCOUNT_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andDiscountFeeEqualTo(long value) {
            addCriterion("DISCOUNT_FEE =", value, "discountFee");
            return (Criteria) this;
        }

        public Criteria andDiscountFeeNotEqualTo(long value) {
            addCriterion("DISCOUNT_FEE <>", value, "discountFee");
            return (Criteria) this;
        }

        public Criteria andDiscountFeeGreaterThan(long value) {
            addCriterion("DISCOUNT_FEE >", value, "discountFee");
            return (Criteria) this;
        }

        public Criteria andDiscountFeeGreaterThanOrEqualTo(long value) {
            addCriterion("DISCOUNT_FEE >=", value, "discountFee");
            return (Criteria) this;
        }

        public Criteria andDiscountFeeLessThan(long value) {
            addCriterion("DISCOUNT_FEE <", value, "discountFee");
            return (Criteria) this;
        }

        public Criteria andDiscountFeeLessThanOrEqualTo(long value) {
            addCriterion("DISCOUNT_FEE <=", value, "discountFee");
            return (Criteria) this;
        }

        public Criteria andDiscountFeeIn(List<Long> values) {
            addCriterion("DISCOUNT_FEE in", values, "discountFee");
            return (Criteria) this;
        }

        public Criteria andDiscountFeeNotIn(List<Long> values) {
            addCriterion("DISCOUNT_FEE not in", values, "discountFee");
            return (Criteria) this;
        }

        public Criteria andDiscountFeeBetween(long value1, long value2) {
            addCriterion("DISCOUNT_FEE between", value1, value2, "discountFee");
            return (Criteria) this;
        }

        public Criteria andDiscountFeeNotBetween(long value1, long value2) {
            addCriterion("DISCOUNT_FEE not between", value1, value2, "discountFee");
            return (Criteria) this;
        }

        public Criteria andOperDiscountFeeIsNull() {
            addCriterion("OPER_DISCOUNT_FEE is null");
            return (Criteria) this;
        }

        public Criteria andOperDiscountFeeIsNotNull() {
            addCriterion("OPER_DISCOUNT_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andOperDiscountFeeEqualTo(long value) {
            addCriterion("OPER_DISCOUNT_FEE =", value, "operDiscountFee");
            return (Criteria) this;
        }

        public Criteria andOperDiscountFeeNotEqualTo(long value) {
            addCriterion("OPER_DISCOUNT_FEE <>", value, "operDiscountFee");
            return (Criteria) this;
        }

        public Criteria andOperDiscountFeeGreaterThan(long value) {
            addCriterion("OPER_DISCOUNT_FEE >", value, "operDiscountFee");
            return (Criteria) this;
        }

        public Criteria andOperDiscountFeeGreaterThanOrEqualTo(long value) {
            addCriterion("OPER_DISCOUNT_FEE >=", value, "operDiscountFee");
            return (Criteria) this;
        }

        public Criteria andOperDiscountFeeLessThan(long value) {
            addCriterion("OPER_DISCOUNT_FEE <", value, "operDiscountFee");
            return (Criteria) this;
        }

        public Criteria andOperDiscountFeeLessThanOrEqualTo(long value) {
            addCriterion("OPER_DISCOUNT_FEE <=", value, "operDiscountFee");
            return (Criteria) this;
        }

        public Criteria andOperDiscountFeeIn(List<Long> values) {
            addCriterion("OPER_DISCOUNT_FEE in", values, "operDiscountFee");
            return (Criteria) this;
        }

        public Criteria andOperDiscountFeeNotIn(List<Long> values) {
            addCriterion("OPER_DISCOUNT_FEE not in", values, "operDiscountFee");
            return (Criteria) this;
        }

        public Criteria andOperDiscountFeeBetween(long value1, long value2) {
            addCriterion("OPER_DISCOUNT_FEE between", value1, value2, "operDiscountFee");
            return (Criteria) this;
        }

        public Criteria andOperDiscountFeeNotBetween(long value1, long value2) {
            addCriterion("OPER_DISCOUNT_FEE not between", value1, value2, "operDiscountFee");
            return (Criteria) this;
        }

        public Criteria andOperDiscountDescIsNull() {
            addCriterion("OPER_DISCOUNT_DESC is null");
            return (Criteria) this;
        }

        public Criteria andOperDiscountDescIsNotNull() {
            addCriterion("OPER_DISCOUNT_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andOperDiscountDescEqualTo(String value) {
            addCriterion("OPER_DISCOUNT_DESC =", value, "operDiscountDesc");
            return (Criteria) this;
        }

        public Criteria andOperDiscountDescNotEqualTo(String value) {
            addCriterion("OPER_DISCOUNT_DESC <>", value, "operDiscountDesc");
            return (Criteria) this;
        }

        public Criteria andOperDiscountDescGreaterThan(String value) {
            addCriterion("OPER_DISCOUNT_DESC >", value, "operDiscountDesc");
            return (Criteria) this;
        }

        public Criteria andOperDiscountDescGreaterThanOrEqualTo(String value) {
            addCriterion("OPER_DISCOUNT_DESC >=", value, "operDiscountDesc");
            return (Criteria) this;
        }

        public Criteria andOperDiscountDescLessThan(String value) {
            addCriterion("OPER_DISCOUNT_DESC <", value, "operDiscountDesc");
            return (Criteria) this;
        }

        public Criteria andOperDiscountDescLessThanOrEqualTo(String value) {
            addCriterion("OPER_DISCOUNT_DESC <=", value, "operDiscountDesc");
            return (Criteria) this;
        }

        public Criteria andOperDiscountDescLike(String value) {
            addCriterion("OPER_DISCOUNT_DESC like", value, "operDiscountDesc");
            return (Criteria) this;
        }

        public Criteria andOperDiscountDescNotLike(String value) {
            addCriterion("OPER_DISCOUNT_DESC not like", value, "operDiscountDesc");
            return (Criteria) this;
        }

        public Criteria andOperDiscountDescIn(List<String> values) {
            addCriterion("OPER_DISCOUNT_DESC in", values, "operDiscountDesc");
            return (Criteria) this;
        }

        public Criteria andOperDiscountDescNotIn(List<String> values) {
            addCriterion("OPER_DISCOUNT_DESC not in", values, "operDiscountDesc");
            return (Criteria) this;
        }

        public Criteria andOperDiscountDescBetween(String value1, String value2) {
            addCriterion("OPER_DISCOUNT_DESC between", value1, value2, "operDiscountDesc");
            return (Criteria) this;
        }

        public Criteria andOperDiscountDescNotBetween(String value1, String value2) {
            addCriterion("OPER_DISCOUNT_DESC not between", value1, value2, "operDiscountDesc");
            return (Criteria) this;
        }

        public Criteria andAdjustFeeIsNull() {
            addCriterion("ADJUST_FEE is null");
            return (Criteria) this;
        }

        public Criteria andAdjustFeeIsNotNull() {
            addCriterion("ADJUST_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andAdjustFeeEqualTo(long value) {
            addCriterion("ADJUST_FEE =", value, "adjustFee");
            return (Criteria) this;
        }

        public Criteria andAdjustFeeNotEqualTo(long value) {
            addCriterion("ADJUST_FEE <>", value, "adjustFee");
            return (Criteria) this;
        }

        public Criteria andAdjustFeeGreaterThan(long value) {
            addCriterion("ADJUST_FEE >", value, "adjustFee");
            return (Criteria) this;
        }

        public Criteria andAdjustFeeGreaterThanOrEqualTo(long value) {
            addCriterion("ADJUST_FEE >=", value, "adjustFee");
            return (Criteria) this;
        }

        public Criteria andAdjustFeeLessThan(long value) {
            addCriterion("ADJUST_FEE <", value, "adjustFee");
            return (Criteria) this;
        }

        public Criteria andAdjustFeeLessThanOrEqualTo(long value) {
            addCriterion("ADJUST_FEE <=", value, "adjustFee");
            return (Criteria) this;
        }

        public Criteria andAdjustFeeIn(List<Long> values) {
            addCriterion("ADJUST_FEE in", values, "adjustFee");
            return (Criteria) this;
        }

        public Criteria andAdjustFeeNotIn(List<Long> values) {
            addCriterion("ADJUST_FEE not in", values, "adjustFee");
            return (Criteria) this;
        }

        public Criteria andAdjustFeeBetween(long value1, long value2) {
            addCriterion("ADJUST_FEE between", value1, value2, "adjustFee");
            return (Criteria) this;
        }

        public Criteria andAdjustFeeNotBetween(long value1, long value2) {
            addCriterion("ADJUST_FEE not between", value1, value2, "adjustFee");
            return (Criteria) this;
        }

        public Criteria andPaidFeeIsNull() {
            addCriterion("PAID_FEE is null");
            return (Criteria) this;
        }

        public Criteria andPaidFeeIsNotNull() {
            addCriterion("PAID_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andPaidFeeEqualTo(long value) {
            addCriterion("PAID_FEE =", value, "paidFee");
            return (Criteria) this;
        }

        public Criteria andPaidFeeNotEqualTo(long value) {
            addCriterion("PAID_FEE <>", value, "paidFee");
            return (Criteria) this;
        }

        public Criteria andPaidFeeGreaterThan(long value) {
            addCriterion("PAID_FEE >", value, "paidFee");
            return (Criteria) this;
        }

        public Criteria andPaidFeeGreaterThanOrEqualTo(long value) {
            addCriterion("PAID_FEE >=", value, "paidFee");
            return (Criteria) this;
        }

        public Criteria andPaidFeeLessThan(long value) {
            addCriterion("PAID_FEE <", value, "paidFee");
            return (Criteria) this;
        }

        public Criteria andPaidFeeLessThanOrEqualTo(long value) {
            addCriterion("PAID_FEE <=", value, "paidFee");
            return (Criteria) this;
        }

        public Criteria andPaidFeeIn(List<Long> values) {
            addCriterion("PAID_FEE in", values, "paidFee");
            return (Criteria) this;
        }

        public Criteria andPaidFeeNotIn(List<Long> values) {
            addCriterion("PAID_FEE not in", values, "paidFee");
            return (Criteria) this;
        }

        public Criteria andPaidFeeBetween(long value1, long value2) {
            addCriterion("PAID_FEE between", value1, value2, "paidFee");
            return (Criteria) this;
        }

        public Criteria andPaidFeeNotBetween(long value1, long value2) {
            addCriterion("PAID_FEE not between", value1, value2, "paidFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeIsNull() {
            addCriterion("PAY_FEE is null");
            return (Criteria) this;
        }

        public Criteria andPayFeeIsNotNull() {
            addCriterion("PAY_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andPayFeeEqualTo(long value) {
            addCriterion("PAY_FEE =", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeNotEqualTo(long value) {
            addCriterion("PAY_FEE <>", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeGreaterThan(long value) {
            addCriterion("PAY_FEE >", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeGreaterThanOrEqualTo(long value) {
            addCriterion("PAY_FEE >=", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeLessThan(long value) {
            addCriterion("PAY_FEE <", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeLessThanOrEqualTo(long value) {
            addCriterion("PAY_FEE <=", value, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeIn(List<Long> values) {
            addCriterion("PAY_FEE in", values, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeNotIn(List<Long> values) {
            addCriterion("PAY_FEE not in", values, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeBetween(long value1, long value2) {
            addCriterion("PAY_FEE between", value1, value2, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayFeeNotBetween(long value1, long value2) {
            addCriterion("PAY_FEE not between", value1, value2, "payFee");
            return (Criteria) this;
        }

        public Criteria andPayStyleIsNull() {
            addCriterion("PAY_STYLE is null");
            return (Criteria) this;
        }

        public Criteria andPayStyleIsNotNull() {
            addCriterion("PAY_STYLE is not null");
            return (Criteria) this;
        }

        public Criteria andPayStyleEqualTo(String value) {
            addCriterion("PAY_STYLE =", value, "payStyle");
            return (Criteria) this;
        }

        public Criteria andPayStyleNotEqualTo(String value) {
            addCriterion("PAY_STYLE <>", value, "payStyle");
            return (Criteria) this;
        }

        public Criteria andPayStyleGreaterThan(String value) {
            addCriterion("PAY_STYLE >", value, "payStyle");
            return (Criteria) this;
        }

        public Criteria andPayStyleGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_STYLE >=", value, "payStyle");
            return (Criteria) this;
        }

        public Criteria andPayStyleLessThan(String value) {
            addCriterion("PAY_STYLE <", value, "payStyle");
            return (Criteria) this;
        }

        public Criteria andPayStyleLessThanOrEqualTo(String value) {
            addCriterion("PAY_STYLE <=", value, "payStyle");
            return (Criteria) this;
        }

        public Criteria andPayStyleLike(String value) {
            addCriterion("PAY_STYLE like", value, "payStyle");
            return (Criteria) this;
        }

        public Criteria andPayStyleNotLike(String value) {
            addCriterion("PAY_STYLE not like", value, "payStyle");
            return (Criteria) this;
        }

        public Criteria andPayStyleIn(List<String> values) {
            addCriterion("PAY_STYLE in", values, "payStyle");
            return (Criteria) this;
        }

        public Criteria andPayStyleNotIn(List<String> values) {
            addCriterion("PAY_STYLE not in", values, "payStyle");
            return (Criteria) this;
        }

        public Criteria andPayStyleBetween(String value1, String value2) {
            addCriterion("PAY_STYLE between", value1, value2, "payStyle");
            return (Criteria) this;
        }

        public Criteria andPayStyleNotBetween(String value1, String value2) {
            addCriterion("PAY_STYLE not between", value1, value2, "payStyle");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Timestamp value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Timestamp value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Timestamp value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Timestamp value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Timestamp value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Timestamp value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Timestamp> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Timestamp> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Timestamp value1, Timestamp value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Timestamp value1, Timestamp value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateChlIdIsNull() {
            addCriterion("UPDATE_CHL_ID is null");
            return (Criteria) this;
        }

        public Criteria andUpdateChlIdIsNotNull() {
            addCriterion("UPDATE_CHL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateChlIdEqualTo(String value) {
            addCriterion("UPDATE_CHL_ID =", value, "updateChlId");
            return (Criteria) this;
        }

        public Criteria andUpdateChlIdNotEqualTo(String value) {
            addCriterion("UPDATE_CHL_ID <>", value, "updateChlId");
            return (Criteria) this;
        }

        public Criteria andUpdateChlIdGreaterThan(String value) {
            addCriterion("UPDATE_CHL_ID >", value, "updateChlId");
            return (Criteria) this;
        }

        public Criteria andUpdateChlIdGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_CHL_ID >=", value, "updateChlId");
            return (Criteria) this;
        }

        public Criteria andUpdateChlIdLessThan(String value) {
            addCriterion("UPDATE_CHL_ID <", value, "updateChlId");
            return (Criteria) this;
        }

        public Criteria andUpdateChlIdLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_CHL_ID <=", value, "updateChlId");
            return (Criteria) this;
        }

        public Criteria andUpdateChlIdLike(String value) {
            addCriterion("UPDATE_CHL_ID like", value, "updateChlId");
            return (Criteria) this;
        }

        public Criteria andUpdateChlIdNotLike(String value) {
            addCriterion("UPDATE_CHL_ID not like", value, "updateChlId");
            return (Criteria) this;
        }

        public Criteria andUpdateChlIdIn(List<String> values) {
            addCriterion("UPDATE_CHL_ID in", values, "updateChlId");
            return (Criteria) this;
        }

        public Criteria andUpdateChlIdNotIn(List<String> values) {
            addCriterion("UPDATE_CHL_ID not in", values, "updateChlId");
            return (Criteria) this;
        }

        public Criteria andUpdateChlIdBetween(String value1, String value2) {
            addCriterion("UPDATE_CHL_ID between", value1, value2, "updateChlId");
            return (Criteria) this;
        }

        public Criteria andUpdateChlIdNotBetween(String value1, String value2) {
            addCriterion("UPDATE_CHL_ID not between", value1, value2, "updateChlId");
            return (Criteria) this;
        }

        public Criteria andUpdateOperIdIsNull() {
            addCriterion("UPDATE_OPER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUpdateOperIdIsNotNull() {
            addCriterion("UPDATE_OPER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateOperIdEqualTo(String value) {
            addCriterion("UPDATE_OPER_ID =", value, "updateOperId");
            return (Criteria) this;
        }

        public Criteria andUpdateOperIdNotEqualTo(String value) {
            addCriterion("UPDATE_OPER_ID <>", value, "updateOperId");
            return (Criteria) this;
        }

        public Criteria andUpdateOperIdGreaterThan(String value) {
            addCriterion("UPDATE_OPER_ID >", value, "updateOperId");
            return (Criteria) this;
        }

        public Criteria andUpdateOperIdGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATE_OPER_ID >=", value, "updateOperId");
            return (Criteria) this;
        }

        public Criteria andUpdateOperIdLessThan(String value) {
            addCriterion("UPDATE_OPER_ID <", value, "updateOperId");
            return (Criteria) this;
        }

        public Criteria andUpdateOperIdLessThanOrEqualTo(String value) {
            addCriterion("UPDATE_OPER_ID <=", value, "updateOperId");
            return (Criteria) this;
        }

        public Criteria andUpdateOperIdLike(String value) {
            addCriterion("UPDATE_OPER_ID like", value, "updateOperId");
            return (Criteria) this;
        }

        public Criteria andUpdateOperIdNotLike(String value) {
            addCriterion("UPDATE_OPER_ID not like", value, "updateOperId");
            return (Criteria) this;
        }

        public Criteria andUpdateOperIdIn(List<String> values) {
            addCriterion("UPDATE_OPER_ID in", values, "updateOperId");
            return (Criteria) this;
        }

        public Criteria andUpdateOperIdNotIn(List<String> values) {
            addCriterion("UPDATE_OPER_ID not in", values, "updateOperId");
            return (Criteria) this;
        }

        public Criteria andUpdateOperIdBetween(String value1, String value2) {
            addCriterion("UPDATE_OPER_ID between", value1, value2, "updateOperId");
            return (Criteria) this;
        }

        public Criteria andUpdateOperIdNotBetween(String value1, String value2) {
            addCriterion("UPDATE_OPER_ID not between", value1, value2, "updateOperId");
            return (Criteria) this;
        }

        public Criteria andTotalJfIsNull() {
            addCriterion("TOTAL_JF is null");
            return (Criteria) this;
        }

        public Criteria andTotalJfIsNotNull() {
            addCriterion("TOTAL_JF is not null");
            return (Criteria) this;
        }

        public Criteria andTotalJfEqualTo(long value) {
            addCriterion("TOTAL_JF =", value, "totalJf");
            return (Criteria) this;
        }

        public Criteria andTotalJfNotEqualTo(long value) {
            addCriterion("TOTAL_JF <>", value, "totalJf");
            return (Criteria) this;
        }

        public Criteria andTotalJfGreaterThan(long value) {
            addCriterion("TOTAL_JF >", value, "totalJf");
            return (Criteria) this;
        }

        public Criteria andTotalJfGreaterThanOrEqualTo(long value) {
            addCriterion("TOTAL_JF >=", value, "totalJf");
            return (Criteria) this;
        }

        public Criteria andTotalJfLessThan(long value) {
            addCriterion("TOTAL_JF <", value, "totalJf");
            return (Criteria) this;
        }

        public Criteria andTotalJfLessThanOrEqualTo(long value) {
            addCriterion("TOTAL_JF <=", value, "totalJf");
            return (Criteria) this;
        }

        public Criteria andTotalJfIn(List<Long> values) {
            addCriterion("TOTAL_JF in", values, "totalJf");
            return (Criteria) this;
        }

        public Criteria andTotalJfNotIn(List<Long> values) {
            addCriterion("TOTAL_JF not in", values, "totalJf");
            return (Criteria) this;
        }

        public Criteria andTotalJfBetween(long value1, long value2) {
            addCriterion("TOTAL_JF between", value1, value2, "totalJf");
            return (Criteria) this;
        }

        public Criteria andTotalJfNotBetween(long value1, long value2) {
            addCriterion("TOTAL_JF not between", value1, value2, "totalJf");
            return (Criteria) this;
        }

        public Criteria andFreightIsNull() {
            addCriterion("FREIGHT is null");
            return (Criteria) this;
        }

        public Criteria andFreightIsNotNull() {
            addCriterion("FREIGHT is not null");
            return (Criteria) this;
        }

        public Criteria andFreightEqualTo(long value) {
            addCriterion("FREIGHT =", value, "freight");
            return (Criteria) this;
        }

        public Criteria andFreightNotEqualTo(long value) {
            addCriterion("FREIGHT <>", value, "freight");
            return (Criteria) this;
        }

        public Criteria andFreightGreaterThan(long value) {
            addCriterion("FREIGHT >", value, "freight");
            return (Criteria) this;
        }

        public Criteria andFreightGreaterThanOrEqualTo(long value) {
            addCriterion("FREIGHT >=", value, "freight");
            return (Criteria) this;
        }

        public Criteria andFreightLessThan(long value) {
            addCriterion("FREIGHT <", value, "freight");
            return (Criteria) this;
        }

        public Criteria andFreightLessThanOrEqualTo(long value) {
            addCriterion("FREIGHT <=", value, "freight");
            return (Criteria) this;
        }

        public Criteria andFreightIn(List<Long> values) {
            addCriterion("FREIGHT in", values, "freight");
            return (Criteria) this;
        }

        public Criteria andFreightNotIn(List<Long> values) {
            addCriterion("FREIGHT not in", values, "freight");
            return (Criteria) this;
        }

        public Criteria andFreightBetween(long value1, long value2) {
            addCriterion("FREIGHT between", value1, value2, "freight");
            return (Criteria) this;
        }

        public Criteria andFreightNotBetween(long value1, long value2) {
            addCriterion("FREIGHT not between", value1, value2, "freight");
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