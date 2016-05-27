package com.ai.slp.order.service.atom.interfaces;

import java.util.List;

import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtend;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtendCriteria;

public interface IOrdOdProdExtendAtomSV {

    int insertSelective(OrdOdProdExtend record);

    List<OrdOdProdExtend> selectByExample(OrdOdProdExtendCriteria example);
}
