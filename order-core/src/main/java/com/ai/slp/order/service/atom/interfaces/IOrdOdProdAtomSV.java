package com.ai.slp.order.service.atom.interfaces;

import com.ai.slp.order.dao.mapper.bo.OrdOdProd;

public interface IOrdOdProdAtomSV {
    int insertSelective(OrdOdProd record);
}
