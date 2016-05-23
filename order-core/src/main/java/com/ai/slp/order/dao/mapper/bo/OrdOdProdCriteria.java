package com.ai.slp.order.dao.mapper.bo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrdOdProdCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public OrdOdProdCriteria() {
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

        public Criteria andProdTypeIsNull() {
            addCriterion("PROD_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andProdTypeIsNotNull() {
            addCriterion("PROD_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andProdTypeEqualTo(String value) {
            addCriterion("PROD_TYPE =", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeNotEqualTo(String value) {
            addCriterion("PROD_TYPE <>", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeGreaterThan(String value) {
            addCriterion("PROD_TYPE >", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PROD_TYPE >=", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeLessThan(String value) {
            addCriterion("PROD_TYPE <", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeLessThanOrEqualTo(String value) {
            addCriterion("PROD_TYPE <=", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeLike(String value) {
            addCriterion("PROD_TYPE like", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeNotLike(String value) {
            addCriterion("PROD_TYPE not like", value, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeIn(List<String> values) {
            addCriterion("PROD_TYPE in", values, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeNotIn(List<String> values) {
            addCriterion("PROD_TYPE not in", values, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeBetween(String value1, String value2) {
            addCriterion("PROD_TYPE between", value1, value2, "prodType");
            return (Criteria) this;
        }

        public Criteria andProdTypeNotBetween(String value1, String value2) {
            addCriterion("PROD_TYPE not between", value1, value2, "prodType");
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

        public Criteria andSupplierIdEqualTo(long value) {
            addCriterion("SUPPLIER_ID =", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotEqualTo(long value) {
            addCriterion("SUPPLIER_ID <>", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThan(long value) {
            addCriterion("SUPPLIER_ID >", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThanOrEqualTo(long value) {
            addCriterion("SUPPLIER_ID >=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThan(long value) {
            addCriterion("SUPPLIER_ID <", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThanOrEqualTo(long value) {
            addCriterion("SUPPLIER_ID <=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIn(List<Long> values) {
            addCriterion("SUPPLIER_ID in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotIn(List<Long> values) {
            addCriterion("SUPPLIER_ID not in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdBetween(long value1, long value2) {
            addCriterion("SUPPLIER_ID between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotBetween(long value1, long value2) {
            addCriterion("SUPPLIER_ID not between", value1, value2, "supplierId");
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

        public Criteria andProdNameIsNull() {
            addCriterion("PROD_NAME is null");
            return (Criteria) this;
        }

        public Criteria andProdNameIsNotNull() {
            addCriterion("PROD_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andProdNameEqualTo(String value) {
            addCriterion("PROD_NAME =", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameNotEqualTo(String value) {
            addCriterion("PROD_NAME <>", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameGreaterThan(String value) {
            addCriterion("PROD_NAME >", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameGreaterThanOrEqualTo(String value) {
            addCriterion("PROD_NAME >=", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameLessThan(String value) {
            addCriterion("PROD_NAME <", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameLessThanOrEqualTo(String value) {
            addCriterion("PROD_NAME <=", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameLike(String value) {
            addCriterion("PROD_NAME like", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameNotLike(String value) {
            addCriterion("PROD_NAME not like", value, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameIn(List<String> values) {
            addCriterion("PROD_NAME in", values, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameNotIn(List<String> values) {
            addCriterion("PROD_NAME not in", values, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameBetween(String value1, String value2) {
            addCriterion("PROD_NAME between", value1, value2, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdNameNotBetween(String value1, String value2) {
            addCriterion("PROD_NAME not between", value1, value2, "prodName");
            return (Criteria) this;
        }

        public Criteria andProdSnIsNull() {
            addCriterion("PROD_SN is null");
            return (Criteria) this;
        }

        public Criteria andProdSnIsNotNull() {
            addCriterion("PROD_SN is not null");
            return (Criteria) this;
        }

        public Criteria andProdSnEqualTo(String value) {
            addCriterion("PROD_SN =", value, "prodSn");
            return (Criteria) this;
        }

        public Criteria andProdSnNotEqualTo(String value) {
            addCriterion("PROD_SN <>", value, "prodSn");
            return (Criteria) this;
        }

        public Criteria andProdSnGreaterThan(String value) {
            addCriterion("PROD_SN >", value, "prodSn");
            return (Criteria) this;
        }

        public Criteria andProdSnGreaterThanOrEqualTo(String value) {
            addCriterion("PROD_SN >=", value, "prodSn");
            return (Criteria) this;
        }

        public Criteria andProdSnLessThan(String value) {
            addCriterion("PROD_SN <", value, "prodSn");
            return (Criteria) this;
        }

        public Criteria andProdSnLessThanOrEqualTo(String value) {
            addCriterion("PROD_SN <=", value, "prodSn");
            return (Criteria) this;
        }

        public Criteria andProdSnLike(String value) {
            addCriterion("PROD_SN like", value, "prodSn");
            return (Criteria) this;
        }

        public Criteria andProdSnNotLike(String value) {
            addCriterion("PROD_SN not like", value, "prodSn");
            return (Criteria) this;
        }

        public Criteria andProdSnIn(List<String> values) {
            addCriterion("PROD_SN in", values, "prodSn");
            return (Criteria) this;
        }

        public Criteria andProdSnNotIn(List<String> values) {
            addCriterion("PROD_SN not in", values, "prodSn");
            return (Criteria) this;
        }

        public Criteria andProdSnBetween(String value1, String value2) {
            addCriterion("PROD_SN between", value1, value2, "prodSn");
            return (Criteria) this;
        }

        public Criteria andProdSnNotBetween(String value1, String value2) {
            addCriterion("PROD_SN not between", value1, value2, "prodSn");
            return (Criteria) this;
        }

        public Criteria andSkuIdIsNull() {
            addCriterion("SKU_ID is null");
            return (Criteria) this;
        }

        public Criteria andSkuIdIsNotNull() {
            addCriterion("SKU_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSkuIdEqualTo(String value) {
            addCriterion("SKU_ID =", value, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdNotEqualTo(String value) {
            addCriterion("SKU_ID <>", value, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdGreaterThan(String value) {
            addCriterion("SKU_ID >", value, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdGreaterThanOrEqualTo(String value) {
            addCriterion("SKU_ID >=", value, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdLessThan(String value) {
            addCriterion("SKU_ID <", value, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdLessThanOrEqualTo(String value) {
            addCriterion("SKU_ID <=", value, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdLike(String value) {
            addCriterion("SKU_ID like", value, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdNotLike(String value) {
            addCriterion("SKU_ID not like", value, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdIn(List<String> values) {
            addCriterion("SKU_ID in", values, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdNotIn(List<String> values) {
            addCriterion("SKU_ID not in", values, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdBetween(String value1, String value2) {
            addCriterion("SKU_ID between", value1, value2, "skuId");
            return (Criteria) this;
        }

        public Criteria andSkuIdNotBetween(String value1, String value2) {
            addCriterion("SKU_ID not between", value1, value2, "skuId");
            return (Criteria) this;
        }

        public Criteria andStandardProdIdIsNull() {
            addCriterion("STANDARD_PROD_ID is null");
            return (Criteria) this;
        }

        public Criteria andStandardProdIdIsNotNull() {
            addCriterion("STANDARD_PROD_ID is not null");
            return (Criteria) this;
        }

        public Criteria andStandardProdIdEqualTo(String value) {
            addCriterion("STANDARD_PROD_ID =", value, "standardProdId");
            return (Criteria) this;
        }

        public Criteria andStandardProdIdNotEqualTo(String value) {
            addCriterion("STANDARD_PROD_ID <>", value, "standardProdId");
            return (Criteria) this;
        }

        public Criteria andStandardProdIdGreaterThan(String value) {
            addCriterion("STANDARD_PROD_ID >", value, "standardProdId");
            return (Criteria) this;
        }

        public Criteria andStandardProdIdGreaterThanOrEqualTo(String value) {
            addCriterion("STANDARD_PROD_ID >=", value, "standardProdId");
            return (Criteria) this;
        }

        public Criteria andStandardProdIdLessThan(String value) {
            addCriterion("STANDARD_PROD_ID <", value, "standardProdId");
            return (Criteria) this;
        }

        public Criteria andStandardProdIdLessThanOrEqualTo(String value) {
            addCriterion("STANDARD_PROD_ID <=", value, "standardProdId");
            return (Criteria) this;
        }

        public Criteria andStandardProdIdLike(String value) {
            addCriterion("STANDARD_PROD_ID like", value, "standardProdId");
            return (Criteria) this;
        }

        public Criteria andStandardProdIdNotLike(String value) {
            addCriterion("STANDARD_PROD_ID not like", value, "standardProdId");
            return (Criteria) this;
        }

        public Criteria andStandardProdIdIn(List<String> values) {
            addCriterion("STANDARD_PROD_ID in", values, "standardProdId");
            return (Criteria) this;
        }

        public Criteria andStandardProdIdNotIn(List<String> values) {
            addCriterion("STANDARD_PROD_ID not in", values, "standardProdId");
            return (Criteria) this;
        }

        public Criteria andStandardProdIdBetween(String value1, String value2) {
            addCriterion("STANDARD_PROD_ID between", value1, value2, "standardProdId");
            return (Criteria) this;
        }

        public Criteria andStandardProdIdNotBetween(String value1, String value2) {
            addCriterion("STANDARD_PROD_ID not between", value1, value2, "standardProdId");
            return (Criteria) this;
        }

        public Criteria andSupplyIdIsNull() {
            addCriterion("SUPPLY_ID is null");
            return (Criteria) this;
        }

        public Criteria andSupplyIdIsNotNull() {
            addCriterion("SUPPLY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyIdEqualTo(String value) {
            addCriterion("SUPPLY_ID =", value, "supplyId");
            return (Criteria) this;
        }

        public Criteria andSupplyIdNotEqualTo(String value) {
            addCriterion("SUPPLY_ID <>", value, "supplyId");
            return (Criteria) this;
        }

        public Criteria andSupplyIdGreaterThan(String value) {
            addCriterion("SUPPLY_ID >", value, "supplyId");
            return (Criteria) this;
        }

        public Criteria andSupplyIdGreaterThanOrEqualTo(String value) {
            addCriterion("SUPPLY_ID >=", value, "supplyId");
            return (Criteria) this;
        }

        public Criteria andSupplyIdLessThan(String value) {
            addCriterion("SUPPLY_ID <", value, "supplyId");
            return (Criteria) this;
        }

        public Criteria andSupplyIdLessThanOrEqualTo(String value) {
            addCriterion("SUPPLY_ID <=", value, "supplyId");
            return (Criteria) this;
        }

        public Criteria andSupplyIdLike(String value) {
            addCriterion("SUPPLY_ID like", value, "supplyId");
            return (Criteria) this;
        }

        public Criteria andSupplyIdNotLike(String value) {
            addCriterion("SUPPLY_ID not like", value, "supplyId");
            return (Criteria) this;
        }

        public Criteria andSupplyIdIn(List<String> values) {
            addCriterion("SUPPLY_ID in", values, "supplyId");
            return (Criteria) this;
        }

        public Criteria andSupplyIdNotIn(List<String> values) {
            addCriterion("SUPPLY_ID not in", values, "supplyId");
            return (Criteria) this;
        }

        public Criteria andSupplyIdBetween(String value1, String value2) {
            addCriterion("SUPPLY_ID between", value1, value2, "supplyId");
            return (Criteria) this;
        }

        public Criteria andSupplyIdNotBetween(String value1, String value2) {
            addCriterion("SUPPLY_ID not between", value1, value2, "supplyId");
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

        public Criteria andValidTimeIsNull() {
            addCriterion("VALID_TIME is null");
            return (Criteria) this;
        }

        public Criteria andValidTimeIsNotNull() {
            addCriterion("VALID_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andValidTimeEqualTo(Timestamp value) {
            addCriterion("VALID_TIME =", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeNotEqualTo(Timestamp value) {
            addCriterion("VALID_TIME <>", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeGreaterThan(Timestamp value) {
            addCriterion("VALID_TIME >", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeGreaterThanOrEqualTo(Timestamp value) {
            addCriterion("VALID_TIME >=", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeLessThan(Timestamp value) {
            addCriterion("VALID_TIME <", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeLessThanOrEqualTo(Timestamp value) {
            addCriterion("VALID_TIME <=", value, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeIn(List<Timestamp> values) {
            addCriterion("VALID_TIME in", values, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeNotIn(List<Timestamp> values) {
            addCriterion("VALID_TIME not in", values, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeBetween(Timestamp value1, Timestamp value2) {
            addCriterion("VALID_TIME between", value1, value2, "validTime");
            return (Criteria) this;
        }

        public Criteria andValidTimeNotBetween(Timestamp value1, Timestamp value2) {
            addCriterion("VALID_TIME not between", value1, value2, "validTime");
            return (Criteria) this;
        }

        public Criteria andInvalidTimeIsNull() {
            addCriterion("INVALID_TIME is null");
            return (Criteria) this;
        }

        public Criteria andInvalidTimeIsNotNull() {
            addCriterion("INVALID_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andInvalidTimeEqualTo(Timestamp value) {
            addCriterion("INVALID_TIME =", value, "invalidTime");
            return (Criteria) this;
        }

        public Criteria andInvalidTimeNotEqualTo(Timestamp value) {
            addCriterion("INVALID_TIME <>", value, "invalidTime");
            return (Criteria) this;
        }

        public Criteria andInvalidTimeGreaterThan(Timestamp value) {
            addCriterion("INVALID_TIME >", value, "invalidTime");
            return (Criteria) this;
        }

        public Criteria andInvalidTimeGreaterThanOrEqualTo(Timestamp value) {
            addCriterion("INVALID_TIME >=", value, "invalidTime");
            return (Criteria) this;
        }

        public Criteria andInvalidTimeLessThan(Timestamp value) {
            addCriterion("INVALID_TIME <", value, "invalidTime");
            return (Criteria) this;
        }

        public Criteria andInvalidTimeLessThanOrEqualTo(Timestamp value) {
            addCriterion("INVALID_TIME <=", value, "invalidTime");
            return (Criteria) this;
        }

        public Criteria andInvalidTimeIn(List<Timestamp> values) {
            addCriterion("INVALID_TIME in", values, "invalidTime");
            return (Criteria) this;
        }

        public Criteria andInvalidTimeNotIn(List<Timestamp> values) {
            addCriterion("INVALID_TIME not in", values, "invalidTime");
            return (Criteria) this;
        }

        public Criteria andInvalidTimeBetween(Timestamp value1, Timestamp value2) {
            addCriterion("INVALID_TIME between", value1, value2, "invalidTime");
            return (Criteria) this;
        }

        public Criteria andInvalidTimeNotBetween(Timestamp value1, Timestamp value2) {
            addCriterion("INVALID_TIME not between", value1, value2, "invalidTime");
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

        public Criteria andBuySumIsNull() {
            addCriterion("BUY_SUM is null");
            return (Criteria) this;
        }

        public Criteria andBuySumIsNotNull() {
            addCriterion("BUY_SUM is not null");
            return (Criteria) this;
        }

        public Criteria andBuySumEqualTo(long value) {
            addCriterion("BUY_SUM =", value, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumNotEqualTo(long value) {
            addCriterion("BUY_SUM <>", value, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumGreaterThan(long value) {
            addCriterion("BUY_SUM >", value, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumGreaterThanOrEqualTo(long value) {
            addCriterion("BUY_SUM >=", value, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumLessThan(long value) {
            addCriterion("BUY_SUM <", value, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumLessThanOrEqualTo(long value) {
            addCriterion("BUY_SUM <=", value, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumIn(List<Long> values) {
            addCriterion("BUY_SUM in", values, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumNotIn(List<Long> values) {
            addCriterion("BUY_SUM not in", values, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumBetween(long value1, long value2) {
            addCriterion("BUY_SUM between", value1, value2, "buySum");
            return (Criteria) this;
        }

        public Criteria andBuySumNotBetween(long value1, long value2) {
            addCriterion("BUY_SUM not between", value1, value2, "buySum");
            return (Criteria) this;
        }

        public Criteria andSalePriceIsNull() {
            addCriterion("SALE_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andSalePriceIsNotNull() {
            addCriterion("SALE_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andSalePriceEqualTo(long value) {
            addCriterion("SALE_PRICE =", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceNotEqualTo(long value) {
            addCriterion("SALE_PRICE <>", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceGreaterThan(long value) {
            addCriterion("SALE_PRICE >", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceGreaterThanOrEqualTo(long value) {
            addCriterion("SALE_PRICE >=", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceLessThan(long value) {
            addCriterion("SALE_PRICE <", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceLessThanOrEqualTo(long value) {
            addCriterion("SALE_PRICE <=", value, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceIn(List<Long> values) {
            addCriterion("SALE_PRICE in", values, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceNotIn(List<Long> values) {
            addCriterion("SALE_PRICE not in", values, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceBetween(long value1, long value2) {
            addCriterion("SALE_PRICE between", value1, value2, "salePrice");
            return (Criteria) this;
        }

        public Criteria andSalePriceNotBetween(long value1, long value2) {
            addCriterion("SALE_PRICE not between", value1, value2, "salePrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceIsNull() {
            addCriterion("COST_PRICE is null");
            return (Criteria) this;
        }

        public Criteria andCostPriceIsNotNull() {
            addCriterion("COST_PRICE is not null");
            return (Criteria) this;
        }

        public Criteria andCostPriceEqualTo(long value) {
            addCriterion("COST_PRICE =", value, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceNotEqualTo(long value) {
            addCriterion("COST_PRICE <>", value, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceGreaterThan(long value) {
            addCriterion("COST_PRICE >", value, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceGreaterThanOrEqualTo(long value) {
            addCriterion("COST_PRICE >=", value, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceLessThan(long value) {
            addCriterion("COST_PRICE <", value, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceLessThanOrEqualTo(long value) {
            addCriterion("COST_PRICE <=", value, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceIn(List<Long> values) {
            addCriterion("COST_PRICE in", values, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceNotIn(List<Long> values) {
            addCriterion("COST_PRICE not in", values, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceBetween(long value1, long value2) {
            addCriterion("COST_PRICE between", value1, value2, "costPrice");
            return (Criteria) this;
        }

        public Criteria andCostPriceNotBetween(long value1, long value2) {
            addCriterion("COST_PRICE not between", value1, value2, "costPrice");
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

        public Criteria andJfIsNull() {
            addCriterion("JF is null");
            return (Criteria) this;
        }

        public Criteria andJfIsNotNull() {
            addCriterion("JF is not null");
            return (Criteria) this;
        }

        public Criteria andJfEqualTo(long value) {
            addCriterion("JF =", value, "jf");
            return (Criteria) this;
        }

        public Criteria andJfNotEqualTo(long value) {
            addCriterion("JF <>", value, "jf");
            return (Criteria) this;
        }

        public Criteria andJfGreaterThan(long value) {
            addCriterion("JF >", value, "jf");
            return (Criteria) this;
        }

        public Criteria andJfGreaterThanOrEqualTo(long value) {
            addCriterion("JF >=", value, "jf");
            return (Criteria) this;
        }

        public Criteria andJfLessThan(long value) {
            addCriterion("JF <", value, "jf");
            return (Criteria) this;
        }

        public Criteria andJfLessThanOrEqualTo(long value) {
            addCriterion("JF <=", value, "jf");
            return (Criteria) this;
        }

        public Criteria andJfIn(List<Long> values) {
            addCriterion("JF in", values, "jf");
            return (Criteria) this;
        }

        public Criteria andJfNotIn(List<Long> values) {
            addCriterion("JF not in", values, "jf");
            return (Criteria) this;
        }

        public Criteria andJfBetween(long value1, long value2) {
            addCriterion("JF between", value1, value2, "jf");
            return (Criteria) this;
        }

        public Criteria andJfNotBetween(long value1, long value2) {
            addCriterion("JF not between", value1, value2, "jf");
            return (Criteria) this;
        }

        public Criteria andProdDescIsNull() {
            addCriterion("PROD_DESC is null");
            return (Criteria) this;
        }

        public Criteria andProdDescIsNotNull() {
            addCriterion("PROD_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andProdDescEqualTo(String value) {
            addCriterion("PROD_DESC =", value, "prodDesc");
            return (Criteria) this;
        }

        public Criteria andProdDescNotEqualTo(String value) {
            addCriterion("PROD_DESC <>", value, "prodDesc");
            return (Criteria) this;
        }

        public Criteria andProdDescGreaterThan(String value) {
            addCriterion("PROD_DESC >", value, "prodDesc");
            return (Criteria) this;
        }

        public Criteria andProdDescGreaterThanOrEqualTo(String value) {
            addCriterion("PROD_DESC >=", value, "prodDesc");
            return (Criteria) this;
        }

        public Criteria andProdDescLessThan(String value) {
            addCriterion("PROD_DESC <", value, "prodDesc");
            return (Criteria) this;
        }

        public Criteria andProdDescLessThanOrEqualTo(String value) {
            addCriterion("PROD_DESC <=", value, "prodDesc");
            return (Criteria) this;
        }

        public Criteria andProdDescLike(String value) {
            addCriterion("PROD_DESC like", value, "prodDesc");
            return (Criteria) this;
        }

        public Criteria andProdDescNotLike(String value) {
            addCriterion("PROD_DESC not like", value, "prodDesc");
            return (Criteria) this;
        }

        public Criteria andProdDescIn(List<String> values) {
            addCriterion("PROD_DESC in", values, "prodDesc");
            return (Criteria) this;
        }

        public Criteria andProdDescNotIn(List<String> values) {
            addCriterion("PROD_DESC not in", values, "prodDesc");
            return (Criteria) this;
        }

        public Criteria andProdDescBetween(String value1, String value2) {
            addCriterion("PROD_DESC between", value1, value2, "prodDesc");
            return (Criteria) this;
        }

        public Criteria andProdDescNotBetween(String value1, String value2) {
            addCriterion("PROD_DESC not between", value1, value2, "prodDesc");
            return (Criteria) this;
        }

        public Criteria andExtendInfoIsNull() {
            addCriterion("EXTEND_INFO is null");
            return (Criteria) this;
        }

        public Criteria andExtendInfoIsNotNull() {
            addCriterion("EXTEND_INFO is not null");
            return (Criteria) this;
        }

        public Criteria andExtendInfoEqualTo(String value) {
            addCriterion("EXTEND_INFO =", value, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoNotEqualTo(String value) {
            addCriterion("EXTEND_INFO <>", value, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoGreaterThan(String value) {
            addCriterion("EXTEND_INFO >", value, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoGreaterThanOrEqualTo(String value) {
            addCriterion("EXTEND_INFO >=", value, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoLessThan(String value) {
            addCriterion("EXTEND_INFO <", value, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoLessThanOrEqualTo(String value) {
            addCriterion("EXTEND_INFO <=", value, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoLike(String value) {
            addCriterion("EXTEND_INFO like", value, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoNotLike(String value) {
            addCriterion("EXTEND_INFO not like", value, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoIn(List<String> values) {
            addCriterion("EXTEND_INFO in", values, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoNotIn(List<String> values) {
            addCriterion("EXTEND_INFO not in", values, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoBetween(String value1, String value2) {
            addCriterion("EXTEND_INFO between", value1, value2, "extendInfo");
            return (Criteria) this;
        }

        public Criteria andExtendInfoNotBetween(String value1, String value2) {
            addCriterion("EXTEND_INFO not between", value1, value2, "extendInfo");
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