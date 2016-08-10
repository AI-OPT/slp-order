package com.ai.slp.order.dao.mapper.factory;

import javax.annotation.PostConstruct;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.slp.order.dao.mapper.interfaces.FreightTemplateMapper;
import com.ai.slp.order.dao.mapper.interfaces.FreightTemplateProdMapper;
import com.ai.slp.order.dao.mapper.interfaces.OrdBalacneIfMapper;
import com.ai.slp.order.dao.mapper.interfaces.OrdOdCartProdMapper;
import com.ai.slp.order.dao.mapper.interfaces.OrdOdExtendMapper;
import com.ai.slp.order.dao.mapper.interfaces.OrdOdFeeOffsetMapper;
import com.ai.slp.order.dao.mapper.interfaces.OrdOdFeeProdMapper;
import com.ai.slp.order.dao.mapper.interfaces.OrdOdFeeTotalMapper;
import com.ai.slp.order.dao.mapper.interfaces.OrdOdInvoiceMapper;
import com.ai.slp.order.dao.mapper.interfaces.OrdOdLogisticsMapper;
import com.ai.slp.order.dao.mapper.interfaces.OrdOdProdExtendMapper;
import com.ai.slp.order.dao.mapper.interfaces.OrdOdProdMapper;
import com.ai.slp.order.dao.mapper.interfaces.OrdOdStateChgMapper;
import com.ai.slp.order.dao.mapper.interfaces.OrdOrderMapper;
import com.ai.slp.order.dao.mapper.interfaces.OrdRuleMapper;

@Component
public class MapperFactory {

    @Autowired
    private SqlSessionTemplate st;

    private static SqlSessionTemplate sqlSessionTemplate;

    @PostConstruct
    void init() {
        setSqlSessionTemplate(st);
    }

    public static void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        MapperFactory.sqlSessionTemplate = sqlSessionTemplate;
    }

    public static OrdOdExtendMapper getOrdOdExtendMapper() {
        return sqlSessionTemplate.getMapper(OrdOdExtendMapper.class);
    }

    public static OrdOdFeeOffsetMapper getOrdOdFeeOffsetMapper()

    {
        return sqlSessionTemplate.getMapper(OrdOdFeeOffsetMapper.class);
    }

    public static OrdOdFeeTotalMapper getOrdOdFeeTotalMapper() {
        return sqlSessionTemplate.getMapper(OrdOdFeeTotalMapper.class);
    }

    public static OrdOdInvoiceMapper getOrdOdInvoiceMapper() {
        return sqlSessionTemplate.getMapper(OrdOdInvoiceMapper.class);
    }

    public static OrdOdLogisticsMapper getOrdOdLogisticsMapper() {
        return sqlSessionTemplate.getMapper(OrdOdLogisticsMapper.class);
    }

    public static OrdOdProdMapper getOrdOdProdMapper() {
        return sqlSessionTemplate.getMapper(OrdOdProdMapper.class);
    }

    public static OrdOdStateChgMapper getOrdOdStateChgMapper() {
        return sqlSessionTemplate.getMapper(OrdOdStateChgMapper.class);
    }

    public static OrdOrderMapper getOrdOrderMapper() {
        return sqlSessionTemplate.getMapper(OrdOrderMapper.class);
    }

    public static OrdOdProdExtendMapper getOrdOdProdExtendMapper() {
        return sqlSessionTemplate.getMapper(OrdOdProdExtendMapper.class);
    }

    public static OrdBalacneIfMapper getOrdBalacneIfMapper() {
        return sqlSessionTemplate.getMapper(OrdBalacneIfMapper.class);
    }

    public static OrdOdCartProdMapper getOrdOdCartProdMapper() {
        return sqlSessionTemplate.getMapper(OrdOdCartProdMapper.class);
    }

    public static OrdOdFeeProdMapper getOrdOdFeeProdMapper() {
        return sqlSessionTemplate.getMapper(OrdOdFeeProdMapper.class);
    }
    
    public static FreightTemplateMapper getFreightTemplateMapper() {
    	return sqlSessionTemplate.getMapper(FreightTemplateMapper.class);
    }
    
    public static FreightTemplateProdMapper getFreightTemplateProdMapper() {
    	return sqlSessionTemplate.getMapper(FreightTemplateProdMapper.class);
    }
    
    public static OrdRuleMapper getOrdRuleMapper() {
        return sqlSessionTemplate.getMapper(OrdRuleMapper.class);
    }
}

