package com.ai.slp.order.dao.mapper.bo;

import java.util.ArrayList;
import java.util.List;

public class FreightTemplateProdCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public FreightTemplateProdCriteria() {
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

        public Criteria andRegionIdIsNull() {
            addCriterion("REGION_ID is null");
            return (Criteria) this;
        }

        public Criteria andRegionIdIsNotNull() {
            addCriterion("REGION_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRegionIdEqualTo(String value) {
            addCriterion("REGION_ID =", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdNotEqualTo(String value) {
            addCriterion("REGION_ID <>", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdGreaterThan(String value) {
            addCriterion("REGION_ID >", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdGreaterThanOrEqualTo(String value) {
            addCriterion("REGION_ID >=", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdLessThan(String value) {
            addCriterion("REGION_ID <", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdLessThanOrEqualTo(String value) {
            addCriterion("REGION_ID <=", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdLike(String value) {
            addCriterion("REGION_ID like", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdNotLike(String value) {
            addCriterion("REGION_ID not like", value, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdIn(List<String> values) {
            addCriterion("REGION_ID in", values, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdNotIn(List<String> values) {
            addCriterion("REGION_ID not in", values, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdBetween(String value1, String value2) {
            addCriterion("REGION_ID between", value1, value2, "regionId");
            return (Criteria) this;
        }

        public Criteria andRegionIdNotBetween(String value1, String value2) {
            addCriterion("REGION_ID not between", value1, value2, "regionId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNull() {
            addCriterion("TEMPLATE_ID is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNotNull() {
            addCriterion("TEMPLATE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdEqualTo(String value) {
            addCriterion("TEMPLATE_ID =", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotEqualTo(String value) {
            addCriterion("TEMPLATE_ID <>", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThan(String value) {
            addCriterion("TEMPLATE_ID >", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(String value) {
            addCriterion("TEMPLATE_ID >=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThan(String value) {
            addCriterion("TEMPLATE_ID <", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(String value) {
            addCriterion("TEMPLATE_ID <=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLike(String value) {
            addCriterion("TEMPLATE_ID like", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotLike(String value) {
            addCriterion("TEMPLATE_ID not like", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIn(List<String> values) {
            addCriterion("TEMPLATE_ID in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotIn(List<String> values) {
            addCriterion("TEMPLATE_ID not in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdBetween(String value1, String value2) {
            addCriterion("TEMPLATE_ID between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotBetween(String value1, String value2) {
            addCriterion("TEMPLATE_ID not between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTransportAddressIsNull() {
            addCriterion("TRANSPORT_ADDRESS is null");
            return (Criteria) this;
        }

        public Criteria andTransportAddressIsNotNull() {
            addCriterion("TRANSPORT_ADDRESS is not null");
            return (Criteria) this;
        }

        public Criteria andTransportAddressEqualTo(String value) {
            addCriterion("TRANSPORT_ADDRESS =", value, "transportAddress");
            return (Criteria) this;
        }

        public Criteria andTransportAddressNotEqualTo(String value) {
            addCriterion("TRANSPORT_ADDRESS <>", value, "transportAddress");
            return (Criteria) this;
        }

        public Criteria andTransportAddressGreaterThan(String value) {
            addCriterion("TRANSPORT_ADDRESS >", value, "transportAddress");
            return (Criteria) this;
        }

        public Criteria andTransportAddressGreaterThanOrEqualTo(String value) {
            addCriterion("TRANSPORT_ADDRESS >=", value, "transportAddress");
            return (Criteria) this;
        }

        public Criteria andTransportAddressLessThan(String value) {
            addCriterion("TRANSPORT_ADDRESS <", value, "transportAddress");
            return (Criteria) this;
        }

        public Criteria andTransportAddressLessThanOrEqualTo(String value) {
            addCriterion("TRANSPORT_ADDRESS <=", value, "transportAddress");
            return (Criteria) this;
        }

        public Criteria andTransportAddressLike(String value) {
            addCriterion("TRANSPORT_ADDRESS like", value, "transportAddress");
            return (Criteria) this;
        }

        public Criteria andTransportAddressNotLike(String value) {
            addCriterion("TRANSPORT_ADDRESS not like", value, "transportAddress");
            return (Criteria) this;
        }

        public Criteria andTransportAddressIn(List<String> values) {
            addCriterion("TRANSPORT_ADDRESS in", values, "transportAddress");
            return (Criteria) this;
        }

        public Criteria andTransportAddressNotIn(List<String> values) {
            addCriterion("TRANSPORT_ADDRESS not in", values, "transportAddress");
            return (Criteria) this;
        }

        public Criteria andTransportAddressBetween(String value1, String value2) {
            addCriterion("TRANSPORT_ADDRESS between", value1, value2, "transportAddress");
            return (Criteria) this;
        }

        public Criteria andTransportAddressNotBetween(String value1, String value2) {
            addCriterion("TRANSPORT_ADDRESS not between", value1, value2, "transportAddress");
            return (Criteria) this;
        }

        public Criteria andFirstNumberIsNull() {
            addCriterion("FIRST_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andFirstNumberIsNotNull() {
            addCriterion("FIRST_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andFirstNumberEqualTo(long value) {
            addCriterion("FIRST_NUMBER =", value, "firstNumber");
            return (Criteria) this;
        }

        public Criteria andFirstNumberNotEqualTo(long value) {
            addCriterion("FIRST_NUMBER <>", value, "firstNumber");
            return (Criteria) this;
        }

        public Criteria andFirstNumberGreaterThan(long value) {
            addCriterion("FIRST_NUMBER >", value, "firstNumber");
            return (Criteria) this;
        }

        public Criteria andFirstNumberGreaterThanOrEqualTo(long value) {
            addCriterion("FIRST_NUMBER >=", value, "firstNumber");
            return (Criteria) this;
        }

        public Criteria andFirstNumberLessThan(long value) {
            addCriterion("FIRST_NUMBER <", value, "firstNumber");
            return (Criteria) this;
        }

        public Criteria andFirstNumberLessThanOrEqualTo(long value) {
            addCriterion("FIRST_NUMBER <=", value, "firstNumber");
            return (Criteria) this;
        }

        public Criteria andFirstNumberIn(List<Long> values) {
            addCriterion("FIRST_NUMBER in", values, "firstNumber");
            return (Criteria) this;
        }

        public Criteria andFirstNumberNotIn(List<Long> values) {
            addCriterion("FIRST_NUMBER not in", values, "firstNumber");
            return (Criteria) this;
        }

        public Criteria andFirstNumberBetween(long value1, long value2) {
            addCriterion("FIRST_NUMBER between", value1, value2, "firstNumber");
            return (Criteria) this;
        }

        public Criteria andFirstNumberNotBetween(long value1, long value2) {
            addCriterion("FIRST_NUMBER not between", value1, value2, "firstNumber");
            return (Criteria) this;
        }

        public Criteria andFirstNumIsNull() {
            addCriterion("FIRST_NUM is null");
            return (Criteria) this;
        }

        public Criteria andFirstNumIsNotNull() {
            addCriterion("FIRST_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andFirstNumEqualTo(long value) {
            addCriterion("FIRST_NUM =", value, "firstNum");
            return (Criteria) this;
        }

        public Criteria andFirstNumNotEqualTo(long value) {
            addCriterion("FIRST_NUM <>", value, "firstNum");
            return (Criteria) this;
        }

        public Criteria andFirstNumGreaterThan(long value) {
            addCriterion("FIRST_NUM >", value, "firstNum");
            return (Criteria) this;
        }

        public Criteria andFirstNumGreaterThanOrEqualTo(long value) {
            addCriterion("FIRST_NUM >=", value, "firstNum");
            return (Criteria) this;
        }

        public Criteria andFirstNumLessThan(long value) {
            addCriterion("FIRST_NUM <", value, "firstNum");
            return (Criteria) this;
        }

        public Criteria andFirstNumLessThanOrEqualTo(long value) {
            addCriterion("FIRST_NUM <=", value, "firstNum");
            return (Criteria) this;
        }

        public Criteria andFirstNumIn(List<Long> values) {
            addCriterion("FIRST_NUM in", values, "firstNum");
            return (Criteria) this;
        }

        public Criteria andFirstNumNotIn(List<Long> values) {
            addCriterion("FIRST_NUM not in", values, "firstNum");
            return (Criteria) this;
        }

        public Criteria andFirstNumBetween(long value1, long value2) {
            addCriterion("FIRST_NUM between", value1, value2, "firstNum");
            return (Criteria) this;
        }

        public Criteria andFirstNumNotBetween(long value1, long value2) {
            addCriterion("FIRST_NUM not between", value1, value2, "firstNum");
            return (Criteria) this;
        }

        public Criteria andPieceNumberIsNull() {
            addCriterion("PIECE_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andPieceNumberIsNotNull() {
            addCriterion("PIECE_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andPieceNumberEqualTo(long value) {
            addCriterion("PIECE_NUMBER =", value, "pieceNumber");
            return (Criteria) this;
        }

        public Criteria andPieceNumberNotEqualTo(long value) {
            addCriterion("PIECE_NUMBER <>", value, "pieceNumber");
            return (Criteria) this;
        }

        public Criteria andPieceNumberGreaterThan(long value) {
            addCriterion("PIECE_NUMBER >", value, "pieceNumber");
            return (Criteria) this;
        }

        public Criteria andPieceNumberGreaterThanOrEqualTo(long value) {
            addCriterion("PIECE_NUMBER >=", value, "pieceNumber");
            return (Criteria) this;
        }

        public Criteria andPieceNumberLessThan(long value) {
            addCriterion("PIECE_NUMBER <", value, "pieceNumber");
            return (Criteria) this;
        }

        public Criteria andPieceNumberLessThanOrEqualTo(long value) {
            addCriterion("PIECE_NUMBER <=", value, "pieceNumber");
            return (Criteria) this;
        }

        public Criteria andPieceNumberIn(List<Long> values) {
            addCriterion("PIECE_NUMBER in", values, "pieceNumber");
            return (Criteria) this;
        }

        public Criteria andPieceNumberNotIn(List<Long> values) {
            addCriterion("PIECE_NUMBER not in", values, "pieceNumber");
            return (Criteria) this;
        }

        public Criteria andPieceNumberBetween(long value1, long value2) {
            addCriterion("PIECE_NUMBER between", value1, value2, "pieceNumber");
            return (Criteria) this;
        }

        public Criteria andPieceNumberNotBetween(long value1, long value2) {
            addCriterion("PIECE_NUMBER not between", value1, value2, "pieceNumber");
            return (Criteria) this;
        }

        public Criteria andPieceNumIsNull() {
            addCriterion("PIECE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andPieceNumIsNotNull() {
            addCriterion("PIECE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andPieceNumEqualTo(long value) {
            addCriterion("PIECE_NUM =", value, "pieceNum");
            return (Criteria) this;
        }

        public Criteria andPieceNumNotEqualTo(long value) {
            addCriterion("PIECE_NUM <>", value, "pieceNum");
            return (Criteria) this;
        }

        public Criteria andPieceNumGreaterThan(long value) {
            addCriterion("PIECE_NUM >", value, "pieceNum");
            return (Criteria) this;
        }

        public Criteria andPieceNumGreaterThanOrEqualTo(long value) {
            addCriterion("PIECE_NUM >=", value, "pieceNum");
            return (Criteria) this;
        }

        public Criteria andPieceNumLessThan(long value) {
            addCriterion("PIECE_NUM <", value, "pieceNum");
            return (Criteria) this;
        }

        public Criteria andPieceNumLessThanOrEqualTo(long value) {
            addCriterion("PIECE_NUM <=", value, "pieceNum");
            return (Criteria) this;
        }

        public Criteria andPieceNumIn(List<Long> values) {
            addCriterion("PIECE_NUM in", values, "pieceNum");
            return (Criteria) this;
        }

        public Criteria andPieceNumNotIn(List<Long> values) {
            addCriterion("PIECE_NUM not in", values, "pieceNum");
            return (Criteria) this;
        }

        public Criteria andPieceNumBetween(long value1, long value2) {
            addCriterion("PIECE_NUM between", value1, value2, "pieceNum");
            return (Criteria) this;
        }

        public Criteria andPieceNumNotBetween(long value1, long value2) {
            addCriterion("PIECE_NUM not between", value1, value2, "pieceNum");
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