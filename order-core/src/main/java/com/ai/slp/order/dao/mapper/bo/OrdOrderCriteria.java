package com.ai.slp.order.dao.mapper.bo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrdOrderCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OrdOrderCriteria() {
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

        public Criteria andBusiCodeIsNull() {
            addCriterion("BUSI_CODE is null");
            return (Criteria) this;
        }

        public Criteria andBusiCodeIsNotNull() {
            addCriterion("BUSI_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andBusiCodeEqualTo(String value) {
            addCriterion("BUSI_CODE =", value, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeNotEqualTo(String value) {
            addCriterion("BUSI_CODE <>", value, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeGreaterThan(String value) {
            addCriterion("BUSI_CODE >", value, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BUSI_CODE >=", value, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeLessThan(String value) {
            addCriterion("BUSI_CODE <", value, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeLessThanOrEqualTo(String value) {
            addCriterion("BUSI_CODE <=", value, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeLike(String value) {
            addCriterion("BUSI_CODE like", value, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeNotLike(String value) {
            addCriterion("BUSI_CODE not like", value, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeIn(List<String> values) {
            addCriterion("BUSI_CODE in", values, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeNotIn(List<String> values) {
            addCriterion("BUSI_CODE not in", values, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeBetween(String value1, String value2) {
            addCriterion("BUSI_CODE between", value1, value2, "busiCode");
            return (Criteria) this;
        }

        public Criteria andBusiCodeNotBetween(String value1, String value2) {
            addCriterion("BUSI_CODE not between", value1, value2, "busiCode");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNull() {
            addCriterion("ORDER_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("ORDER_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(String value) {
            addCriterion("ORDER_TYPE =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(String value) {
            addCriterion("ORDER_TYPE <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(String value) {
            addCriterion("ORDER_TYPE >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_TYPE >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(String value) {
            addCriterion("ORDER_TYPE <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(String value) {
            addCriterion("ORDER_TYPE <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLike(String value) {
            addCriterion("ORDER_TYPE like", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotLike(String value) {
            addCriterion("ORDER_TYPE not like", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<String> values) {
            addCriterion("ORDER_TYPE in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<String> values) {
            addCriterion("ORDER_TYPE not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(String value1, String value2) {
            addCriterion("ORDER_TYPE between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(String value1, String value2) {
            addCriterion("ORDER_TYPE not between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andSubFlagIsNull() {
            addCriterion("SUB_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andSubFlagIsNotNull() {
            addCriterion("SUB_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andSubFlagEqualTo(String value) {
            addCriterion("SUB_FLAG =", value, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagNotEqualTo(String value) {
            addCriterion("SUB_FLAG <>", value, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagGreaterThan(String value) {
            addCriterion("SUB_FLAG >", value, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagGreaterThanOrEqualTo(String value) {
            addCriterion("SUB_FLAG >=", value, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagLessThan(String value) {
            addCriterion("SUB_FLAG <", value, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagLessThanOrEqualTo(String value) {
            addCriterion("SUB_FLAG <=", value, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagLike(String value) {
            addCriterion("SUB_FLAG like", value, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagNotLike(String value) {
            addCriterion("SUB_FLAG not like", value, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagIn(List<String> values) {
            addCriterion("SUB_FLAG in", values, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagNotIn(List<String> values) {
            addCriterion("SUB_FLAG not in", values, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagBetween(String value1, String value2) {
            addCriterion("SUB_FLAG between", value1, value2, "subFlag");
            return (Criteria) this;
        }

        public Criteria andSubFlagNotBetween(String value1, String value2) {
            addCriterion("SUB_FLAG not between", value1, value2, "subFlag");
            return (Criteria) this;
        }

        public Criteria andParentOrderIdIsNull() {
            addCriterion("PARENT_ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andParentOrderIdIsNotNull() {
            addCriterion("PARENT_ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andParentOrderIdEqualTo(long value) {
            addCriterion("PARENT_ORDER_ID =", value, "parentOrderId");
            return (Criteria) this;
        }

        public Criteria andParentOrderIdNotEqualTo(long value) {
            addCriterion("PARENT_ORDER_ID <>", value, "parentOrderId");
            return (Criteria) this;
        }

        public Criteria andParentOrderIdGreaterThan(long value) {
            addCriterion("PARENT_ORDER_ID >", value, "parentOrderId");
            return (Criteria) this;
        }

        public Criteria andParentOrderIdGreaterThanOrEqualTo(long value) {
            addCriterion("PARENT_ORDER_ID >=", value, "parentOrderId");
            return (Criteria) this;
        }

        public Criteria andParentOrderIdLessThan(long value) {
            addCriterion("PARENT_ORDER_ID <", value, "parentOrderId");
            return (Criteria) this;
        }

        public Criteria andParentOrderIdLessThanOrEqualTo(long value) {
            addCriterion("PARENT_ORDER_ID <=", value, "parentOrderId");
            return (Criteria) this;
        }

        public Criteria andParentOrderIdIn(List<Long> values) {
            addCriterion("PARENT_ORDER_ID in", values, "parentOrderId");
            return (Criteria) this;
        }

        public Criteria andParentOrderIdNotIn(List<Long> values) {
            addCriterion("PARENT_ORDER_ID not in", values, "parentOrderId");
            return (Criteria) this;
        }

        public Criteria andParentOrderIdBetween(long value1, long value2) {
            addCriterion("PARENT_ORDER_ID between", value1, value2, "parentOrderId");
            return (Criteria) this;
        }

        public Criteria andParentOrderIdNotBetween(long value1, long value2) {
            addCriterion("PARENT_ORDER_ID not between", value1, value2, "parentOrderId");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNull() {
            addCriterion("BATCH_NO is null");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNotNull() {
            addCriterion("BATCH_NO is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNoEqualTo(long value) {
            addCriterion("BATCH_NO =", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotEqualTo(long value) {
            addCriterion("BATCH_NO <>", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThan(long value) {
            addCriterion("BATCH_NO >", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThanOrEqualTo(long value) {
            addCriterion("BATCH_NO >=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThan(long value) {
            addCriterion("BATCH_NO <", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThanOrEqualTo(long value) {
            addCriterion("BATCH_NO <=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoIn(List<Long> values) {
            addCriterion("BATCH_NO in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotIn(List<Long> values) {
            addCriterion("BATCH_NO not in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoBetween(long value1, long value2) {
            addCriterion("BATCH_NO between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotBetween(long value1, long value2) {
            addCriterion("BATCH_NO not between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("USER_ID is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("USER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("USER_ID =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("USER_ID <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("USER_ID >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("USER_ID >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("USER_ID <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("USER_ID <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("USER_ID like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("USER_ID not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("USER_ID in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("USER_ID not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("USER_ID between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("USER_ID not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andAcctIdIsNull() {
            addCriterion("ACCT_ID is null");
            return (Criteria) this;
        }

        public Criteria andAcctIdIsNotNull() {
            addCriterion("ACCT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAcctIdEqualTo(long value) {
            addCriterion("ACCT_ID =", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdNotEqualTo(long value) {
            addCriterion("ACCT_ID <>", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdGreaterThan(long value) {
            addCriterion("ACCT_ID >", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdGreaterThanOrEqualTo(long value) {
            addCriterion("ACCT_ID >=", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdLessThan(long value) {
            addCriterion("ACCT_ID <", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdLessThanOrEqualTo(long value) {
            addCriterion("ACCT_ID <=", value, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdIn(List<Long> values) {
            addCriterion("ACCT_ID in", values, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdNotIn(List<Long> values) {
            addCriterion("ACCT_ID not in", values, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdBetween(long value1, long value2) {
            addCriterion("ACCT_ID between", value1, value2, "acctId");
            return (Criteria) this;
        }

        public Criteria andAcctIdNotBetween(long value1, long value2) {
            addCriterion("ACCT_ID not between", value1, value2, "acctId");
            return (Criteria) this;
        }

        public Criteria andSubsIdIsNull() {
            addCriterion("SUBS_ID is null");
            return (Criteria) this;
        }

        public Criteria andSubsIdIsNotNull() {
            addCriterion("SUBS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSubsIdEqualTo(long value) {
            addCriterion("SUBS_ID =", value, "subsId");
            return (Criteria) this;
        }

        public Criteria andSubsIdNotEqualTo(long value) {
            addCriterion("SUBS_ID <>", value, "subsId");
            return (Criteria) this;
        }

        public Criteria andSubsIdGreaterThan(long value) {
            addCriterion("SUBS_ID >", value, "subsId");
            return (Criteria) this;
        }

        public Criteria andSubsIdGreaterThanOrEqualTo(long value) {
            addCriterion("SUBS_ID >=", value, "subsId");
            return (Criteria) this;
        }

        public Criteria andSubsIdLessThan(long value) {
            addCriterion("SUBS_ID <", value, "subsId");
            return (Criteria) this;
        }

        public Criteria andSubsIdLessThanOrEqualTo(long value) {
            addCriterion("SUBS_ID <=", value, "subsId");
            return (Criteria) this;
        }

        public Criteria andSubsIdIn(List<Long> values) {
            addCriterion("SUBS_ID in", values, "subsId");
            return (Criteria) this;
        }

        public Criteria andSubsIdNotIn(List<Long> values) {
            addCriterion("SUBS_ID not in", values, "subsId");
            return (Criteria) this;
        }

        public Criteria andSubsIdBetween(long value1, long value2) {
            addCriterion("SUBS_ID between", value1, value2, "subsId");
            return (Criteria) this;
        }

        public Criteria andSubsIdNotBetween(long value1, long value2) {
            addCriterion("SUBS_ID not between", value1, value2, "subsId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNull() {
            addCriterion("SUPPLIER_ID is null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNotNull() {
            addCriterion("SUPPLIER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdEqualTo(String value) {
            addCriterion("SUPPLIER_ID =", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotEqualTo(String value) {
            addCriterion("SUPPLIER_ID <>", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThan(String value) {
            addCriterion("SUPPLIER_ID >", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThanOrEqualTo(String value) {
            addCriterion("SUPPLIER_ID >=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThan(String value) {
            addCriterion("SUPPLIER_ID <", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThanOrEqualTo(String value) {
            addCriterion("SUPPLIER_ID <=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLike(String value) {
            addCriterion("SUPPLIER_ID like", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotLike(String value) {
            addCriterion("SUPPLIER_ID not like", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIn(List<String> values) {
            addCriterion("SUPPLIER_ID in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotIn(List<String> values) {
            addCriterion("SUPPLIER_ID not in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdBetween(String value1, String value2) {
            addCriterion("SUPPLIER_ID between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotBetween(String value1, String value2) {
            addCriterion("SUPPLIER_ID not between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andStorageIdIsNull() {
            addCriterion("STORAGE_ID is null");
            return (Criteria) this;
        }

        public Criteria andStorageIdIsNotNull() {
            addCriterion("STORAGE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andStorageIdEqualTo(String value) {
            addCriterion("STORAGE_ID =", value, "storageId");
            return (Criteria) this;
        }

        public Criteria andStorageIdNotEqualTo(String value) {
            addCriterion("STORAGE_ID <>", value, "storageId");
            return (Criteria) this;
        }

        public Criteria andStorageIdGreaterThan(String value) {
            addCriterion("STORAGE_ID >", value, "storageId");
            return (Criteria) this;
        }

        public Criteria andStorageIdGreaterThanOrEqualTo(String value) {
            addCriterion("STORAGE_ID >=", value, "storageId");
            return (Criteria) this;
        }

        public Criteria andStorageIdLessThan(String value) {
            addCriterion("STORAGE_ID <", value, "storageId");
            return (Criteria) this;
        }

        public Criteria andStorageIdLessThanOrEqualTo(String value) {
            addCriterion("STORAGE_ID <=", value, "storageId");
            return (Criteria) this;
        }

        public Criteria andStorageIdLike(String value) {
            addCriterion("STORAGE_ID like", value, "storageId");
            return (Criteria) this;
        }

        public Criteria andStorageIdNotLike(String value) {
            addCriterion("STORAGE_ID not like", value, "storageId");
            return (Criteria) this;
        }

        public Criteria andStorageIdIn(List<String> values) {
            addCriterion("STORAGE_ID in", values, "storageId");
            return (Criteria) this;
        }

        public Criteria andStorageIdNotIn(List<String> values) {
            addCriterion("STORAGE_ID not in", values, "storageId");
            return (Criteria) this;
        }

        public Criteria andStorageIdBetween(String value1, String value2) {
            addCriterion("STORAGE_ID between", value1, value2, "storageId");
            return (Criteria) this;
        }

        public Criteria andStorageIdNotBetween(String value1, String value2) {
            addCriterion("STORAGE_ID not between", value1, value2, "storageId");
            return (Criteria) this;
        }

        public Criteria andRouteIdIsNull() {
            addCriterion("ROUTE_ID is null");
            return (Criteria) this;
        }

        public Criteria andRouteIdIsNotNull() {
            addCriterion("ROUTE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRouteIdEqualTo(String value) {
            addCriterion("ROUTE_ID =", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdNotEqualTo(String value) {
            addCriterion("ROUTE_ID <>", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdGreaterThan(String value) {
            addCriterion("ROUTE_ID >", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdGreaterThanOrEqualTo(String value) {
            addCriterion("ROUTE_ID >=", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdLessThan(String value) {
            addCriterion("ROUTE_ID <", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdLessThanOrEqualTo(String value) {
            addCriterion("ROUTE_ID <=", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdLike(String value) {
            addCriterion("ROUTE_ID like", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdNotLike(String value) {
            addCriterion("ROUTE_ID not like", value, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdIn(List<String> values) {
            addCriterion("ROUTE_ID in", values, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdNotIn(List<String> values) {
            addCriterion("ROUTE_ID not in", values, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdBetween(String value1, String value2) {
            addCriterion("ROUTE_ID between", value1, value2, "routeId");
            return (Criteria) this;
        }

        public Criteria andRouteIdNotBetween(String value1, String value2) {
            addCriterion("ROUTE_ID not between", value1, value2, "routeId");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeIsNull() {
            addCriterion("PROVINCE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeIsNotNull() {
            addCriterion("PROVINCE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeEqualTo(String value) {
            addCriterion("PROVINCE_CODE =", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeNotEqualTo(String value) {
            addCriterion("PROVINCE_CODE <>", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeGreaterThan(String value) {
            addCriterion("PROVINCE_CODE >", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeGreaterThanOrEqualTo(String value) {
            addCriterion("PROVINCE_CODE >=", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeLessThan(String value) {
            addCriterion("PROVINCE_CODE <", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeLessThanOrEqualTo(String value) {
            addCriterion("PROVINCE_CODE <=", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeLike(String value) {
            addCriterion("PROVINCE_CODE like", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeNotLike(String value) {
            addCriterion("PROVINCE_CODE not like", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeIn(List<String> values) {
            addCriterion("PROVINCE_CODE in", values, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeNotIn(List<String> values) {
            addCriterion("PROVINCE_CODE not in", values, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeBetween(String value1, String value2) {
            addCriterion("PROVINCE_CODE between", value1, value2, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeNotBetween(String value1, String value2) {
            addCriterion("PROVINCE_CODE not between", value1, value2, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeIsNull() {
            addCriterion("CITY_CODE is null");
            return (Criteria) this;
        }

        public Criteria andCityCodeIsNotNull() {
            addCriterion("CITY_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andCityCodeEqualTo(String value) {
            addCriterion("CITY_CODE =", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotEqualTo(String value) {
            addCriterion("CITY_CODE <>", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeGreaterThan(String value) {
            addCriterion("CITY_CODE >", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeGreaterThanOrEqualTo(String value) {
            addCriterion("CITY_CODE >=", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLessThan(String value) {
            addCriterion("CITY_CODE <", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLessThanOrEqualTo(String value) {
            addCriterion("CITY_CODE <=", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLike(String value) {
            addCriterion("CITY_CODE like", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotLike(String value) {
            addCriterion("CITY_CODE not like", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeIn(List<String> values) {
            addCriterion("CITY_CODE in", values, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotIn(List<String> values) {
            addCriterion("CITY_CODE not in", values, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeBetween(String value1, String value2) {
            addCriterion("CITY_CODE between", value1, value2, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotBetween(String value1, String value2) {
            addCriterion("CITY_CODE not between", value1, value2, "cityCode");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("STATE is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("STATE is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("STATE =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("STATE <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("STATE >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("STATE >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("STATE <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("STATE <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("STATE like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("STATE not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("STATE in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("STATE not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("STATE between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("STATE not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateChgTimeIsNull() {
            addCriterion("STATE_CHG_TIME is null");
            return (Criteria) this;
        }

        public Criteria andStateChgTimeIsNotNull() {
            addCriterion("STATE_CHG_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andStateChgTimeEqualTo(Timestamp value) {
            addCriterion("STATE_CHG_TIME =", value, "stateChgTime");
            return (Criteria) this;
        }

        public Criteria andStateChgTimeNotEqualTo(Timestamp value) {
            addCriterion("STATE_CHG_TIME <>", value, "stateChgTime");
            return (Criteria) this;
        }

        public Criteria andStateChgTimeGreaterThan(Timestamp value) {
            addCriterion("STATE_CHG_TIME >", value, "stateChgTime");
            return (Criteria) this;
        }

        public Criteria andStateChgTimeGreaterThanOrEqualTo(Timestamp value) {
            addCriterion("STATE_CHG_TIME >=", value, "stateChgTime");
            return (Criteria) this;
        }

        public Criteria andStateChgTimeLessThan(Timestamp value) {
            addCriterion("STATE_CHG_TIME <", value, "stateChgTime");
            return (Criteria) this;
        }

        public Criteria andStateChgTimeLessThanOrEqualTo(Timestamp value) {
            addCriterion("STATE_CHG_TIME <=", value, "stateChgTime");
            return (Criteria) this;
        }

        public Criteria andStateChgTimeIn(List<Timestamp> values) {
            addCriterion("STATE_CHG_TIME in", values, "stateChgTime");
            return (Criteria) this;
        }

        public Criteria andStateChgTimeNotIn(List<Timestamp> values) {
            addCriterion("STATE_CHG_TIME not in", values, "stateChgTime");
            return (Criteria) this;
        }

        public Criteria andStateChgTimeBetween(Timestamp value1, Timestamp value2) {
            addCriterion("STATE_CHG_TIME between", value1, value2, "stateChgTime");
            return (Criteria) this;
        }

        public Criteria andStateChgTimeNotBetween(Timestamp value1, Timestamp value2) {
            addCriterion("STATE_CHG_TIME not between", value1, value2, "stateChgTime");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagIsNull() {
            addCriterion("DISPLAY_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagIsNotNull() {
            addCriterion("DISPLAY_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagEqualTo(String value) {
            addCriterion("DISPLAY_FLAG =", value, "displayFlag");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagNotEqualTo(String value) {
            addCriterion("DISPLAY_FLAG <>", value, "displayFlag");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagGreaterThan(String value) {
            addCriterion("DISPLAY_FLAG >", value, "displayFlag");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagGreaterThanOrEqualTo(String value) {
            addCriterion("DISPLAY_FLAG >=", value, "displayFlag");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagLessThan(String value) {
            addCriterion("DISPLAY_FLAG <", value, "displayFlag");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagLessThanOrEqualTo(String value) {
            addCriterion("DISPLAY_FLAG <=", value, "displayFlag");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagLike(String value) {
            addCriterion("DISPLAY_FLAG like", value, "displayFlag");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagNotLike(String value) {
            addCriterion("DISPLAY_FLAG not like", value, "displayFlag");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagIn(List<String> values) {
            addCriterion("DISPLAY_FLAG in", values, "displayFlag");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagNotIn(List<String> values) {
            addCriterion("DISPLAY_FLAG not in", values, "displayFlag");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagBetween(String value1, String value2) {
            addCriterion("DISPLAY_FLAG between", value1, value2, "displayFlag");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagNotBetween(String value1, String value2) {
            addCriterion("DISPLAY_FLAG not between", value1, value2, "displayFlag");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagChgTimeIsNull() {
            addCriterion("DISPLAY_FLAG_CHG_TIME is null");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagChgTimeIsNotNull() {
            addCriterion("DISPLAY_FLAG_CHG_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagChgTimeEqualTo(Timestamp value) {
            addCriterion("DISPLAY_FLAG_CHG_TIME =", value, "displayFlagChgTime");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagChgTimeNotEqualTo(Timestamp value) {
            addCriterion("DISPLAY_FLAG_CHG_TIME <>", value, "displayFlagChgTime");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagChgTimeGreaterThan(Timestamp value) {
            addCriterion("DISPLAY_FLAG_CHG_TIME >", value, "displayFlagChgTime");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagChgTimeGreaterThanOrEqualTo(Timestamp value) {
            addCriterion("DISPLAY_FLAG_CHG_TIME >=", value, "displayFlagChgTime");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagChgTimeLessThan(Timestamp value) {
            addCriterion("DISPLAY_FLAG_CHG_TIME <", value, "displayFlagChgTime");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagChgTimeLessThanOrEqualTo(Timestamp value) {
            addCriterion("DISPLAY_FLAG_CHG_TIME <=", value, "displayFlagChgTime");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagChgTimeIn(List<Timestamp> values) {
            addCriterion("DISPLAY_FLAG_CHG_TIME in", values, "displayFlagChgTime");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagChgTimeNotIn(List<Timestamp> values) {
            addCriterion("DISPLAY_FLAG_CHG_TIME not in", values, "displayFlagChgTime");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagChgTimeBetween(Timestamp value1, Timestamp value2) {
            addCriterion("DISPLAY_FLAG_CHG_TIME between", value1, value2, "displayFlagChgTime");
            return (Criteria) this;
        }

        public Criteria andDisplayFlagChgTimeNotBetween(Timestamp value1, Timestamp value2) {
            addCriterion("DISPLAY_FLAG_CHG_TIME not between", value1, value2, "displayFlagChgTime");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagIsNull() {
            addCriterion("DELIVERY_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagIsNotNull() {
            addCriterion("DELIVERY_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagEqualTo(String value) {
            addCriterion("DELIVERY_FLAG =", value, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagNotEqualTo(String value) {
            addCriterion("DELIVERY_FLAG <>", value, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagGreaterThan(String value) {
            addCriterion("DELIVERY_FLAG >", value, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagGreaterThanOrEqualTo(String value) {
            addCriterion("DELIVERY_FLAG >=", value, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagLessThan(String value) {
            addCriterion("DELIVERY_FLAG <", value, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagLessThanOrEqualTo(String value) {
            addCriterion("DELIVERY_FLAG <=", value, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagLike(String value) {
            addCriterion("DELIVERY_FLAG like", value, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagNotLike(String value) {
            addCriterion("DELIVERY_FLAG not like", value, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagIn(List<String> values) {
            addCriterion("DELIVERY_FLAG in", values, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagNotIn(List<String> values) {
            addCriterion("DELIVERY_FLAG not in", values, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagBetween(String value1, String value2) {
            addCriterion("DELIVERY_FLAG between", value1, value2, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andDeliveryFlagNotBetween(String value1, String value2) {
            addCriterion("DELIVERY_FLAG not between", value1, value2, "deliveryFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagIsNull() {
            addCriterion("LOCK_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andLockFlagIsNotNull() {
            addCriterion("LOCK_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andLockFlagEqualTo(String value) {
            addCriterion("LOCK_FLAG =", value, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagNotEqualTo(String value) {
            addCriterion("LOCK_FLAG <>", value, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagGreaterThan(String value) {
            addCriterion("LOCK_FLAG >", value, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagGreaterThanOrEqualTo(String value) {
            addCriterion("LOCK_FLAG >=", value, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagLessThan(String value) {
            addCriterion("LOCK_FLAG <", value, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagLessThanOrEqualTo(String value) {
            addCriterion("LOCK_FLAG <=", value, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagLike(String value) {
            addCriterion("LOCK_FLAG like", value, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagNotLike(String value) {
            addCriterion("LOCK_FLAG not like", value, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagIn(List<String> values) {
            addCriterion("LOCK_FLAG in", values, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagNotIn(List<String> values) {
            addCriterion("LOCK_FLAG not in", values, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagBetween(String value1, String value2) {
            addCriterion("LOCK_FLAG between", value1, value2, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockFlagNotBetween(String value1, String value2) {
            addCriterion("LOCK_FLAG not between", value1, value2, "lockFlag");
            return (Criteria) this;
        }

        public Criteria andLockTimeIsNull() {
            addCriterion("LOCK_TIME is null");
            return (Criteria) this;
        }

        public Criteria andLockTimeIsNotNull() {
            addCriterion("LOCK_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andLockTimeEqualTo(Timestamp value) {
            addCriterion("LOCK_TIME =", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeNotEqualTo(Timestamp value) {
            addCriterion("LOCK_TIME <>", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeGreaterThan(Timestamp value) {
            addCriterion("LOCK_TIME >", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeGreaterThanOrEqualTo(Timestamp value) {
            addCriterion("LOCK_TIME >=", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeLessThan(Timestamp value) {
            addCriterion("LOCK_TIME <", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeLessThanOrEqualTo(Timestamp value) {
            addCriterion("LOCK_TIME <=", value, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeIn(List<Timestamp> values) {
            addCriterion("LOCK_TIME in", values, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeNotIn(List<Timestamp> values) {
            addCriterion("LOCK_TIME not in", values, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeBetween(Timestamp value1, Timestamp value2) {
            addCriterion("LOCK_TIME between", value1, value2, "lockTime");
            return (Criteria) this;
        }

        public Criteria andLockTimeNotBetween(Timestamp value1, Timestamp value2) {
            addCriterion("LOCK_TIME not between", value1, value2, "lockTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeIsNull() {
            addCriterion("ORDER_TIME is null");
            return (Criteria) this;
        }

        public Criteria andOrderTimeIsNotNull() {
            addCriterion("ORDER_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTimeEqualTo(Timestamp value) {
            addCriterion("ORDER_TIME =", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeNotEqualTo(Timestamp value) {
            addCriterion("ORDER_TIME <>", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeGreaterThan(Timestamp value) {
            addCriterion("ORDER_TIME >", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeGreaterThanOrEqualTo(Timestamp value) {
            addCriterion("ORDER_TIME >=", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeLessThan(Timestamp value) {
            addCriterion("ORDER_TIME <", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeLessThanOrEqualTo(Timestamp value) {
            addCriterion("ORDER_TIME <=", value, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeIn(List<Timestamp> values) {
            addCriterion("ORDER_TIME in", values, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeNotIn(List<Timestamp> values) {
            addCriterion("ORDER_TIME not in", values, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeBetween(Timestamp value1, Timestamp value2) {
            addCriterion("ORDER_TIME between", value1, value2, "orderTime");
            return (Criteria) this;
        }

        public Criteria andOrderTimeNotBetween(Timestamp value1, Timestamp value2) {
            addCriterion("ORDER_TIME not between", value1, value2, "orderTime");
            return (Criteria) this;
        }

        public Criteria andSellerIdIsNull() {
            addCriterion("SELLER_ID is null");
            return (Criteria) this;
        }

        public Criteria andSellerIdIsNotNull() {
            addCriterion("SELLER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSellerIdEqualTo(long value) {
            addCriterion("SELLER_ID =", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdNotEqualTo(long value) {
            addCriterion("SELLER_ID <>", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdGreaterThan(long value) {
            addCriterion("SELLER_ID >", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdGreaterThanOrEqualTo(long value) {
            addCriterion("SELLER_ID >=", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdLessThan(long value) {
            addCriterion("SELLER_ID <", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdLessThanOrEqualTo(long value) {
            addCriterion("SELLER_ID <=", value, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdIn(List<Long> values) {
            addCriterion("SELLER_ID in", values, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdNotIn(List<Long> values) {
            addCriterion("SELLER_ID not in", values, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdBetween(long value1, long value2) {
            addCriterion("SELLER_ID between", value1, value2, "sellerId");
            return (Criteria) this;
        }

        public Criteria andSellerIdNotBetween(long value1, long value2) {
            addCriterion("SELLER_ID not between", value1, value2, "sellerId");
            return (Criteria) this;
        }

        public Criteria andChlIdIsNull() {
            addCriterion("CHL_ID is null");
            return (Criteria) this;
        }

        public Criteria andChlIdIsNotNull() {
            addCriterion("CHL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andChlIdEqualTo(String value) {
            addCriterion("CHL_ID =", value, "chlId");
            return (Criteria) this;
        }

        public Criteria andChlIdNotEqualTo(String value) {
            addCriterion("CHL_ID <>", value, "chlId");
            return (Criteria) this;
        }

        public Criteria andChlIdGreaterThan(String value) {
            addCriterion("CHL_ID >", value, "chlId");
            return (Criteria) this;
        }

        public Criteria andChlIdGreaterThanOrEqualTo(String value) {
            addCriterion("CHL_ID >=", value, "chlId");
            return (Criteria) this;
        }

        public Criteria andChlIdLessThan(String value) {
            addCriterion("CHL_ID <", value, "chlId");
            return (Criteria) this;
        }

        public Criteria andChlIdLessThanOrEqualTo(String value) {
            addCriterion("CHL_ID <=", value, "chlId");
            return (Criteria) this;
        }

        public Criteria andChlIdLike(String value) {
            addCriterion("CHL_ID like", value, "chlId");
            return (Criteria) this;
        }

        public Criteria andChlIdNotLike(String value) {
            addCriterion("CHL_ID not like", value, "chlId");
            return (Criteria) this;
        }

        public Criteria andChlIdIn(List<String> values) {
            addCriterion("CHL_ID in", values, "chlId");
            return (Criteria) this;
        }

        public Criteria andChlIdNotIn(List<String> values) {
            addCriterion("CHL_ID not in", values, "chlId");
            return (Criteria) this;
        }

        public Criteria andChlIdBetween(String value1, String value2) {
            addCriterion("CHL_ID between", value1, value2, "chlId");
            return (Criteria) this;
        }

        public Criteria andChlIdNotBetween(String value1, String value2) {
            addCriterion("CHL_ID not between", value1, value2, "chlId");
            return (Criteria) this;
        }

        public Criteria andOperIdIsNull() {
            addCriterion("OPER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOperIdIsNotNull() {
            addCriterion("OPER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOperIdEqualTo(String value) {
            addCriterion("OPER_ID =", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdNotEqualTo(String value) {
            addCriterion("OPER_ID <>", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdGreaterThan(String value) {
            addCriterion("OPER_ID >", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdGreaterThanOrEqualTo(String value) {
            addCriterion("OPER_ID >=", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdLessThan(String value) {
            addCriterion("OPER_ID <", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdLessThanOrEqualTo(String value) {
            addCriterion("OPER_ID <=", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdLike(String value) {
            addCriterion("OPER_ID like", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdNotLike(String value) {
            addCriterion("OPER_ID not like", value, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdIn(List<String> values) {
            addCriterion("OPER_ID in", values, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdNotIn(List<String> values) {
            addCriterion("OPER_ID not in", values, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdBetween(String value1, String value2) {
            addCriterion("OPER_ID between", value1, value2, "operId");
            return (Criteria) this;
        }

        public Criteria andOperIdNotBetween(String value1, String value2) {
            addCriterion("OPER_ID not between", value1, value2, "operId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdIsNull() {
            addCriterion("WORKFLOW_ID is null");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdIsNotNull() {
            addCriterion("WORKFLOW_ID is not null");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdEqualTo(String value) {
            addCriterion("WORKFLOW_ID =", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdNotEqualTo(String value) {
            addCriterion("WORKFLOW_ID <>", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdGreaterThan(String value) {
            addCriterion("WORKFLOW_ID >", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdGreaterThanOrEqualTo(String value) {
            addCriterion("WORKFLOW_ID >=", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdLessThan(String value) {
            addCriterion("WORKFLOW_ID <", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdLessThanOrEqualTo(String value) {
            addCriterion("WORKFLOW_ID <=", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdLike(String value) {
            addCriterion("WORKFLOW_ID like", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdNotLike(String value) {
            addCriterion("WORKFLOW_ID not like", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdIn(List<String> values) {
            addCriterion("WORKFLOW_ID in", values, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdNotIn(List<String> values) {
            addCriterion("WORKFLOW_ID not in", values, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdBetween(String value1, String value2) {
            addCriterion("WORKFLOW_ID between", value1, value2, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdNotBetween(String value1, String value2) {
            addCriterion("WORKFLOW_ID not between", value1, value2, "workflowId");
            return (Criteria) this;
        }

        public Criteria andReasonTypeIsNull() {
            addCriterion("REASON_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andReasonTypeIsNotNull() {
            addCriterion("REASON_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andReasonTypeEqualTo(String value) {
            addCriterion("REASON_TYPE =", value, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeNotEqualTo(String value) {
            addCriterion("REASON_TYPE <>", value, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeGreaterThan(String value) {
            addCriterion("REASON_TYPE >", value, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeGreaterThanOrEqualTo(String value) {
            addCriterion("REASON_TYPE >=", value, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeLessThan(String value) {
            addCriterion("REASON_TYPE <", value, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeLessThanOrEqualTo(String value) {
            addCriterion("REASON_TYPE <=", value, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeLike(String value) {
            addCriterion("REASON_TYPE like", value, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeNotLike(String value) {
            addCriterion("REASON_TYPE not like", value, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeIn(List<String> values) {
            addCriterion("REASON_TYPE in", values, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeNotIn(List<String> values) {
            addCriterion("REASON_TYPE not in", values, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeBetween(String value1, String value2) {
            addCriterion("REASON_TYPE between", value1, value2, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeNotBetween(String value1, String value2) {
            addCriterion("REASON_TYPE not between", value1, value2, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonDescIsNull() {
            addCriterion("REASON_DESC is null");
            return (Criteria) this;
        }

        public Criteria andReasonDescIsNotNull() {
            addCriterion("REASON_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andReasonDescEqualTo(String value) {
            addCriterion("REASON_DESC =", value, "reasonDesc");
            return (Criteria) this;
        }

        public Criteria andReasonDescNotEqualTo(String value) {
            addCriterion("REASON_DESC <>", value, "reasonDesc");
            return (Criteria) this;
        }

        public Criteria andReasonDescGreaterThan(String value) {
            addCriterion("REASON_DESC >", value, "reasonDesc");
            return (Criteria) this;
        }

        public Criteria andReasonDescGreaterThanOrEqualTo(String value) {
            addCriterion("REASON_DESC >=", value, "reasonDesc");
            return (Criteria) this;
        }

        public Criteria andReasonDescLessThan(String value) {
            addCriterion("REASON_DESC <", value, "reasonDesc");
            return (Criteria) this;
        }

        public Criteria andReasonDescLessThanOrEqualTo(String value) {
            addCriterion("REASON_DESC <=", value, "reasonDesc");
            return (Criteria) this;
        }

        public Criteria andReasonDescLike(String value) {
            addCriterion("REASON_DESC like", value, "reasonDesc");
            return (Criteria) this;
        }

        public Criteria andReasonDescNotLike(String value) {
            addCriterion("REASON_DESC not like", value, "reasonDesc");
            return (Criteria) this;
        }

        public Criteria andReasonDescIn(List<String> values) {
            addCriterion("REASON_DESC in", values, "reasonDesc");
            return (Criteria) this;
        }

        public Criteria andReasonDescNotIn(List<String> values) {
            addCriterion("REASON_DESC not in", values, "reasonDesc");
            return (Criteria) this;
        }

        public Criteria andReasonDescBetween(String value1, String value2) {
            addCriterion("REASON_DESC between", value1, value2, "reasonDesc");
            return (Criteria) this;
        }

        public Criteria andReasonDescNotBetween(String value1, String value2) {
            addCriterion("REASON_DESC not between", value1, value2, "reasonDesc");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNull() {
            addCriterion("FINISH_TIME is null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNotNull() {
            addCriterion("FINISH_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeEqualTo(Timestamp value) {
            addCriterion("FINISH_TIME =", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotEqualTo(Timestamp value) {
            addCriterion("FINISH_TIME <>", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThan(Timestamp value) {
            addCriterion("FINISH_TIME >", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThanOrEqualTo(Timestamp value) {
            addCriterion("FINISH_TIME >=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThan(Timestamp value) {
            addCriterion("FINISH_TIME <", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThanOrEqualTo(Timestamp value) {
            addCriterion("FINISH_TIME <=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIn(List<Timestamp> values) {
            addCriterion("FINISH_TIME in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotIn(List<Timestamp> values) {
            addCriterion("FINISH_TIME not in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeBetween(Timestamp value1, Timestamp value2) {
            addCriterion("FINISH_TIME between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotBetween(Timestamp value1, Timestamp value2) {
            addCriterion("FINISH_TIME not between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andOrigOrderIdIsNull() {
            addCriterion("ORIG_ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrigOrderIdIsNotNull() {
            addCriterion("ORIG_ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrigOrderIdEqualTo(long value) {
            addCriterion("ORIG_ORDER_ID =", value, "origOrderId");
            return (Criteria) this;
        }

        public Criteria andOrigOrderIdNotEqualTo(long value) {
            addCriterion("ORIG_ORDER_ID <>", value, "origOrderId");
            return (Criteria) this;
        }

        public Criteria andOrigOrderIdGreaterThan(long value) {
            addCriterion("ORIG_ORDER_ID >", value, "origOrderId");
            return (Criteria) this;
        }

        public Criteria andOrigOrderIdGreaterThanOrEqualTo(long value) {
            addCriterion("ORIG_ORDER_ID >=", value, "origOrderId");
            return (Criteria) this;
        }

        public Criteria andOrigOrderIdLessThan(long value) {
            addCriterion("ORIG_ORDER_ID <", value, "origOrderId");
            return (Criteria) this;
        }

        public Criteria andOrigOrderIdLessThanOrEqualTo(long value) {
            addCriterion("ORIG_ORDER_ID <=", value, "origOrderId");
            return (Criteria) this;
        }

        public Criteria andOrigOrderIdIn(List<Long> values) {
            addCriterion("ORIG_ORDER_ID in", values, "origOrderId");
            return (Criteria) this;
        }

        public Criteria andOrigOrderIdNotIn(List<Long> values) {
            addCriterion("ORIG_ORDER_ID not in", values, "origOrderId");
            return (Criteria) this;
        }

        public Criteria andOrigOrderIdBetween(long value1, long value2) {
            addCriterion("ORIG_ORDER_ID between", value1, value2, "origOrderId");
            return (Criteria) this;
        }

        public Criteria andOrigOrderIdNotBetween(long value1, long value2) {
            addCriterion("ORIG_ORDER_ID not between", value1, value2, "origOrderId");
            return (Criteria) this;
        }

        public Criteria andOrderDescIsNull() {
            addCriterion("ORDER_DESC is null");
            return (Criteria) this;
        }

        public Criteria andOrderDescIsNotNull() {
            addCriterion("ORDER_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andOrderDescEqualTo(String value) {
            addCriterion("ORDER_DESC =", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescNotEqualTo(String value) {
            addCriterion("ORDER_DESC <>", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescGreaterThan(String value) {
            addCriterion("ORDER_DESC >", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_DESC >=", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescLessThan(String value) {
            addCriterion("ORDER_DESC <", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescLessThanOrEqualTo(String value) {
            addCriterion("ORDER_DESC <=", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescLike(String value) {
            addCriterion("ORDER_DESC like", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescNotLike(String value) {
            addCriterion("ORDER_DESC not like", value, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescIn(List<String> values) {
            addCriterion("ORDER_DESC in", values, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescNotIn(List<String> values) {
            addCriterion("ORDER_DESC not in", values, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescBetween(String value1, String value2) {
            addCriterion("ORDER_DESC between", value1, value2, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andOrderDescNotBetween(String value1, String value2) {
            addCriterion("ORDER_DESC not between", value1, value2, "orderDesc");
            return (Criteria) this;
        }

        public Criteria andKeywordsIsNull() {
            addCriterion("KEYWORDS is null");
            return (Criteria) this;
        }

        public Criteria andKeywordsIsNotNull() {
            addCriterion("KEYWORDS is not null");
            return (Criteria) this;
        }

        public Criteria andKeywordsEqualTo(String value) {
            addCriterion("KEYWORDS =", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotEqualTo(String value) {
            addCriterion("KEYWORDS <>", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThan(String value) {
            addCriterion("KEYWORDS >", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThanOrEqualTo(String value) {
            addCriterion("KEYWORDS >=", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThan(String value) {
            addCriterion("KEYWORDS <", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThanOrEqualTo(String value) {
            addCriterion("KEYWORDS <=", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLike(String value) {
            addCriterion("KEYWORDS like", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotLike(String value) {
            addCriterion("KEYWORDS not like", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsIn(List<String> values) {
            addCriterion("KEYWORDS in", values, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotIn(List<String> values) {
            addCriterion("KEYWORDS not in", values, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsBetween(String value1, String value2) {
            addCriterion("KEYWORDS between", value1, value2, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotBetween(String value1, String value2) {
            addCriterion("KEYWORDS not between", value1, value2, "keywords");
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

        public Criteria andExternalOrderIdIsNull() {
            addCriterion("EXTERNAL_ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andExternalOrderIdIsNotNull() {
            addCriterion("EXTERNAL_ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andExternalOrderIdEqualTo(String value) {
            addCriterion("EXTERNAL_ORDER_ID =", value, "externalOrderId");
            return (Criteria) this;
        }

        public Criteria andExternalOrderIdNotEqualTo(String value) {
            addCriterion("EXTERNAL_ORDER_ID <>", value, "externalOrderId");
            return (Criteria) this;
        }

        public Criteria andExternalOrderIdGreaterThan(String value) {
            addCriterion("EXTERNAL_ORDER_ID >", value, "externalOrderId");
            return (Criteria) this;
        }

        public Criteria andExternalOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("EXTERNAL_ORDER_ID >=", value, "externalOrderId");
            return (Criteria) this;
        }

        public Criteria andExternalOrderIdLessThan(String value) {
            addCriterion("EXTERNAL_ORDER_ID <", value, "externalOrderId");
            return (Criteria) this;
        }

        public Criteria andExternalOrderIdLessThanOrEqualTo(String value) {
            addCriterion("EXTERNAL_ORDER_ID <=", value, "externalOrderId");
            return (Criteria) this;
        }

        public Criteria andExternalOrderIdLike(String value) {
            addCriterion("EXTERNAL_ORDER_ID like", value, "externalOrderId");
            return (Criteria) this;
        }

        public Criteria andExternalOrderIdNotLike(String value) {
            addCriterion("EXTERNAL_ORDER_ID not like", value, "externalOrderId");
            return (Criteria) this;
        }

        public Criteria andExternalOrderIdIn(List<String> values) {
            addCriterion("EXTERNAL_ORDER_ID in", values, "externalOrderId");
            return (Criteria) this;
        }

        public Criteria andExternalOrderIdNotIn(List<String> values) {
            addCriterion("EXTERNAL_ORDER_ID not in", values, "externalOrderId");
            return (Criteria) this;
        }

        public Criteria andExternalOrderIdBetween(String value1, String value2) {
            addCriterion("EXTERNAL_ORDER_ID between", value1, value2, "externalOrderId");
            return (Criteria) this;
        }

        public Criteria andExternalOrderIdNotBetween(String value1, String value2) {
            addCriterion("EXTERNAL_ORDER_ID not between", value1, value2, "externalOrderId");
            return (Criteria) this;
        }

        public Criteria andExternalSupplyIdIsNull() {
            addCriterion("EXTERNAL_SUPPLY_ID is null");
            return (Criteria) this;
        }

        public Criteria andExternalSupplyIdIsNotNull() {
            addCriterion("EXTERNAL_SUPPLY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andExternalSupplyIdEqualTo(String value) {
            addCriterion("EXTERNAL_SUPPLY_ID =", value, "externalSupplyId");
            return (Criteria) this;
        }

        public Criteria andExternalSupplyIdNotEqualTo(String value) {
            addCriterion("EXTERNAL_SUPPLY_ID <>", value, "externalSupplyId");
            return (Criteria) this;
        }

        public Criteria andExternalSupplyIdGreaterThan(String value) {
            addCriterion("EXTERNAL_SUPPLY_ID >", value, "externalSupplyId");
            return (Criteria) this;
        }

        public Criteria andExternalSupplyIdGreaterThanOrEqualTo(String value) {
            addCriterion("EXTERNAL_SUPPLY_ID >=", value, "externalSupplyId");
            return (Criteria) this;
        }

        public Criteria andExternalSupplyIdLessThan(String value) {
            addCriterion("EXTERNAL_SUPPLY_ID <", value, "externalSupplyId");
            return (Criteria) this;
        }

        public Criteria andExternalSupplyIdLessThanOrEqualTo(String value) {
            addCriterion("EXTERNAL_SUPPLY_ID <=", value, "externalSupplyId");
            return (Criteria) this;
        }

        public Criteria andExternalSupplyIdLike(String value) {
            addCriterion("EXTERNAL_SUPPLY_ID like", value, "externalSupplyId");
            return (Criteria) this;
        }

        public Criteria andExternalSupplyIdNotLike(String value) {
            addCriterion("EXTERNAL_SUPPLY_ID not like", value, "externalSupplyId");
            return (Criteria) this;
        }

        public Criteria andExternalSupplyIdIn(List<String> values) {
            addCriterion("EXTERNAL_SUPPLY_ID in", values, "externalSupplyId");
            return (Criteria) this;
        }

        public Criteria andExternalSupplyIdNotIn(List<String> values) {
            addCriterion("EXTERNAL_SUPPLY_ID not in", values, "externalSupplyId");
            return (Criteria) this;
        }

        public Criteria andExternalSupplyIdBetween(String value1, String value2) {
            addCriterion("EXTERNAL_SUPPLY_ID between", value1, value2, "externalSupplyId");
            return (Criteria) this;
        }

        public Criteria andExternalSupplyIdNotBetween(String value1, String value2) {
            addCriterion("EXTERNAL_SUPPLY_ID not between", value1, value2, "externalSupplyId");
            return (Criteria) this;
        }

        public Criteria andDownstreamOrderIdIsNull() {
            addCriterion("DOWNSTREAM_ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andDownstreamOrderIdIsNotNull() {
            addCriterion("DOWNSTREAM_ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDownstreamOrderIdEqualTo(String value) {
            addCriterion("DOWNSTREAM_ORDER_ID =", value, "downstreamOrderId");
            return (Criteria) this;
        }

        public Criteria andDownstreamOrderIdNotEqualTo(String value) {
            addCriterion("DOWNSTREAM_ORDER_ID <>", value, "downstreamOrderId");
            return (Criteria) this;
        }

        public Criteria andDownstreamOrderIdGreaterThan(String value) {
            addCriterion("DOWNSTREAM_ORDER_ID >", value, "downstreamOrderId");
            return (Criteria) this;
        }

        public Criteria andDownstreamOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("DOWNSTREAM_ORDER_ID >=", value, "downstreamOrderId");
            return (Criteria) this;
        }

        public Criteria andDownstreamOrderIdLessThan(String value) {
            addCriterion("DOWNSTREAM_ORDER_ID <", value, "downstreamOrderId");
            return (Criteria) this;
        }

        public Criteria andDownstreamOrderIdLessThanOrEqualTo(String value) {
            addCriterion("DOWNSTREAM_ORDER_ID <=", value, "downstreamOrderId");
            return (Criteria) this;
        }

        public Criteria andDownstreamOrderIdLike(String value) {
            addCriterion("DOWNSTREAM_ORDER_ID like", value, "downstreamOrderId");
            return (Criteria) this;
        }

        public Criteria andDownstreamOrderIdNotLike(String value) {
            addCriterion("DOWNSTREAM_ORDER_ID not like", value, "downstreamOrderId");
            return (Criteria) this;
        }

        public Criteria andDownstreamOrderIdIn(List<String> values) {
            addCriterion("DOWNSTREAM_ORDER_ID in", values, "downstreamOrderId");
            return (Criteria) this;
        }

        public Criteria andDownstreamOrderIdNotIn(List<String> values) {
            addCriterion("DOWNSTREAM_ORDER_ID not in", values, "downstreamOrderId");
            return (Criteria) this;
        }

        public Criteria andDownstreamOrderIdBetween(String value1, String value2) {
            addCriterion("DOWNSTREAM_ORDER_ID between", value1, value2, "downstreamOrderId");
            return (Criteria) this;
        }

        public Criteria andDownstreamOrderIdNotBetween(String value1, String value2) {
            addCriterion("DOWNSTREAM_ORDER_ID not between", value1, value2, "downstreamOrderId");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNull() {
            addCriterion("USER_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andUserTypeIsNotNull() {
            addCriterion("USER_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andUserTypeEqualTo(String value) {
            addCriterion("USER_TYPE =", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotEqualTo(String value) {
            addCriterion("USER_TYPE <>", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThan(String value) {
            addCriterion("USER_TYPE >", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeGreaterThanOrEqualTo(String value) {
            addCriterion("USER_TYPE >=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThan(String value) {
            addCriterion("USER_TYPE <", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLessThanOrEqualTo(String value) {
            addCriterion("USER_TYPE <=", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeLike(String value) {
            addCriterion("USER_TYPE like", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotLike(String value) {
            addCriterion("USER_TYPE not like", value, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeIn(List<String> values) {
            addCriterion("USER_TYPE in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotIn(List<String> values) {
            addCriterion("USER_TYPE not in", values, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeBetween(String value1, String value2) {
            addCriterion("USER_TYPE between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andUserTypeNotBetween(String value1, String value2) {
            addCriterion("USER_TYPE not between", value1, value2, "userType");
            return (Criteria) this;
        }

        public Criteria andIpAddressIsNull() {
            addCriterion("IP_ADDRESS is null");
            return (Criteria) this;
        }

        public Criteria andIpAddressIsNotNull() {
            addCriterion("IP_ADDRESS is not null");
            return (Criteria) this;
        }

        public Criteria andIpAddressEqualTo(String value) {
            addCriterion("IP_ADDRESS =", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressNotEqualTo(String value) {
            addCriterion("IP_ADDRESS <>", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressGreaterThan(String value) {
            addCriterion("IP_ADDRESS >", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressGreaterThanOrEqualTo(String value) {
            addCriterion("IP_ADDRESS >=", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressLessThan(String value) {
            addCriterion("IP_ADDRESS <", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressLessThanOrEqualTo(String value) {
            addCriterion("IP_ADDRESS <=", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressLike(String value) {
            addCriterion("IP_ADDRESS like", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressNotLike(String value) {
            addCriterion("IP_ADDRESS not like", value, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressIn(List<String> values) {
            addCriterion("IP_ADDRESS in", values, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressNotIn(List<String> values) {
            addCriterion("IP_ADDRESS not in", values, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressBetween(String value1, String value2) {
            addCriterion("IP_ADDRESS between", value1, value2, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIpAddressNotBetween(String value1, String value2) {
            addCriterion("IP_ADDRESS not between", value1, value2, "ipAddress");
            return (Criteria) this;
        }

        public Criteria andIfWarningIsNull() {
            addCriterion("IF_WARNING is null");
            return (Criteria) this;
        }

        public Criteria andIfWarningIsNotNull() {
            addCriterion("IF_WARNING is not null");
            return (Criteria) this;
        }

        public Criteria andIfWarningEqualTo(String value) {
            addCriterion("IF_WARNING =", value, "ifWarning");
            return (Criteria) this;
        }

        public Criteria andIfWarningNotEqualTo(String value) {
            addCriterion("IF_WARNING <>", value, "ifWarning");
            return (Criteria) this;
        }

        public Criteria andIfWarningGreaterThan(String value) {
            addCriterion("IF_WARNING >", value, "ifWarning");
            return (Criteria) this;
        }

        public Criteria andIfWarningGreaterThanOrEqualTo(String value) {
            addCriterion("IF_WARNING >=", value, "ifWarning");
            return (Criteria) this;
        }

        public Criteria andIfWarningLessThan(String value) {
            addCriterion("IF_WARNING <", value, "ifWarning");
            return (Criteria) this;
        }

        public Criteria andIfWarningLessThanOrEqualTo(String value) {
            addCriterion("IF_WARNING <=", value, "ifWarning");
            return (Criteria) this;
        }

        public Criteria andIfWarningLike(String value) {
            addCriterion("IF_WARNING like", value, "ifWarning");
            return (Criteria) this;
        }

        public Criteria andIfWarningNotLike(String value) {
            addCriterion("IF_WARNING not like", value, "ifWarning");
            return (Criteria) this;
        }

        public Criteria andIfWarningIn(List<String> values) {
            addCriterion("IF_WARNING in", values, "ifWarning");
            return (Criteria) this;
        }

        public Criteria andIfWarningNotIn(List<String> values) {
            addCriterion("IF_WARNING not in", values, "ifWarning");
            return (Criteria) this;
        }

        public Criteria andIfWarningBetween(String value1, String value2) {
            addCriterion("IF_WARNING between", value1, value2, "ifWarning");
            return (Criteria) this;
        }

        public Criteria andIfWarningNotBetween(String value1, String value2) {
            addCriterion("IF_WARNING not between", value1, value2, "ifWarning");
            return (Criteria) this;
        }

        public Criteria andWarningTypeIsNull() {
            addCriterion("WARNING_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andWarningTypeIsNotNull() {
            addCriterion("WARNING_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andWarningTypeEqualTo(String value) {
            addCriterion("WARNING_TYPE =", value, "warningType");
            return (Criteria) this;
        }

        public Criteria andWarningTypeNotEqualTo(String value) {
            addCriterion("WARNING_TYPE <>", value, "warningType");
            return (Criteria) this;
        }

        public Criteria andWarningTypeGreaterThan(String value) {
            addCriterion("WARNING_TYPE >", value, "warningType");
            return (Criteria) this;
        }

        public Criteria andWarningTypeGreaterThanOrEqualTo(String value) {
            addCriterion("WARNING_TYPE >=", value, "warningType");
            return (Criteria) this;
        }

        public Criteria andWarningTypeLessThan(String value) {
            addCriterion("WARNING_TYPE <", value, "warningType");
            return (Criteria) this;
        }

        public Criteria andWarningTypeLessThanOrEqualTo(String value) {
            addCriterion("WARNING_TYPE <=", value, "warningType");
            return (Criteria) this;
        }

        public Criteria andWarningTypeLike(String value) {
            addCriterion("WARNING_TYPE like", value, "warningType");
            return (Criteria) this;
        }

        public Criteria andWarningTypeNotLike(String value) {
            addCriterion("WARNING_TYPE not like", value, "warningType");
            return (Criteria) this;
        }

        public Criteria andWarningTypeIn(List<String> values) {
            addCriterion("WARNING_TYPE in", values, "warningType");
            return (Criteria) this;
        }

        public Criteria andWarningTypeNotIn(List<String> values) {
            addCriterion("WARNING_TYPE not in", values, "warningType");
            return (Criteria) this;
        }

        public Criteria andWarningTypeBetween(String value1, String value2) {
            addCriterion("WARNING_TYPE between", value1, value2, "warningType");
            return (Criteria) this;
        }

        public Criteria andWarningTypeNotBetween(String value1, String value2) {
            addCriterion("WARNING_TYPE not between", value1, value2, "warningType");
            return (Criteria) this;
        }

        public Criteria andCusServiceFlagIsNull() {
            addCriterion("CUS_SERVICE_FLAG is null");
            return (Criteria) this;
        }

        public Criteria andCusServiceFlagIsNotNull() {
            addCriterion("CUS_SERVICE_FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andCusServiceFlagEqualTo(String value) {
            addCriterion("CUS_SERVICE_FLAG =", value, "cusServiceFlag");
            return (Criteria) this;
        }

        public Criteria andCusServiceFlagNotEqualTo(String value) {
            addCriterion("CUS_SERVICE_FLAG <>", value, "cusServiceFlag");
            return (Criteria) this;
        }

        public Criteria andCusServiceFlagGreaterThan(String value) {
            addCriterion("CUS_SERVICE_FLAG >", value, "cusServiceFlag");
            return (Criteria) this;
        }

        public Criteria andCusServiceFlagGreaterThanOrEqualTo(String value) {
            addCriterion("CUS_SERVICE_FLAG >=", value, "cusServiceFlag");
            return (Criteria) this;
        }

        public Criteria andCusServiceFlagLessThan(String value) {
            addCriterion("CUS_SERVICE_FLAG <", value, "cusServiceFlag");
            return (Criteria) this;
        }

        public Criteria andCusServiceFlagLessThanOrEqualTo(String value) {
            addCriterion("CUS_SERVICE_FLAG <=", value, "cusServiceFlag");
            return (Criteria) this;
        }

        public Criteria andCusServiceFlagLike(String value) {
            addCriterion("CUS_SERVICE_FLAG like", value, "cusServiceFlag");
            return (Criteria) this;
        }

        public Criteria andCusServiceFlagNotLike(String value) {
            addCriterion("CUS_SERVICE_FLAG not like", value, "cusServiceFlag");
            return (Criteria) this;
        }

        public Criteria andCusServiceFlagIn(List<String> values) {
            addCriterion("CUS_SERVICE_FLAG in", values, "cusServiceFlag");
            return (Criteria) this;
        }

        public Criteria andCusServiceFlagNotIn(List<String> values) {
            addCriterion("CUS_SERVICE_FLAG not in", values, "cusServiceFlag");
            return (Criteria) this;
        }

        public Criteria andCusServiceFlagBetween(String value1, String value2) {
            addCriterion("CUS_SERVICE_FLAG between", value1, value2, "cusServiceFlag");
            return (Criteria) this;
        }

        public Criteria andCusServiceFlagNotBetween(String value1, String value2) {
            addCriterion("CUS_SERVICE_FLAG not between", value1, value2, "cusServiceFlag");
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