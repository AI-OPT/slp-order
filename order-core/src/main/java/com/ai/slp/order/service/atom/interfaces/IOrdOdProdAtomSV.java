package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;

public interface IOrdOdProdAtomSV {
    int insertSelective(OrdOdProd record);

    List<OrdOdProd> selectByExample(OrdOdProdCriteria example);

    int updateById(OrdOdProd ordOdProd);
    
    OrdOdProd selectByPrimaryKey(long prodDetalId);
    
    List<OrdOdProd> selectByOrd(String tenantId,long orderId);
}
