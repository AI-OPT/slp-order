package com.ai.slp.order.vo;

import java.util.List;

/**
 * 商品明细拓展信息 Date: 2016年5月25日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
public class InfoJsonVo {
    /**
     * 拓展信息值
     */
    private List<ProdExtendInfoVo> prodExtendInfoVoList;

    public List<ProdExtendInfoVo> getProdExtendInfoVoList() {
        return prodExtendInfoVoList;
    }

    public void setProdExtendInfoVoList(List<ProdExtendInfoVo> prodExtendInfoVoList) {
        this.prodExtendInfoVoList = prodExtendInfoVoList;
    }

}
