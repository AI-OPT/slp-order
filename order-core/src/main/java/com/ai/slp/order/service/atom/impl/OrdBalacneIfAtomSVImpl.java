package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIf;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIfCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIfCriteria.Criteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.interfaces.OrdBalacneIfMapper;
import com.ai.slp.order.service.atom.interfaces.IOrdBalacneIfAtomSV;

@Component
public class OrdBalacneIfAtomSVImpl implements IOrdBalacneIfAtomSV {
	
	@Autowired
	private OrdBalacneIfMapper ordBalacneIfMapper;

    @Override
    public int insertSelective(OrdBalacneIf record) {
        return ordBalacneIfMapper.insertSelective(record);
    }
    
    public List<OrdBalacneIf> selectByExample(OrdBalacneIfCriteria example) {
    	return ordBalacneIfMapper.selectByExample(example);
    }
    
    @Override
    public OrdBalacneIf selectByOrderId(String tenantId, long orderId) {
    	OrdBalacneIfCriteria example = new OrdBalacneIfCriteria();
        example.createCriteria().andTenantIdEqualTo(tenantId).andOrderIdEqualTo(orderId);
        List<OrdBalacneIf> OrdBalacneIfs = ordBalacneIfMapper
                .selectByExample(example);
        return OrdBalacneIfs == null || OrdBalacneIfs.isEmpty() ? null : OrdBalacneIfs.get(0);
    }

	@Override
	public int updateByPrimaryKey(OrdBalacneIf record) {
		return ordBalacneIfMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<OrdBalacneIf> selectBalacneIf(OrdOrder order) {
		// TODO Auto-generated method stub
		OrdBalacneIfCriteria exampleBalance = new OrdBalacneIfCriteria();
		Criteria criteriaBalance = exampleBalance.createCriteria();
		criteriaBalance.andTenantIdEqualTo(order.getTenantId());
		criteriaBalance.andOrderIdEqualTo(order.getOrderId());
		return ordBalacneIfMapper.selectByExample(exampleBalance);
	}
}
