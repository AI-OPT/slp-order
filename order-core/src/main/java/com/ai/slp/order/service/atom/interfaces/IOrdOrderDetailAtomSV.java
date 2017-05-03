package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotalCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtend;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtendCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;

public interface IOrdOrderDetailAtomSV {
    List<OrdOrder> selectByExample(OrdOrderCriteria example);
    //按条件查询订单商品信息
    List<OrdOdProd> getOrdOdProdInfo(OrdOdProdCriteria example); 
    //按条件查询订单商品明细信息
    List<OrdOdProdExtend> getOrdOdProdExtend(OrdOdProdExtendCriteria example);
    //按条件查询订单费用总表信息
    List<OrdOdFeeTotal> getOrdOdFeeTotalInfo(OrdOdFeeTotalCriteria example);
    
}
