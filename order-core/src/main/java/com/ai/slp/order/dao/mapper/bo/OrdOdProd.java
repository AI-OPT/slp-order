package com.ai.slp.order.dao.mapper.bo;

import java.sql.Timestamp;

public class OrdOdProd {
    private long prodDetalId;

    private String tenantId;

    private long orderId;

    private String prodType;

    private long supplierId;

    private String sellerId;

    private String prodId;

    private String prodName;

    private String prodSn;

    private String skuId;

    private String standardProdId;

    private String supplyId;

    private String storageId;

    private String routeId;

    private Timestamp validTime;

    private Timestamp invalidTime;

    private String state;

    private long buySum;

    private long salePrice;

    private long costPrice;

    private long totalFee;

    private long discountFee;

    private long operDiscountFee;

    private String operDiscountDesc;

    private long adjustFee;

    private long jf;

    private String prodDesc;

    private String extendInfo;

    private Timestamp updateTime;

    private String updateChlId;

    private String updateOperId;

    private String skuStorageId;

    private String isInvoice;

    public long getProdDetalId() {
        return prodDetalId;
    }

    public void setProdDetalId(long prodDetalId) {
        this.prodDetalId = prodDetalId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType == null ? null : prodType.trim();
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId == null ? null : prodId.trim();
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }

    public String getProdSn() {
        return prodSn;
    }

    public void setProdSn(String prodSn) {
        this.prodSn = prodSn == null ? null : prodSn.trim();
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId == null ? null : skuId.trim();
    }

    public String getStandardProdId() {
        return standardProdId;
    }

    public void setStandardProdId(String standardProdId) {
        this.standardProdId = standardProdId == null ? null : standardProdId.trim();
    }

    public String getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(String supplyId) {
        this.supplyId = supplyId == null ? null : supplyId.trim();
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId == null ? null : storageId.trim();
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId == null ? null : routeId.trim();
    }

    public Timestamp getValidTime() {
        return validTime;
    }

    public void setValidTime(Timestamp validTime) {
        this.validTime = validTime;
    }

    public Timestamp getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Timestamp invalidTime) {
        this.invalidTime = invalidTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public long getBuySum() {
        return buySum;
    }

    public void setBuySum(long buySum) {
        this.buySum = buySum;
    }

    public long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(long salePrice) {
        this.salePrice = salePrice;
    }

    public long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(long costPrice) {
        this.costPrice = costPrice;
    }

    public long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }

    public long getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(long discountFee) {
        this.discountFee = discountFee;
    }

    public long getOperDiscountFee() {
        return operDiscountFee;
    }

    public void setOperDiscountFee(long operDiscountFee) {
        this.operDiscountFee = operDiscountFee;
    }

    public String getOperDiscountDesc() {
        return operDiscountDesc;
    }

    public void setOperDiscountDesc(String operDiscountDesc) {
        this.operDiscountDesc = operDiscountDesc == null ? null : operDiscountDesc.trim();
    }

    public long getAdjustFee() {
        return adjustFee;
    }

    public void setAdjustFee(long adjustFee) {
        this.adjustFee = adjustFee;
    }

    public long getJf() {
        return jf;
    }

    public void setJf(long jf) {
        this.jf = jf;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc == null ? null : prodDesc.trim();
    }

    public String getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo == null ? null : extendInfo.trim();
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateChlId() {
        return updateChlId;
    }

    public void setUpdateChlId(String updateChlId) {
        this.updateChlId = updateChlId == null ? null : updateChlId.trim();
    }

    public String getUpdateOperId() {
        return updateOperId;
    }

    public void setUpdateOperId(String updateOperId) {
        this.updateOperId = updateOperId == null ? null : updateOperId.trim();
    }

    public String getSkuStorageId() {
        return skuStorageId;
    }

    public void setSkuStorageId(String skuStorageId) {
        this.skuStorageId = skuStorageId == null ? null : skuStorageId.trim();
    }

    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice == null ? null : isInvoice.trim();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (adjustFee ^ (adjustFee >>> 32));
		result = prime * result + (int) (buySum ^ (buySum >>> 32));
		result = prime * result + (int) (costPrice ^ (costPrice >>> 32));
		result = prime * result + (int) (discountFee ^ (discountFee >>> 32));
		result = prime * result + ((extendInfo == null) ? 0 : extendInfo.hashCode());
		result = prime * result + ((invalidTime == null) ? 0 : invalidTime.hashCode());
		result = prime * result + ((isInvoice == null) ? 0 : isInvoice.hashCode());
		result = prime * result + (int) (jf ^ (jf >>> 32));
		result = prime * result + ((operDiscountDesc == null) ? 0 : operDiscountDesc.hashCode());
		result = prime * result + (int) (operDiscountFee ^ (operDiscountFee >>> 32));
		result = prime * result + (int) (orderId ^ (orderId >>> 32));
		result = prime * result + ((prodDesc == null) ? 0 : prodDesc.hashCode());
		result = prime * result + (int) (prodDetalId ^ (prodDetalId >>> 32));
		result = prime * result + ((prodId == null) ? 0 : prodId.hashCode());
		result = prime * result + ((prodName == null) ? 0 : prodName.hashCode());
		result = prime * result + ((prodSn == null) ? 0 : prodSn.hashCode());
		result = prime * result + ((prodType == null) ? 0 : prodType.hashCode());
		result = prime * result + ((routeId == null) ? 0 : routeId.hashCode());
		result = prime * result + (int) (salePrice ^ (salePrice >>> 32));
		result = prime * result + ((sellerId == null) ? 0 : sellerId.hashCode());
		result = prime * result + ((skuId == null) ? 0 : skuId.hashCode());
		result = prime * result + ((skuStorageId == null) ? 0 : skuStorageId.hashCode());
		result = prime * result + ((standardProdId == null) ? 0 : standardProdId.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((storageId == null) ? 0 : storageId.hashCode());
		result = prime * result + (int) (supplierId ^ (supplierId >>> 32));
		result = prime * result + ((supplyId == null) ? 0 : supplyId.hashCode());
		result = prime * result + ((tenantId == null) ? 0 : tenantId.hashCode());
		result = prime * result + (int) (totalFee ^ (totalFee >>> 32));
		result = prime * result + ((updateChlId == null) ? 0 : updateChlId.hashCode());
		result = prime * result + ((updateOperId == null) ? 0 : updateOperId.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((validTime == null) ? 0 : validTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdOdProd other = (OrdOdProd) obj;
		if (adjustFee != other.adjustFee)
			return false;
		if (buySum != other.buySum)
			return false;
		if (costPrice != other.costPrice)
			return false;
		if (discountFee != other.discountFee)
			return false;
		if (extendInfo == null) {
			if (other.extendInfo != null)
				return false;
		} else if (!extendInfo.equals(other.extendInfo))
			return false;
		if (invalidTime == null) {
			if (other.invalidTime != null)
				return false;
		} else if (!invalidTime.equals(other.invalidTime))
			return false;
		if (isInvoice == null) {
			if (other.isInvoice != null)
				return false;
		} else if (!isInvoice.equals(other.isInvoice))
			return false;
		if (jf != other.jf)
			return false;
		if (operDiscountDesc == null) {
			if (other.operDiscountDesc != null)
				return false;
		} else if (!operDiscountDesc.equals(other.operDiscountDesc))
			return false;
		if (operDiscountFee != other.operDiscountFee)
			return false;
		if (orderId != other.orderId)
			return false;
		if (prodDesc == null) {
			if (other.prodDesc != null)
				return false;
		} else if (!prodDesc.equals(other.prodDesc))
			return false;
		if (prodDetalId != other.prodDetalId)
			return false;
		if (prodId == null) {
			if (other.prodId != null)
				return false;
		} else if (!prodId.equals(other.prodId))
			return false;
		if (prodName == null) {
			if (other.prodName != null)
				return false;
		} else if (!prodName.equals(other.prodName))
			return false;
		if (prodSn == null) {
			if (other.prodSn != null)
				return false;
		} else if (!prodSn.equals(other.prodSn))
			return false;
		if (prodType == null) {
			if (other.prodType != null)
				return false;
		} else if (!prodType.equals(other.prodType))
			return false;
		if (routeId == null) {
			if (other.routeId != null)
				return false;
		} else if (!routeId.equals(other.routeId))
			return false;
		if (salePrice != other.salePrice)
			return false;
		if (sellerId == null) {
			if (other.sellerId != null)
				return false;
		} else if (!sellerId.equals(other.sellerId))
			return false;
		if (skuId == null) {
			if (other.skuId != null)
				return false;
		} else if (!skuId.equals(other.skuId))
			return false;
		if (skuStorageId == null) {
			if (other.skuStorageId != null)
				return false;
		} else if (!skuStorageId.equals(other.skuStorageId))
			return false;
		if (standardProdId == null) {
			if (other.standardProdId != null)
				return false;
		} else if (!standardProdId.equals(other.standardProdId))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (storageId == null) {
			if (other.storageId != null)
				return false;
		} else if (!storageId.equals(other.storageId))
			return false;
		if (supplierId != other.supplierId)
			return false;
		if (supplyId == null) {
			if (other.supplyId != null)
				return false;
		} else if (!supplyId.equals(other.supplyId))
			return false;
		if (tenantId == null) {
			if (other.tenantId != null)
				return false;
		} else if (!tenantId.equals(other.tenantId))
			return false;
		if (totalFee != other.totalFee)
			return false;
		if (updateChlId == null) {
			if (other.updateChlId != null)
				return false;
		} else if (!updateChlId.equals(other.updateChlId))
			return false;
		if (updateOperId == null) {
			if (other.updateOperId != null)
				return false;
		} else if (!updateOperId.equals(other.updateOperId))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (validTime == null) {
			if (other.validTime != null)
				return false;
		} else if (!validTime.equals(other.validTime))
			return false;
		return true;
	}
}