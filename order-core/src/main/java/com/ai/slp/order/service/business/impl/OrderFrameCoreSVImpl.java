package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.dao.mapper.bo.OrdOdStateChg;
import com.ai.slp.order.service.atom.interfaces.IOrdOdStateChgAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.util.SequenceUtil;
import com.ai.slp.order.vo.OrderStateChgVo;

@Service
@Transactional
public class OrderFrameCoreSVImpl implements IOrderFrameCoreSV {
    @Autowired
    private IOrdOdStateChgAtomSV ordOdStateChgAtomSV;

    //订单轨迹记录
    @Override
    public void ordOdStateChg(Long orderId, String tenantId, String orgState, String newState,
            String chgDesc, String orgId, String operId, String operName, Timestamp timestamp)
            throws BusinessException, SystemException {
        OrdOdStateChg bean = new OrdOdStateChg();
        Long stateChgId = SequenceUtil.createStateChgId();
        bean.setStateChgId(stateChgId);
        bean.setOrderId(orderId);
        bean.setTenantId(tenantId);
        bean.setOrgState(orgState);
        bean.setNewState(newState);
        bean.setChgDesc(chgDesc);
        bean.setOrgId(orgId);
        bean.setOperId(operId);
        bean.setOperName(operName);
        bean.setStateChgTime(timestamp);
        ordOdStateChgAtomSV.insertSelective(bean);
    }
    
    //订单轨迹记录 异步
	@Override
	public void ordOdStateChg(OrderStateChgVo stateChgVo) throws BusinessException, SystemException {
		// TODO Auto-generated method stub
		OrdOdStateChg bean = new OrdOdStateChg();
        Long stateChgId = SequenceUtil.createStateChgId();
        bean.setStateChgId(stateChgId);
        bean.setOrderId(stateChgVo.getOrderId());
        bean.setTenantId(stateChgVo.getTenantId());
        bean.setOrgState(stateChgVo.getOrgState());
        bean.setNewState(stateChgVo.getNewState());
        bean.setChgDesc(stateChgVo.getChgDesc());
        bean.setOrgId(stateChgVo.getOrgId());
        bean.setOperId(stateChgVo.getOperId());
        bean.setOperName(stateChgVo.getOperName());
        bean.setStateChgTime(stateChgVo.getTimestamp());
        ordOdStateChgAtomSV.insertSelective(bean);
	}
}
