package com.ai.slp.order.service.business.interfaces;

import java.sql.Timestamp;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;

public interface IOrderFrameCoreSV {

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

    /**
     * 创建订单扩展信息
     * 
     * @param orderId
     * @param tenantId
     * @param infoJson
     * @throws BusinessException
     * @throws SystemException
     * @author zhangxw
     * @ApiDocMethod
     */
    public void createOrdExtend(long orderId, String tenantId, String infoJson)
            throws BusinessException, SystemException;

    /**
     * 创建订单商品明细扩展信息
     * @param prodDetailId
     * @param orderId
     * @param tenantId
     * @param infoJson
     * @throws BusinessException
     * @throws SystemException
     * @author zhangxw
     * @param batchFlag 
     * @ApiDocMethod
     */
    public void createOrdProdExtend(long prodDetailId, long orderId, String tenantId,
            String infoJson, String batchFlag) throws BusinessException, SystemException;

}
