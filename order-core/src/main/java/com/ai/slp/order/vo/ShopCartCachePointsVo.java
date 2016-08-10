package com.ai.slp.order.vo;

/**
 * 商品购物车中概览信息
 * Created by jackieliu on 16/5/18.
 */
public class ShopCartCachePointsVo {
    /**
     * 购物车里商品数
     */
    private long prodNum;
    /**
     * 购物车里商品的总数量
     */
    private long prodTotal;

    public long getProdNum() {
        return prodNum;
    }

    public void setProdNum(long prodNum) {
        this.prodNum = prodNum;
    }

    public long getProdTotal() {
        return prodTotal;
    }

    public void setProdTotal(long prodTotal) {
        this.prodTotal = prodTotal;
    }
}
