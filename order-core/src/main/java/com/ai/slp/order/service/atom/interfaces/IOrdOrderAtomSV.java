package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;

public interface IOrdOrderAtomSV {

    int countByExample(OrdOrderCriteria example);

    List<OrdOrder> selectByExample(OrdOrderCriteria example);
}
