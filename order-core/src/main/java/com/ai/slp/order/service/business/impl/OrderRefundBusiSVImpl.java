package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.orderrefund.param.OrderFullRefundRequest;
import com.ai.slp.order.api.orderrefund.param.OrderPartRefundRequest;
import com.ai.slp.order.api.orderrefund.param.OrderRefuseRefundRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.service.business.interfaces.IOrderRefundBusiSV;
import com.ai.slp.order.util.CommonCheckUtils;

@Service
@Transactional
public class OrderRefundBusiSVImpl implements IOrderRefundBusiSV {

	private static Logger logger = LoggerFactory.getLogger(OrderRefundBusiSVImpl.class);
	
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	
	@Autowired
	private IOrderFrameCoreSV orderFrameCoreSV;
	 
	public BaseResponse fullRefund(OrderFullRefundRequest request) throws BusinessException, SystemException {
		CommonCheckUtils.checkTenantId(request.getTenantId(), ExceptCodeConstants.Special.PARAM_IS_NULL);
		OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(ordOrder==null) {
			logger.error("");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单信息不存在[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
		}
		/* 更新订单状态和写入订单轨迹*/
		String newState = OrdersConstants.OrdOrder.State.FINISH_REPAY;
		String chgDesc = OrdOdStateChg.ChgDesc.ORDER_REVOKE_FINISH_PAY;
		this.updateOrderState(ordOrder, newState, request.getOperId(), chgDesc);
		//TODO
		/*退款到指定的账户中 */
		
		return null;
	}

	public BaseResponse partRefund(OrderPartRefundRequest request) throws BusinessException, SystemException {
		return null;
	}
	
	@Override
	public BaseResponse refuseRefund(OrderRefuseRefundRequest request) throws BusinessException, SystemException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
    /**
     * 更新订单状态
     */
    private void updateOrderState(OrdOrder ordOrder,String newState,
    		String operId,String chgDesc) {
    	Timestamp sysDate=DateUtil.getSysDate();
        String orgState = ordOrder.getState();
        ordOrder.setState(newState);
        ordOrder.setStateChgTime(sysDate);
        ordOrderAtomSV.updateById(ordOrder);
        // 写入订单状态变化轨迹表
        orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState, newState,
                OrdOdStateChg.ChgDesc.ORDER_TO_PAY, null, null, null, sysDate);
    }

}
