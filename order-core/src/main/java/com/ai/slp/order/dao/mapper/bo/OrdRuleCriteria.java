package com.ai.slp.order.dao.mapper.bo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrdRuleCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OrdRuleCriteria() {
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

        public Criteria andOrderRuleIdIsNull() {
            addCriterion("order_rule_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderRuleIdIsNotNull() {
            addCriterion("order_rule_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderRuleIdEqualTo(String value) {
            addCriterion("order_rule_id =", value, "orderRuleId");
            return (Criteria) this;
        }

        public Criteria andOrderRuleIdNotEqualTo(String value) {
            addCriterion("order_rule_id <>", value, "orderRuleId");
            return (Criteria) this;
        }

        public Criteria andOrderRuleIdGreaterThan(String value) {
            addCriterion("order_rule_id >", value, "orderRuleId");
            return (Criteria) this;
        }

        public Criteria andOrderRuleIdGreaterThanOrEqualTo(String value) {
            addCriterion("order_rule_id >=", value, "orderRuleId");
            return (Criteria) this;
        }

        public Criteria andOrderRuleIdLessThan(String value) {
            addCriterion("order_rule_id <", value, "orderRuleId");
            return (Criteria) this;
        }

        public Criteria andOrderRuleIdLessThanOrEqualTo(String value) {
            addCriterion("order_rule_id <=", value, "orderRuleId");
            return (Criteria) this;
        }

        public Criteria andOrderRuleIdLike(String value) {
            addCriterion("order_rule_id like", value, "orderRuleId");
            return (Criteria) this;
        }

        public Criteria andOrderRuleIdNotLike(String value) {
            addCriterion("order_rule_id not like", value, "orderRuleId");
            return (Criteria) this;
        }

        public Criteria andOrderRuleIdIn(List<String> values) {
            addCriterion("order_rule_id in", values, "orderRuleId");
            return (Criteria) this;
        }

        public Criteria andOrderRuleIdNotIn(List<String> values) {
            addCriterion("order_rule_id not in", values, "orderRuleId");
            return (Criteria) this;
        }

        public Criteria andOrderRuleIdBetween(String value1, String value2) {
            addCriterion("order_rule_id between", value1, value2, "orderRuleId");
            return (Criteria) this;
        }

        public Criteria andOrderRuleIdNotBetween(String value1, String value2) {
            addCriterion("order_rule_id not between", value1, value2, "orderRuleId");
            return (Criteria) this;
        }

        public Criteria andMonitorTimeIsNull() {
            addCriterion("monitor_time is null");
            return (Criteria) this;
        }

        public Criteria andMonitorTimeIsNotNull() {
            addCriterion("monitor_time is not null");
            return (Criteria) this;
        }

        public Criteria andMonitorTimeEqualTo(Integer value) {
            addCriterion("monitor_time =", value, "monitorTime");
            return (Criteria) this;
        }

        public Criteria andMonitorTimeNotEqualTo(Integer value) {
            addCriterion("monitor_time <>", value, "monitorTime");
            return (Criteria) this;
        }

        public Criteria andMonitorTimeGreaterThan(Integer value) {
            addCriterion("monitor_time >", value, "monitorTime");
            return (Criteria) this;
        }

        public Criteria andMonitorTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("monitor_time >=", value, "monitorTime");
            return (Criteria) this;
        }

        public Criteria andMonitorTimeLessThan(Integer value) {
            addCriterion("monitor_time <", value, "monitorTime");
            return (Criteria) this;
        }

        public Criteria andMonitorTimeLessThanOrEqualTo(Integer value) {
            addCriterion("monitor_time <=", value, "monitorTime");
            return (Criteria) this;
        }

        public Criteria andMonitorTimeIn(List<Integer> values) {
            addCriterion("monitor_time in", values, "monitorTime");
            return (Criteria) this;
        }

        public Criteria andMonitorTimeNotIn(List<Integer> values) {
            addCriterion("monitor_time not in", values, "monitorTime");
            return (Criteria) this;
        }

        public Criteria andMonitorTimeBetween(Integer value1, Integer value2) {
            addCriterion("monitor_time between", value1, value2, "monitorTime");
            return (Criteria) this;
        }

        public Criteria andMonitorTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("monitor_time not between", value1, value2, "monitorTime");
            return (Criteria) this;
        }

        public Criteria andTimeTypeIsNull() {
            addCriterion("time_type is null");
            return (Criteria) this;
        }

        public Criteria andTimeTypeIsNotNull() {
            addCriterion("time_type is not null");
            return (Criteria) this;
        }

        public Criteria andTimeTypeEqualTo(String value) {
            addCriterion("time_type =", value, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeNotEqualTo(String value) {
            addCriterion("time_type <>", value, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeGreaterThan(String value) {
            addCriterion("time_type >", value, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("time_type >=", value, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeLessThan(String value) {
            addCriterion("time_type <", value, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeLessThanOrEqualTo(String value) {
            addCriterion("time_type <=", value, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeLike(String value) {
            addCriterion("time_type like", value, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeNotLike(String value) {
            addCriterion("time_type not like", value, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeIn(List<String> values) {
            addCriterion("time_type in", values, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeNotIn(List<String> values) {
            addCriterion("time_type not in", values, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeBetween(String value1, String value2) {
            addCriterion("time_type between", value1, value2, "timeType");
            return (Criteria) this;
        }

        public Criteria andTimeTypeNotBetween(String value1, String value2) {
            addCriterion("time_type not between", value1, value2, "timeType");
            return (Criteria) this;
        }

        public Criteria andOrderSumIsNull() {
            addCriterion("order_sum is null");
            return (Criteria) this;
        }

        public Criteria andOrderSumIsNotNull() {
            addCriterion("order_sum is not null");
            return (Criteria) this;
        }

        public Criteria andOrderSumEqualTo(Integer value) {
            addCriterion("order_sum =", value, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumNotEqualTo(Integer value) {
            addCriterion("order_sum <>", value, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumGreaterThan(Integer value) {
            addCriterion("order_sum >", value, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_sum >=", value, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumLessThan(Integer value) {
            addCriterion("order_sum <", value, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumLessThanOrEqualTo(Integer value) {
            addCriterion("order_sum <=", value, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumIn(List<Integer> values) {
            addCriterion("order_sum in", values, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumNotIn(List<Integer> values) {
            addCriterion("order_sum not in", values, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumBetween(Integer value1, Integer value2) {
            addCriterion("order_sum between", value1, value2, "orderSum");
            return (Criteria) this;
        }

        public Criteria andOrderSumNotBetween(Integer value1, Integer value2) {
            addCriterion("order_sum not between", value1, value2, "orderSum");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Timestamp value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Timestamp value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Timestamp value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Timestamp value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Timestamp value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Timestamp value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Timestamp> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Timestamp> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Timestamp value1, Timestamp value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Timestamp value1, Timestamp value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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