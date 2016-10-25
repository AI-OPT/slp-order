package com.ai.slp.order.service.atom.impl;

import java.util.List;
import org.springframework.stereotype.Component;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIf;
import com.ai.slp.order.dao.mapper.bo.OrdBalacneIfCriteria;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdBalacneIfAtomSV;

@Component
public class OrdBalacneIfAtomSVImpl implements IOrdBalacneIfAtomSV {

    @Override
    public int insertSelective(OrdBalacneIf record) {
        return MapperFactory.getOrdBalacneIfMapper().insertSelective(record);
    }
    
    public List<OrdBalacneIf> selectByExample(OrdBalacneIfCriteria example) {
    	OrdBalacneIfCriteria balanceExample=new OrdBalacneIfCriteria();
    	return MapperFactory.getOrdBalacneIfMapper().selectByExample(example);
    }
    
    @Override
    public OrdBalacneIf selectByOrderId(String tenantId, long orderId) {
    	OrdBalacneIfCriteria example = new OrdBalacneIfCriteria();
        example.createCriteria().andTenantIdEqualTo(tenantId).andOrderIdEqualTo(orderId);
        List<OrdBalacneIf> OrdBalacneIfs = MapperFactory.getOrdBalacneIfMapper()
                .selectByExample(example);
        return OrdBalacneIfs == null || OrdBalacneIfs.isEmpty() ? null : OrdBalacneIfs.get(0);
    }

	@Override
	public int updateByPrimaryKey(OrdBalacneIf record) {
		return MapperFactory.getOrdBalacneIfMapper().updateByPrimaryKey(record);
	}
}
