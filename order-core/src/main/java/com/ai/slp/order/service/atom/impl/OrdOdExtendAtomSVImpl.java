package com.ai.slp.order.service.atom.impl;

import java.util.List;
import org.springframework.stereotype.Component;
import com.ai.slp.order.dao.mapper.bo.OrdOdExtend;
import com.ai.slp.order.dao.mapper.bo.OrdOdExtendCriteria;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdOdExtendAtomSV;

@Component
public class OrdOdExtendAtomSVImpl implements IOrdOdExtendAtomSV {

    @Override
    public int insertSelective(OrdOdExtend record) {
        return MapperFactory.getOrdOdExtendMapper().insertSelective(record);
    }

	@Override
	public List<OrdOdExtend> selectByExample(OrdOdExtendCriteria example) {
		return MapperFactory.getOrdOdExtendMapper().selectByExample(example);
	}
    
    
}
