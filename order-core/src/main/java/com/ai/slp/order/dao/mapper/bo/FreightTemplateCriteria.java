package com.ai.slp.order.dao.mapper.bo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class FreightTemplateCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public FreightTemplateCriteria() {
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

        public Criteria andTemplateNameIsNull() {
            addCriterion("TEMPLATE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andTemplateNameIsNotNull() {
            addCriterion("TEMPLATE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateNameEqualTo(String value) {
            addCriterion("TEMPLATE_NAME =", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameNotEqualTo(String value) {
            addCriterion("TEMPLATE_NAME <>", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameGreaterThan(String value) {
            addCriterion("TEMPLATE_NAME >", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameGreaterThanOrEqualTo(String value) {
            addCriterion("TEMPLATE_NAME >=", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameLessThan(String value) {
            addCriterion("TEMPLATE_NAME <", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameLessThanOrEqualTo(String value) {
            addCriterion("TEMPLATE_NAME <=", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameLike(String value) {
            addCriterion("TEMPLATE_NAME like", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameNotLike(String value) {
            addCriterion("TEMPLATE_NAME not like", value, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameIn(List<String> values) {
            addCriterion("TEMPLATE_NAME in", values, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameNotIn(List<String> values) {
            addCriterion("TEMPLATE_NAME not in", values, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameBetween(String value1, String value2) {
            addCriterion("TEMPLATE_NAME between", value1, value2, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateNameNotBetween(String value1, String value2) {
            addCriterion("TEMPLATE_NAME not between", value1, value2, "templateName");
            return (Criteria) this;
        }

        public Criteria andTemplateTypeIsNull() {
            addCriterion("TEMPLATE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTemplateTypeIsNotNull() {
            addCriterion("TEMPLATE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateTypeEqualTo(String value) {
            addCriterion("TEMPLATE_TYPE =", value, "templateType");
            return (Criteria) this;
        }

        public Criteria andTemplateTypeNotEqualTo(String value) {
            addCriterion("TEMPLATE_TYPE <>", value, "templateType");
            return (Criteria) this;
        }

        public Criteria andTemplateTypeGreaterThan(String value) {
            addCriterion("TEMPLATE_TYPE >", value, "templateType");
            return (Criteria) this;
        }

        public Criteria andTemplateTypeGreaterThanOrEqualTo(String value) {
            addCriterion("TEMPLATE_TYPE >=", value, "templateType");
            return (Criteria) this;
        }

        public Criteria andTemplateTypeLessThan(String value) {
            addCriterion("TEMPLATE_TYPE <", value, "templateType");
            return (Criteria) this;
        }

        public Criteria andTemplateTypeLessThanOrEqualTo(String value) {
            addCriterion("TEMPLATE_TYPE <=", value, "templateType");
            return (Criteria) this;
        }

        public Criteria andTemplateTypeLike(String value) {
            addCriterion("TEMPLATE_TYPE like", value, "templateType");
            return (Criteria) this;
        }

        public Criteria andTemplateTypeNotLike(String value) {
            addCriterion("TEMPLATE_TYPE not like", value, "templateType");
            return (Criteria) this;
        }

        public Criteria andTemplateTypeIn(List<String> values) {
            addCriterion("TEMPLATE_TYPE in", values, "templateType");
            return (Criteria) this;
        }

        public Criteria andTemplateTypeNotIn(List<String> values) {
            addCriterion("TEMPLATE_TYPE not in", values, "templateType");
            return (Criteria) this;
        }

        public Criteria andTemplateTypeBetween(String value1, String value2) {
            addCriterion("TEMPLATE_TYPE between", value1, value2, "templateType");
            return (Criteria) this;
        }

        public Criteria andTemplateTypeNotBetween(String value1, String value2) {
            addCriterion("TEMPLATE_TYPE not between", value1, value2, "templateType");
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

        public Criteria andLogisticsCompanyIdIsNull() {
            addCriterion("LOGISTICS_COMPANY_ID is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsCompanyIdIsNotNull() {
            addCriterion("LOGISTICS_COMPANY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsCompanyIdEqualTo(String value) {
            addCriterion("LOGISTICS_COMPANY_ID =", value, "logisticsCompanyId");
            return (Criteria) this;
        }

        public Criteria andLogisticsCompanyIdNotEqualTo(String value) {
            addCriterion("LOGISTICS_COMPANY_ID <>", value, "logisticsCompanyId");
            return (Criteria) this;
        }

        public Criteria andLogisticsCompanyIdGreaterThan(String value) {
            addCriterion("LOGISTICS_COMPANY_ID >", value, "logisticsCompanyId");
            return (Criteria) this;
        }

        public Criteria andLogisticsCompanyIdGreaterThanOrEqualTo(String value) {
            addCriterion("LOGISTICS_COMPANY_ID >=", value, "logisticsCompanyId");
            return (Criteria) this;
        }

        public Criteria andLogisticsCompanyIdLessThan(String value) {
            addCriterion("LOGISTICS_COMPANY_ID <", value, "logisticsCompanyId");
            return (Criteria) this;
        }

        public Criteria andLogisticsCompanyIdLessThanOrEqualTo(String value) {
            addCriterion("LOGISTICS_COMPANY_ID <=", value, "logisticsCompanyId");
            return (Criteria) this;
        }

        public Criteria andLogisticsCompanyIdLike(String value) {
            addCriterion("LOGISTICS_COMPANY_ID like", value, "logisticsCompanyId");
            return (Criteria) this;
        }

        public Criteria andLogisticsCompanyIdNotLike(String value) {
            addCriterion("LOGISTICS_COMPANY_ID not like", value, "logisticsCompanyId");
            return (Criteria) this;
        }

        public Criteria andLogisticsCompanyIdIn(List<String> values) {
            addCriterion("LOGISTICS_COMPANY_ID in", values, "logisticsCompanyId");
            return (Criteria) this;
        }

        public Criteria andLogisticsCompanyIdNotIn(List<String> values) {
            addCriterion("LOGISTICS_COMPANY_ID not in", values, "logisticsCompanyId");
            return (Criteria) this;
        }

        public Criteria andLogisticsCompanyIdBetween(String value1, String value2) {
            addCriterion("LOGISTICS_COMPANY_ID between", value1, value2, "logisticsCompanyId");
            return (Criteria) this;
        }

        public Criteria andLogisticsCompanyIdNotBetween(String value1, String value2) {
            addCriterion("LOGISTICS_COMPANY_ID not between", value1, value2, "logisticsCompanyId");
            return (Criteria) this;
        }

        public Criteria andIsFreeIsNull() {
            addCriterion("IS_FREE is null");
            return (Criteria) this;
        }

        public Criteria andIsFreeIsNotNull() {
            addCriterion("IS_FREE is not null");
            return (Criteria) this;
        }

        public Criteria andIsFreeEqualTo(String value) {
            addCriterion("IS_FREE =", value, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeNotEqualTo(String value) {
            addCriterion("IS_FREE <>", value, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeGreaterThan(String value) {
            addCriterion("IS_FREE >", value, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeGreaterThanOrEqualTo(String value) {
            addCriterion("IS_FREE >=", value, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeLessThan(String value) {
            addCriterion("IS_FREE <", value, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeLessThanOrEqualTo(String value) {
            addCriterion("IS_FREE <=", value, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeLike(String value) {
            addCriterion("IS_FREE like", value, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeNotLike(String value) {
            addCriterion("IS_FREE not like", value, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeIn(List<String> values) {
            addCriterion("IS_FREE in", values, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeNotIn(List<String> values) {
            addCriterion("IS_FREE not in", values, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeBetween(String value1, String value2) {
            addCriterion("IS_FREE between", value1, value2, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsFreeNotBetween(String value1, String value2) {
            addCriterion("IS_FREE not between", value1, value2, "isFree");
            return (Criteria) this;
        }

        public Criteria andIsTermFreeIsNull() {
            addCriterion("IS_TERM_FREE is null");
            return (Criteria) this;
        }

        public Criteria andIsTermFreeIsNotNull() {
            addCriterion("IS_TERM_FREE is not null");
            return (Criteria) this;
        }

        public Criteria andIsTermFreeEqualTo(String value) {
            addCriterion("IS_TERM_FREE =", value, "isTermFree");
            return (Criteria) this;
        }

        public Criteria andIsTermFreeNotEqualTo(String value) {
            addCriterion("IS_TERM_FREE <>", value, "isTermFree");
            return (Criteria) this;
        }

        public Criteria andIsTermFreeGreaterThan(String value) {
            addCriterion("IS_TERM_FREE >", value, "isTermFree");
            return (Criteria) this;
        }

        public Criteria andIsTermFreeGreaterThanOrEqualTo(String value) {
            addCriterion("IS_TERM_FREE >=", value, "isTermFree");
            return (Criteria) this;
        }

        public Criteria andIsTermFreeLessThan(String value) {
            addCriterion("IS_TERM_FREE <", value, "isTermFree");
            return (Criteria) this;
        }

        public Criteria andIsTermFreeLessThanOrEqualTo(String value) {
            addCriterion("IS_TERM_FREE <=", value, "isTermFree");
            return (Criteria) this;
        }

        public Criteria andIsTermFreeLike(String value) {
            addCriterion("IS_TERM_FREE like", value, "isTermFree");
            return (Criteria) this;
        }

        public Criteria andIsTermFreeNotLike(String value) {
            addCriterion("IS_TERM_FREE not like", value, "isTermFree");
            return (Criteria) this;
        }

        public Criteria andIsTermFreeIn(List<String> values) {
            addCriterion("IS_TERM_FREE in", values, "isTermFree");
            return (Criteria) this;
        }

        public Criteria andIsTermFreeNotIn(List<String> values) {
            addCriterion("IS_TERM_FREE not in", values, "isTermFree");
            return (Criteria) this;
        }

        public Criteria andIsTermFreeBetween(String value1, String value2) {
            addCriterion("IS_TERM_FREE between", value1, value2, "isTermFree");
            return (Criteria) this;
        }

        public Criteria andIsTermFreeNotBetween(String value1, String value2) {
            addCriterion("IS_TERM_FREE not between", value1, value2, "isTermFree");
            return (Criteria) this;
        }

        public Criteria andValuationTypeIsNull() {
            addCriterion("VALUATION_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andValuationTypeIsNotNull() {
            addCriterion("VALUATION_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andValuationTypeEqualTo(String value) {
            addCriterion("VALUATION_TYPE =", value, "valuationType");
            return (Criteria) this;
        }

        public Criteria andValuationTypeNotEqualTo(String value) {
            addCriterion("VALUATION_TYPE <>", value, "valuationType");
            return (Criteria) this;
        }

        public Criteria andValuationTypeGreaterThan(String value) {
            addCriterion("VALUATION_TYPE >", value, "valuationType");
            return (Criteria) this;
        }

        public Criteria andValuationTypeGreaterThanOrEqualTo(String value) {
            addCriterion("VALUATION_TYPE >=", value, "valuationType");
            return (Criteria) this;
        }

        public Criteria andValuationTypeLessThan(String value) {
            addCriterion("VALUATION_TYPE <", value, "valuationType");
            return (Criteria) this;
        }

        public Criteria andValuationTypeLessThanOrEqualTo(String value) {
            addCriterion("VALUATION_TYPE <=", value, "valuationType");
            return (Criteria) this;
        }

        public Criteria andValuationTypeLike(String value) {
            addCriterion("VALUATION_TYPE like", value, "valuationType");
            return (Criteria) this;
        }

        public Criteria andValuationTypeNotLike(String value) {
            addCriterion("VALUATION_TYPE not like", value, "valuationType");
            return (Criteria) this;
        }

        public Criteria andValuationTypeIn(List<String> values) {
            addCriterion("VALUATION_TYPE in", values, "valuationType");
            return (Criteria) this;
        }

        public Criteria andValuationTypeNotIn(List<String> values) {
            addCriterion("VALUATION_TYPE not in", values, "valuationType");
            return (Criteria) this;
        }

        public Criteria andValuationTypeBetween(String value1, String value2) {
            addCriterion("VALUATION_TYPE between", value1, value2, "valuationType");
            return (Criteria) this;
        }

        public Criteria andValuationTypeNotBetween(String value1, String value2) {
            addCriterion("VALUATION_TYPE not between", value1, value2, "valuationType");
            return (Criteria) this;
        }

        public Criteria andTimeIsNull() {
            addCriterion("TIME is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("TIME is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(Timestamp value) {
            addCriterion("TIME =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(Timestamp value) {
            addCriterion("TIME <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(Timestamp value) {
            addCriterion("TIME >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(Timestamp value) {
            addCriterion("TIME >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(Timestamp value) {
            addCriterion("TIME <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(Timestamp value) {
            addCriterion("TIME <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<Timestamp> values) {
            addCriterion("TIME in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<Timestamp> values) {
            addCriterion("TIME not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(Timestamp value1, Timestamp value2) {
            addCriterion("TIME between", value1, value2, "time");
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