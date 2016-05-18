package com.ai.slp.order.vo;

/**
 * 商品购物车中概览信息
 * Created by jackieliu on 16/5/18.
 */
public class ShopCartCachePointsVo {
    /**
     * 购物车里商品数
     */
    private Long prodNum;
    /**
     * 购物车里商品的总数量
     */
    private Long prodTotal;

    public Long getProdNum() {
        return prodNum;
    }

    public void setProdNum(Long prodNum) {
        this.prodNum = prodNum;
    }

    public Long getProdTotal() {
        return prodTotal;
    }

    public void setProdTotal(Long prodTotal) {
        this.prodTotal = prodTotal;
    }
}
