package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.o2pcallback.param.O2pCallBackRequest;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IO2pCallBackBusiSV;

@Service
@Transactional
public class O2pCallBackBusiSVImpl implements IO2pCallBackBusiSV {

    private static final Log LOG = LogFactory.getLog(O2pCallBackBusiSVImpl.class);

    @Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;

    @Override
    public void callBack(O2pCallBackRequest o2pCallBackRequest) throws BusinessException,
            SystemException {
        LOG.info("开始o2p回调:外部订单Id" + o2pCallBackRequest.getExternalOrderId());
        OrdOrder ordOrder = null;
        Timestamp sysDate = DateUtil.getSysDate();
        OrdOrderCriteria example = new OrdOrderCriteria();
        OrdOrderCriteria.Criteria criteria = example.createCriteria();
        criteria.andTenantIdEqualTo(o2pCallBackRequest.getTenantId());
//        if (o2pCallBackRequest.getExternalOrderId()!=0) {
//            criteria.andOrderTypeEqualTo(o2pCallBackRequest.getExternalOrderId());
//        }
        if (!StringUtils.isBlank(o2pCallBackRequest.getExternalSupplyId())) {
            criteria.andUserIdEqualTo(o2pCallBackRequest.getExternalSupplyId());
        }
        List<OrdOrder> list = ordOrderAtomSV.selectByExample(example);
        if (!CollectionUtil.isEmpty(list)) {
            ordOrder = list.get(0);
            ordOrder.setState(o2pCallBackRequest.getState());
            ordOrder.setStateChgTime(sysDate);
            ordOrderAtomSV.updateById(ordOrder);
        }
    }

}
