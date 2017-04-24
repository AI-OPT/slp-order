package com.ai.slp.order.service.business.interfaces;

import java.sql.Timestamp;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.slp.order.vo.OrderStateChgVo;

public interface IOrderFrameCoreSV {

    /**
     * 记录订单轨迹2
     * @param stateChgVo
     * @throws BusinessException
     * @throws SystemException
     * @author caofz
     * @ApiDocMethod
     * @ApiCode 
     * @RestRelativeURL
     */
    public void ordOdStateChg(OrderStateChgVo stateChgVo) throws BusinessException, SystemException;
    
    /**
     * 记录订单轨迹
     * 
     * @param orderId
     * @param tenantId
     * @param orgState
     * @param newState
     * @param chgDesc
     * @param orgId
     * @param operId
     * @param operName
     * @param timestamp
     * @throws Exception
     * @author zhangxw
     * @ApiDocMethod
     */
    public void ordOdStateChg(Long orderId, String tenantId, String orgState, String newState,
            String chgDesc, String orgId, String operId, String operName, Timestamp timestamp)
            throws BusinessException, SystemException;
}
