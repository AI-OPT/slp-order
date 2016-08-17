package com.ai.slp.order.api.orderDetailList.param;

import java.util.Date;
import java.util.List;

import com.ai.opt.base.vo.BaseResponse;

public class ProductOrderDetailListParam extends BaseResponse{

    private static final long serialVersionUID = 1L;
    
    /**
     * 订单来源
     */
    public String orderResource;
    
    /**
     * 订单归属地
     */
    public String cityCode;
    
    /**
     * 订单类型
     */
    public String orderType;
    /**
     * 父订单号
     */
    public String parentOrderId;
    /**
     * 是否需要物流
     */
    private String deliveryFlag;
    
    /**
     * 订单状态
     */
    private String state;
    
    /**
     * 下单时间
     */
    private Date orderTime;
    
    /**
     * 完成时间
     */
    private Date finishTime;
    
    /**
     * 支付类型
     */
    public String payStyle;
    
    /**
     * 买家账号
     */
    public String BuyerAccount;
    
    /**
     * 手机号
     */
    public String phoneNumber;
    
    /**
     * 退款流水号
     */
    public String RefundSerialNumber;
    /**
     * 退款账号
     */
    public String RefundProgress;
    
    /**
     * 退款时间
     */
    
    public Date RefundTime;
    
    /**
     * 关闭类型
     */
    public String CloseTypes;
    /**
     * 关闭时间
     */
    public Date closeTime;
    
    /**
     * 商品
     */
    public String commodity;
    /**
     * 单价
     */
    public int SalePrice;
    /**
     * 购买数量
     */
    public int buySum;
    /**
     * 总金额
     */
    public int totalFee;
    /**
     * 减免金额
     */
    public int discountFee;
    
    

    public String getOrderResource() {
        return orderResource;
    }

    public void setOrderResource(String orderResource) {
        this.orderResource = orderResource;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(String parentOrderId) {
        this.parentOrderId = parentOrderId;
    }

    public String getDeliveryFlag() {
        return deliveryFlag;
    }

    public void setDeliveryFlag(String deliveryFlag) {
        this.deliveryFlag = deliveryFlag;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getPayStyle() {
        return payStyle;
    }

    public void setPayStyle(String payStyle) {
        this.payStyle = payStyle;
    }

    public String getBuyerAccount() {
        return BuyerAccount;
    }

    public void setBuyerAccount(String buyerAccount) {
        BuyerAccount = buyerAccount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRefundSerialNumber() {
        return RefundSerialNumber;
    }

    public void setRefundSerialNumber(String refundSerialNumber) {
        RefundSerialNumber = refundSerialNumber;
    }

    public String getRefundProgress() {
        return RefundProgress;
    }

    public void setRefundProgress(String refundProgress) {
        RefundProgress = refundProgress;
    }

    public Date getRefundTime() {
        return RefundTime;
    }

    public void setRefundTime(Date refundTime) {
        RefundTime = refundTime;
    }

    public String getCloseTypes() {
        return CloseTypes;
    }

    public void setCloseTypes(String closeTypes) {
        CloseTypes = closeTypes;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public int getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(int salePrice) {
        SalePrice = salePrice;
    }

    public int getBuySum() {
        return buySum;
    }

    public void setBuySum(int buySum) {
        this.buySum = buySum;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public int getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(int discountFee) {
        this.discountFee = discountFee;
    }

}
