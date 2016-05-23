package com.ai.slp.order.dao.mapper.bo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrdOdStateChgCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OrdOdStateChgCriteria() {
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

        public Criteria andStateChgIdIsNull() {
            addCriterion("STATE_CHG_ID is null");
            return (Criteria) this;
        }

        public Criteria andStateChgIdIsNotNull() {
            addCriterion("STATE_CHG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andStateChgIdEqualTo(long value) {
            addCriterion("STATE_CHG_ID =", value, "stateChgId");
            return (Criteria) this;
        }

        public Criteria andStateChgIdNotEqualTo(long value) {
            addCriterion("STATE_CHG_ID <>", value, "stateChgId");
            return (Criteria) this;
        }

        public Criteria andStateChgIdGreaterThan(long value) {
            addCriterion("STATE_CHG_ID >", value, "stateChgId");
            return (Criteria) this;
        }

        public Criteria andStateChgIdGreaterThanOrEqualTo(long value) {
            addCriterion("STATE_CHG_ID >=", value, "stateChgId");
            return (Criteria) this;
        }

        public Criteria andStateChgIdLessThan(long value) {
            addCriterion("STATE_CHG_ID <", value, "stateChgId");
            return (Criteria) this;
        }

        public Criteria andStateChgIdLessThanOrEqualTo(long value) {
            addCriterion("STATE_CHG_ID <=", value, "stateChgId");
            return (Criteria) this;
        }

        public Criteria andStateChgIdIn(List<Long> values) {
            addCriterion("STATE_CHG_ID in", values, "stateChgId");
            return (Criteria) this;
        }

        public Criteria andStateChgIdNotIn(List<Long> values) {
            addCriterion("STATE_CHG_ID not in", values, "stateChgId");
            return (Criteria) this;
        }

        public Criteria andStateChgIdBetween(long value1, long value2) {
            addCriterion("STATE_CHG_ID between", value1, value2, "stateChgId");
            return (Criteria) this;
        }

        public Criteria andStateChgIdNotBetween(long value1, long value2) {
            addCriterion("STATE_CHG_ID not between", value1, value2, "stateChgId");
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

        public Criteria andOrgStateIsNull() {
            addCriterion("ORG_STATE is null");
            return (Criteria) this;
        }

        public Criteria andOrgStateIsNotNull() {
            addCriterion("ORG_STATE is not null");
            return (Criteria) this;
        }

        public Criteria andOrgStateEqualTo(String value) {
            addCriterion("ORG_STATE =", value, "orgState");
            return (Criteria) this;
        }

        public Criteria andOrgStateNotEqualTo(String value) {
            addCriterion("ORG_STATE <>", value, "orgState");
            return (Criteria) this;
        }

        public Criteria andOrgStateGreaterThan(String value) {
            addCriterion("ORG_STATE >", value, "orgState");
            return (Criteria) this;
        }

        public Criteria andOrgStateGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_STATE >=", value, "orgState");
            return (Criteria) this;
        }

        public Criteria andOrgStateLessThan(String value) {
            addCriterion("ORG_STATE <", value, "orgState");
            return (Criteria) this;
        }

        public Criteria andOrgStateLessThanOrEqualTo(String value) {
            addCriterion("ORG_STATE <=", value, "orgState");
            return (Criteria) this;
        }

        public Criteria andOrgStateLike(String value) {
            addCriterion("ORG_STATE like", value, "orgState");
            return (Criteria) this;
        }

        public Criteria andOrgStateNotLike(String value) {
            addCriterion("ORG_STATE not like", value, "orgState");
            return (Criteria) this;
        }

        public Criteria andOrgStateIn(List<String> values) {
            addCriterion("ORG_STATE in", values, "orgState");
            return (Criteria) this;
        }

        public Criteria andOrgStateNotIn(List<String> values) {
            addCriterion("ORG_STATE not in", values, "orgState");
            return (Criteria) this;
        }

        public Criteria andOrgStateBetween(String value1, String value2) {
            addCriterion("ORG_STATE between", value1, value2, "orgState");
            return (Criteria) this;
        }

        public Criteria andOrgStateNotBetween(String value1, String value2) {
            addCriterion("ORG_STATE not between", value1, value2, "orgState");
            return (Criteria) this;
        }

        public Criteria andNewStateIsNull() {
            addCriterion("NEW_STATE is null");
            return (Criteria) this;
        }

        public Criteria andNewStateIsNotNull() {
            addCriterion("NEW_STATE is not null");
            return (Criteria) this;
        }

        public Criteria andNewStateEqualTo(String value) {
            addCriterion("NEW_STATE =", value, "newState");
            return (Criteria) this;
        }

        public Criteria andNewStateNotEqualTo(String value) {
            addCriterion("NEW_STATE <>", value, "newState");
            return (Criteria) this;
        }

        public Criteria andNewStateGreaterThan(String value) {
            addCriterion("NEW_STATE >", value, "newState");
            return (Criteria) this;
        }

        public Criteria andNewStateGreaterThanOrEqualTo(String value) {
            addCriterion("NEW_STATE >=", value, "newState");
            return (Criteria) this;
        }

        public Criteria andNewStateLessThan(String value) {
            addCriterion("NEW_STATE <", value, "newState");
            return (Criteria) this;
        }

        public Criteria andNewStateLessThanOrEqualTo(String value) {
            addCriterion("NEW_STATE <=", value, "newState");
            return (Criteria) this;
        }

        public Criteria andNewStateLike(String value) {
            addCriterion("NEW_STATE like", value, "newState");
            return (Criteria) this;
        }

        public Criteria andNewStateNotLike(String value) {
            addCriterion("NEW_STATE not like", value, "newState");
            return (Criteria) this;
        }

        public Criteria andNewStateIn(List<String> values) {
            addCriterion("NEW_STATE in", values, "newState");
            return (Criteria) this;
        }

        public Criteria andNewStateNotIn(List<String> values) {
            addCriterion("NEW_STATE not in", values, "newState");
            return (Criteria) this;
        }

        public Criteria andNewStateBetween(String value1, String value2) {
            addCriterion("NEW_STATE between", value1, value2, "newState");
            return (Criteria) this;
        }

        public Criteria andNewStateNotBetween(String value1, String value2) {
            addCriterion("NEW_STATE not between", value1, value2, "newState");
            return (Criteria) this;
        }

        public Criteria andChgDescIsNull() {
            addCriterion("CHG_DESC is null");
            return (Criteria) this;
        }

        public Criteria andChgDescIsNotNull() {
            addCriterion("CHG_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andChgDescEqualTo(String value) {
            addCriterion("CHG_DESC =", value, "chgDesc");
            return (Criteria) this;
        }

        public Criteria andChgDescNotEqualTo(String value) {
            addCriterion("CHG_DESC <>", value, "chgDesc");
            return (Criteria) this;
        }

        public Criteria andChgDescGreaterThan(String value) {
            addCriterion("CHG_DESC >", value, "chgDesc");
            return (Criteria) this;
        }

        public Criteria andChgDescGreaterThanOrEqualTo(String value) {
            addCriterion("CHG_DESC >=", value, "chgDesc");
            return (Criteria) this;
        }

        public Criteria andChgDescLessThan(String value) {
            addCriterion("CHG_DESC <", value, "chgDesc");
            return (Criteria) this;
        }

        public Criteria andChgDescLessThanOrEqualTo(String value) {
            addCriterion("CHG_DESC <=", value, "chgDesc");
            return (Criteria) this;
        }

        public Criteria andChgDescLike(String value) {
            addCriterion("CHG_DESC like", value, "chgDesc");
            return (Criteria) this;
        }

        public Criteria andChgDescNotLike(String value) {
            addCriterion("CHG_DESC not like", value, "chgDesc");
            return (Criteria) this;
        }

        public Criteria andChgDescIn(List<String> values) {
            addCriterion("CHG_DESC in", values, "chgDesc");
            return (Criteria) this;
        }

        public Criteria andChgDescNotIn(List<String> values) {
            addCriterion("CHG_DESC not in", values, "chgDesc");
            return (Criteria) this;
        }

        public Criteria andChgDescBetween(String value1, String value2) {
            addCriterion("CHG_DESC between", value1, value2, "chgDesc");
            return (Criteria) this;
        }

        public Criteria andChgDescNotBetween(String value1, String value2) {
            addCriterion("CHG_DESC not between", value1, value2, "chgDesc");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNull() {
            addCriterion("ORG_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrgIdIsNotNull() {
            addCriterion("ORG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrgIdEqualTo(String value) {
            addCriterion("ORG_ID =", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotEqualTo(String value) {
            addCriterion("ORG_ID <>", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThan(String value) {
            addCriterion("ORG_ID >", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_ID >=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThan(String value) {
            addCriterion("ORG_ID <", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLessThanOrEqualTo(String value) {
            addCriterion("ORG_ID <=", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdLike(String value) {
            addCriterion("ORG_ID like", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotLike(String value) {
            addCriterion("ORG_ID not like", value, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdIn(List<String> values) {
            addCriterion("ORG_ID in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotIn(List<String> values) {
            addCriterion("ORG_ID not in", values, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdBetween(String value1, String value2) {
            addCriterion("ORG_ID between", value1, value2, "orgId");
            return (Criteria) this;
        }

        public Criteria andOrgIdNotBetween(String value1, String value2) {
            addCriterion("ORG_ID not between", value1, value2, "orgId");
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

        public Criteria andOperNameIsNull() {
            addCriterion("OPER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andOperNameIsNotNull() {
            addCriterion("OPER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andOperNameEqualTo(String value) {
            addCriterion("OPER_NAME =", value, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameNotEqualTo(String value) {
            addCriterion("OPER_NAME <>", value, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameGreaterThan(String value) {
            addCriterion("OPER_NAME >", value, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameGreaterThanOrEqualTo(String value) {
            addCriterion("OPER_NAME >=", value, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameLessThan(String value) {
            addCriterion("OPER_NAME <", value, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameLessThanOrEqualTo(String value) {
            addCriterion("OPER_NAME <=", value, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameLike(String value) {
            addCriterion("OPER_NAME like", value, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameNotLike(String value) {
            addCriterion("OPER_NAME not like", value, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameIn(List<String> values) {
            addCriterion("OPER_NAME in", values, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameNotIn(List<String> values) {
            addCriterion("OPER_NAME not in", values, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameBetween(String value1, String value2) {
            addCriterion("OPER_NAME between", value1, value2, "operName");
            return (Criteria) this;
        }

        public Criteria andOperNameNotBetween(String value1, String value2) {
            addCriterion("OPER_NAME not between", value1, value2, "operName");
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