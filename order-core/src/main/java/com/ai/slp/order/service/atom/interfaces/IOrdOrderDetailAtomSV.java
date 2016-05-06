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
    List<OrdOdProd> getOrdOdProdInfo(OrdOdProdCriteria example); 
    List<OrdOdProdExtend> getOrdOdProdExtend(OrdOdProdExtendCriteria example);
    List<OrdOdFeeTotal> getOrdOdFeeTotalInfo(OrdOdFeeTotalCriteria example);
    
}
