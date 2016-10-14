package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeProdAtomSV;

@Component
public class OrdOdFeeProdAtomSVImpl implements IOrdOdFeeProdAtomSV {

    @Override
    public List<OrdOdFeeProd> selectByExample(OrdOdFeeProdCriteria example) {
        return MapperFactory.getOrdOdFeeProdMapper().selectByExample(example);
    }

    @Override
    public int insertSelective(OrdOdFeeProd record) {
        return MapperFactory.getOrdOdFeeProdMapper().insertSelective(record);
    }

	@Override
	public OrdOdFeeProd selectByOrdAndStyle(long orderId, String payStyle) {
		OrdOdFeeProdCriteria example = new OrdOdFeeProdCriteria();
		OrdOdFeeProdCriteria.Criteria param = example.createCriteria();
        param.andOrderIdEqualTo(orderId);
        param.andPayStyleEqualTo(payStyle);
        List<OrdOdFeeProd> list = MapperFactory.getOrdOdFeeProdMapper().selectByExample(example);
        if(!CollectionUtil.isEmpty(list)){
        	return list.get(0);
        }
        return null;
	}
	
	@Override
	public List<OrdOdFeeProd> selectByOrderId(long orderId) {
		OrdOdFeeProdCriteria example = new OrdOdFeeProdCriteria();
		OrdOdFeeProdCriteria.Criteria param = example.createCriteria();
		param.andOrderIdEqualTo(orderId);
		return MapperFactory.getOrdOdFeeProdMapper().selectByExample(example);
	}

}
