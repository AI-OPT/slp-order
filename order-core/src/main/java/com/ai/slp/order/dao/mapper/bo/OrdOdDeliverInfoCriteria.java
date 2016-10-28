package com.ai.slp.order.dao.mapper.bo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrdOdDeliverInfoCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OrdOdDeliverInfoCriteria() {
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

        public Criteria andDeliverInfoIdIsNull() {
            addCriterion("DELIVER_INFO_ID is null");
            return (Criteria) this;
        }

        public Criteria andDeliverInfoIdIsNotNull() {
            addCriterion("DELIVER_INFO_ID is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverInfoIdEqualTo(long value) {
            addCriterion("DELIVER_INFO_ID =", value, "deliverInfoId");
            return (Criteria) this;
        }

        public Criteria andDeliverInfoIdNotEqualTo(long value) {
            addCriterion("DELIVER_INFO_ID <>", value, "deliverInfoId");
            return (Criteria) this;
        }

        public Criteria andDeliverInfoIdGreaterThan(long value) {
            addCriterion("DELIVER_INFO_ID >", value, "deliverInfoId");
            return (Criteria) this;
        }

        public Criteria andDeliverInfoIdGreaterThanOrEqualTo(long value) {
            addCriterion("DELIVER_INFO_ID >=", value, "deliverInfoId");
            return (Criteria) this;
        }

        public Criteria andDeliverInfoIdLessThan(long value) {
            addCriterion("DELIVER_INFO_ID <", value, "deliverInfoId");
            return (Criteria) this;
        }

        public Criteria andDeliverInfoIdLessThanOrEqualTo(long value) {
            addCriterion("DELIVER_INFO_ID <=", value, "deliverInfoId");
            return (Criteria) this;
        }

        public Criteria andDeliverInfoIdIn(List<Long> values) {
            addCriterion("DELIVER_INFO_ID in", values, "deliverInfoId");
            return (Criteria) this;
        }

        public Criteria andDeliverInfoIdNotIn(List<Long> values) {
            addCriterion("DELIVER_INFO_ID not in", values, "deliverInfoId");
            return (Criteria) this;
        }

        public Criteria andDeliverInfoIdBetween(long value1, long value2) {
            addCriterion("DELIVER_INFO_ID between", value1, value2, "deliverInfoId");
            return (Criteria) this;
        }

        public Criteria andDeliverInfoIdNotBetween(long value1, long value2) {
            addCriterion("DELIVER_INFO_ID not between", value1, value2, "deliverInfoId");
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

        public Criteria andHorOrderIdIsNull() {
            addCriterion("HOR_ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andHorOrderIdIsNotNull() {
            addCriterion("HOR_ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andHorOrderIdEqualTo(String value) {
            addCriterion("HOR_ORDER_ID =", value, "horOrderId");
            return (Criteria) this;
        }

        public Criteria andHorOrderIdNotEqualTo(String value) {
            addCriterion("HOR_ORDER_ID <>", value, "horOrderId");
            return (Criteria) this;
        }

        public Criteria andHorOrderIdGreaterThan(String value) {
            addCriterion("HOR_ORDER_ID >", value, "horOrderId");
            return (Criteria) this;
        }

        public Criteria andHorOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("HOR_ORDER_ID >=", value, "horOrderId");
            return (Criteria) this;
        }

        public Criteria andHorOrderIdLessThan(String value) {
            addCriterion("HOR_ORDER_ID <", value, "horOrderId");
            return (Criteria) this;
        }

        public Criteria andHorOrderIdLessThanOrEqualTo(String value) {
            addCriterion("HOR_ORDER_ID <=", value, "horOrderId");
            return (Criteria) this;
        }

        public Criteria andHorOrderIdLike(String value) {
            addCriterion("HOR_ORDER_ID like", value, "horOrderId");
            return (Criteria) this;
        }

        public Criteria andHorOrderIdNotLike(String value) {
            addCriterion("HOR_ORDER_ID not like", value, "horOrderId");
            return (Criteria) this;
        }

        public Criteria andHorOrderIdIn(List<String> values) {
            addCriterion("HOR_ORDER_ID in", values, "horOrderId");
            return (Criteria) this;
        }

        public Criteria andHorOrderIdNotIn(List<String> values) {
            addCriterion("HOR_ORDER_ID not in", values, "horOrderId");
            return (Criteria) this;
        }

        public Criteria andHorOrderIdBetween(String value1, String value2) {
            addCriterion("HOR_ORDER_ID between", value1, value2, "horOrderId");
            return (Criteria) this;
        }

        public Criteria andHorOrderIdNotBetween(String value1, String value2) {
            addCriterion("HOR_ORDER_ID not between", value1, value2, "horOrderId");
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

        public Criteria andPrintInfoIsNull() {
            addCriterion("PRINT_INFO is null");
            return (Criteria) this;
        }

        public Criteria andPrintInfoIsNotNull() {
            addCriterion("PRINT_INFO is not null");
            return (Criteria) this;
        }

        public Criteria andPrintInfoEqualTo(String value) {
            addCriterion("PRINT_INFO =", value, "printInfo");
            return (Criteria) this;
        }

        public Criteria andPrintInfoNotEqualTo(String value) {
            addCriterion("PRINT_INFO <>", value, "printInfo");
            return (Criteria) this;
        }

        public Criteria andPrintInfoGreaterThan(String value) {
            addCriterion("PRINT_INFO >", value, "printInfo");
            return (Criteria) this;
        }

        public Criteria andPrintInfoGreaterThanOrEqualTo(String value) {
            addCriterion("PRINT_INFO >=", value, "printInfo");
            return (Criteria) this;
        }

        public Criteria andPrintInfoLessThan(String value) {
            addCriterion("PRINT_INFO <", value, "printInfo");
            return (Criteria) this;
        }

        public Criteria andPrintInfoLessThanOrEqualTo(String value) {
            addCriterion("PRINT_INFO <=", value, "printInfo");
            return (Criteria) this;
        }

        public Criteria andPrintInfoLike(String value) {
            addCriterion("PRINT_INFO like", value, "printInfo");
            return (Criteria) this;
        }

        public Criteria andPrintInfoNotLike(String value) {
            addCriterion("PRINT_INFO not like", value, "printInfo");
            return (Criteria) this;
        }

        public Criteria andPrintInfoIn(List<String> values) {
            addCriterion("PRINT_INFO in", values, "printInfo");
            return (Criteria) this;
        }

        public Criteria andPrintInfoNotIn(List<String> values) {
            addCriterion("PRINT_INFO not in", values, "printInfo");
            return (Criteria) this;
        }

        public Criteria andPrintInfoBetween(String value1, String value2) {
            addCriterion("PRINT_INFO between", value1, value2, "printInfo");
            return (Criteria) this;
        }

        public Criteria andPrintInfoNotBetween(String value1, String value2) {
            addCriterion("PRINT_INFO not between", value1, value2, "printInfo");
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