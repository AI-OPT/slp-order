package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.factory.MapperFactory;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;

@Component
public class OrdOdProdAtomSVImpl implements IOrdOdProdAtomSV {

    @Override
    public int insertSelective(OrdOdProd record) {
        return MapperFactory.getOrdOdProdMapper().insertSelective(record);
    }

    @Override
    public List<OrdOdProd> selectByExample(OrdOdProdCriteria example) {
        return MapperFactory.getOrdOdProdMapper().selectByExample(example);
    }

    @Override
    public int updateById(OrdOdProd ordOdProd) {
        return MapperFactory.getOrdOdProdMapper().updateByPrimaryKey(ordOdProd);
    }

	@Override
	public OrdOdProd selectByPrimaryKey(long prodDetalId) {
		return MapperFactory.getOrdOdProdMapper().selectByPrimaryKey(prodDetalId);
	}

	@Override
	public List<OrdOdProd> selectByOrd(String tenantId, long orderId) {
		OrdOdProdCriteria example = new OrdOdProdCriteria();
		OrdOdProdCriteria.Criteria param = example.createCriteria();
        if(!StringUtil.isBlank(tenantId)){
        	param.andTenantIdEqualTo(tenantId);
        }
        param.andOrderIdEqualTo(orderId);
        return MapperFactory.getOrdOdProdMapper().selectByExample(example);
	}

	@Override
	public List<OrdOdProd> selectByProdName(String tenantId, String prodName) {
		OrdOdProdCriteria example = new OrdOdProdCriteria();
		OrdOdProdCriteria.Criteria param = example.createCriteria();
        if(!StringUtil.isBlank(tenantId)){
        	param.andTenantIdEqualTo(tenantId);
        }
        param.andProdNameLike("%"+prodName+"%");
        return MapperFactory.getOrdOdProdMapper().selectByExample(example);
	}

}
