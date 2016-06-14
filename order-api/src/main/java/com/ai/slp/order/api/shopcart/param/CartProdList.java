package com.ai.slp.order.api.shopcart.param;

import com.ai.opt.base.vo.BaseResponse;

import java.util.List;

/**
 * 购物车中所有商品信息
 *
 * Date: 2016年5月16日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 *
 * @author liutong5
 */
public class CartProdList extends BaseResponse {
    /**
     * 购物车商品详情信息
     */
    private  List<CartProdInfo> prodInfoList;

    public List<CartProdInfo> getProdInfoList() {
        return prodInfoList;
    }

    public void setProdInfoList(List<CartProdInfo> prodInfoList) {
        this.prodInfoList = prodInfoList;
    }
}
