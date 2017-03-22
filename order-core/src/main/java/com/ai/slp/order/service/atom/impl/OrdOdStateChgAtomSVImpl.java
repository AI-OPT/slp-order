package com.ai.slp.order.service.atom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.bo.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.interfaces.OrdOdStateChgMapper;
import com.ai.slp.order.service.atom.interfaces.IOrdOdStateChgAtomSV;

@Component
public class OrdOdStateChgAtomSVImpl implements IOrdOdStateChgAtomSV {
	
	@Autowired
	private OrdOdStateChgMapper ordOdStateChgMapper;
    @Override
    public int insertSelective(OrdOdStateChg record) {
        return ordOdStateChgMapper.insertSelective(record);
    }

}
