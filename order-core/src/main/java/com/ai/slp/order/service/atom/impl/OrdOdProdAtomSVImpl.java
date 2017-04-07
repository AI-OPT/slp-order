package com.ai.slp.order.service.atom.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.sdk.util.StringUtil;
import com.ai.slp.order.dao.mapper.attach.OrdOrderAttachMapper;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.interfaces.OrdOdProdMapper;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;

@Component
public class OrdOdProdAtomSVImpl implements IOrdOdProdAtomSV {
	@Autowired
	private OrdOdProdMapper ordOdProdMapper;
	@Autowired
	private OrdOrderAttachMapper ordOrderAttachMapper;

    @Override
    public int insertSelective(OrdOdProd record) {
        return ordOdProdMapper.insertSelective(record);
    }

    @Override
    public List<OrdOdProd> selectByExample(OrdOdProdCriteria example) {
        return ordOdProdMapper.selectByExample(example);
    }

    @Override
    public int updateById(OrdOdProd ordOdProd) {
        return ordOdProdMapper.updateByPrimaryKey(ordOdProd);
    }

	@Override
	public OrdOdProd selectByPrimaryKey(long prodDetalId) {
		return ordOdProdMapper.selectByPrimaryKey(prodDetalId);
	}

	@Override
	public List<OrdOdProd> selectByOrd(String tenantId, long orderId) {
		OrdOdProdCriteria example = new OrdOdProdCriteria();
		OrdOdProdCriteria.Criteria param = example.createCriteria();
        if(!StringUtil.isBlank(tenantId)){
        	param.andTenantIdEqualTo(tenantId);
        }
        param.andOrderIdEqualTo(orderId);
        return ordOdProdMapper.selectByExample(example);
	}

	@Override
	public List<OrdOdProd> selectByProdName(String tenantId, String prodName) {
		OrdOdProdCriteria example = new OrdOdProdCriteria();
		OrdOdProdCriteria.Criteria param = example.createCriteria();
        if(!StringUtil.isBlank(tenantId)){
        	param.andTenantIdEqualTo(tenantId);
        }
        param.andProdNameLike("%"+prodName+"%");
        return ordOdProdMapper.selectByExample(example);
	}

	@Override
	public int updateByExampleSelective(OrdOdProd record, OrdOdProdCriteria example) {
		return ordOdProdMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int countByExample(OrdOdProdCriteria example) {
		return ordOdProdMapper.countByExample(example);
	}

	@Override
	public List<OrdOdProd> selectSaleProd(String tenantId, long orderId, String skuId) {
		// TODO Auto-generated method stub
		OrdOdProdCriteria prodExample=new OrdOdProdCriteria();
		OrdOdProdCriteria.Criteria prodCriteria = prodExample.createCriteria();
		prodCriteria.andOrderIdEqualTo(orderId);
		prodCriteria.andTenantIdEqualTo(tenantId);
		prodCriteria.andSkuIdEqualTo(skuId);
		return ordOdProdMapper.selectByExample(prodExample);
	}

	@Override
	public List<OrdOdProd> selectOrdProd(String tenantId, long orderId,String cusServiceFlag) {
		// TODO Auto-generated method stub
		OrdOdProdCriteria example=new OrdOdProdCriteria();
		OrdOdProdCriteria.Criteria criteria = example.createCriteria();
		criteria.andTenantIdEqualTo(tenantId);
		criteria.andOrderIdEqualTo(orderId);
		criteria.andCusServiceFlagEqualTo(cusServiceFlag);
		return ordOdProdMapper.selectByExample(example);
	}

	@Override
	public int updateCusServiceFlag(OrdOdProd ordOdProd) {
		// TODO Auto-generated method stub
		return ordOrderAttachMapper.updateCusServiceFlag(ordOdProd);
	}

}
