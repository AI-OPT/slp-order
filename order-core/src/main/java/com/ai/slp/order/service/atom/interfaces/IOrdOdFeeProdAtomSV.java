package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProdCriteria;

public interface IOrdOdFeeProdAtomSV {
    List<OrdOdFeeProd> selectByExample(OrdOdFeeProdCriteria example);

    int insertSelective(OrdOdFeeProd record);
    
    OrdOdFeeProd selectByOrdAndStyle(long orderId,String payStyle);

}
