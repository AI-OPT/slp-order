package com.ai.slp.order.api.orderDetailList.param;

import java.util.Date;
import java.util.List;

import com.ai.opt.base.vo.BaseResponse;

public class OrdOrderDetailParams extends BaseResponse{
    private static final long serialVersionUID = 1L;

    /**
     * 订单Id
     */
    public long orderId;
    
    /**
     * 101  提交 
       11  待支付
       111 已支付
       12  待充值
       121 已充值
       122 充值失败
       123 充值未知
       13  待配货
       131 已配货
       14  待出库
       141 已出库
       15  待发货
       151 已发货
       16  待收货
       161 已收货
       17  待确认
       171 已确认
       19  待归档  
       191 已归档
       21  待审核
       211 已审核
       212 审核失败
       22  待买家退货
       221 已买家退货
       23   待卖家收货确认
       231  卖家已收货确认
       21  待退费
       211 已退费
       90  完成
       91  关闭（取消）
       92  退货完成
       93  换货完成
       94  退费完成
     */
    public String state;
    
    /**
     * 订单时间
     */
    public Date orderTime;
    
    /**
     * 完成时间
     */
    public Date finishTime;
    
    /**
     * 运营商
     */
    public String basicOrgId;
    
    /**
     * 归属地
     */
    public String cityCode;
    
    /**
     * 购买数量
     */
    public long buySum;
    
    /**
     * 手机号
     */
    public String phoneNum;
    
    /**
     * 销售单价
     */
    public long salePrice;
    
    
    
    /**
     * 支付类型
     */
    public String payStyle;
    
    /**
     * 总额
     */
    public long totalFee;

    /**
     * 数量
     */
    public int count;
    /**
     * 小计
     */
    public long subTotal;
    
    
    
    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
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

    public String getBasicOrgId() {
        return basicOrgId;
    }

    public void setBasicOrgId(String basicOrgId) {
        this.basicOrgId = basicOrgId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public long getBuySum() {
        return buySum;
    }

    public void setBuySum(long buySum) {
        this.buySum = buySum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(long salePrice) {
        this.salePrice = salePrice;
    }

    public String getPayStyle() {
        return payStyle;
    }

    public void setPayStyle(String payStyle) {
        this.payStyle = payStyle;
    }

    public long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(long subTotal) {
        this.subTotal = subTotal;
    }
    
    
}
