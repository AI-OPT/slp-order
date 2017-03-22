package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOdExtend;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdExtend;
import com.ai.slp.order.dao.mapper.bo.OrdOdStateChg;
import com.ai.slp.order.service.atom.interfaces.IOrdOdExtendAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdExtendAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdStateChgAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.util.SequenceUtil;
import com.ai.slp.order.vo.OrderStateChgVo;

@Service
@Transactional
public class OrderFrameCoreSVImpl implements IOrderFrameCoreSV {
    @Autowired
    private IOrdOdStateChgAtomSV ordOdStateChgAtomSV;

    @Autowired
    private IOrdOdExtendAtomSV ordOdExtendAtomSV;

    @Autowired
    private IOrdOdProdExtendAtomSV ordOdProdExtendAtomSV;
    
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

    @Override
    public void createOrdExtend(long orderId, String tenantId, String infoJson)
            throws BusinessException, SystemException {
        if (orderId == 0) {
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "保存订单扩展信息-订单号");
        }
        OrdOdExtend bean = new OrdOdExtend();
        bean.setOrderId(orderId);
        bean.setTenantId(tenantId);
        bean.setInfoJson(infoJson);
        ordOdExtendAtomSV.insertSelective(bean);
    }

    @Override
    public void createOrdProdExtend(long prodDetailId, long orderId, String tenantId,
            String infoJson,String batchFlag) throws BusinessException, SystemException {
        if (orderId == 0) {
            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "保存订单扩展信息-订单号");
        }
        OrdOdProdExtend bean = new OrdOdProdExtend();
        bean.setProdDetalExtendId(SequenceUtil.createProdDetailExtendId());
        bean.setProdDetalId(prodDetailId);
        bean.setOrderId(orderId);
        bean.setTenantId(tenantId);
        bean.setInfoJson(infoJson);
        bean.setBatchFlag(batchFlag);
        ordOdProdExtendAtomSV.insertSelective(bean);
    }

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
