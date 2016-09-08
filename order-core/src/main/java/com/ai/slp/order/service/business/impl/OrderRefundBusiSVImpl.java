package com.ai.slp.order.service.business.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.orderrefund.param.OrderRefundRequest;
import com.ai.slp.order.api.orderrefund.param.OrderRefuseRefundRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.constants.OrdersConstants.OrdOdStateChg;
import com.ai.slp.order.dao.mapper.bo.OrdOdFeeTotal;
import com.ai.slp.order.dao.mapper.bo.OrdOdProd;
import com.ai.slp.order.dao.mapper.bo.OrdOdProdCriteria;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.service.atom.interfaces.IOrdOdFeeTotalAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOdProdAtomSV;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderFrameCoreSV;
import com.ai.slp.order.service.business.interfaces.IOrderRefundBusiSV;
import com.ai.slp.order.util.CommonCheckUtils;
import com.ai.slp.product.api.storageserver.interfaces.IStorageNumSV;
import com.ai.slp.product.api.storageserver.param.StorageNumBackReq;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

@Service
@Transactional
public class OrderRefundBusiSVImpl implements IOrderRefundBusiSV {

	private static Logger logger = LoggerFactory.getLogger(OrderRefundBusiSVImpl.class);
	
	@Autowired
	private IOrdOrderAtomSV ordOrderAtomSV;
	
	@Autowired
	private IOrderFrameCoreSV orderFrameCoreSV;
	
	@Autowired
	private IOrdOdProdAtomSV ordOdProdAtomSV;
	
	@Autowired
	private IOrdOdFeeTotalAtomSV  ordOdFeeTotalAtomSV;
	 
	public void fullRefund(OrderRefundRequest request) throws BusinessException, SystemException {
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
		this.updateOrderState(ordOrder, newState, request, chgDesc);
		/* 根据业务类型判断是否退回库存*/
		if(OrdersConstants.OrdOrder.BusiCode.UNSUBSCRIBE_ORDER.equals(ordOrder.getBusiCode())) {
			 /* 库存回退 */
	        List<OrdOdProd> ordOdProds = this.getOrdOdProds(ordOrder.getOrderId());
	        if (CollectionUtil.isEmpty(ordOdProds))
	            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品明细信息["
	                    + ordOrder.getOrderId() + "]");
	        for (OrdOdProd ordOdProd : ordOdProds) {
	            Map<String, Integer> storageNum = JSON.parseObject(ordOdProd.getSkuStorageId(),
	                    new TypeReference<Map<String, Integer>>(){});
	            this.backStorageNum(ordOdProd.getTenantId(), ordOdProd.getSkuId(), storageNum);
	        }
		}
		//TODO
		/*退款到指定的账户中 */
		
	}

	public void partRefund(OrderRefundRequest request) throws BusinessException, SystemException {
		CommonCheckUtils.checkTenantId(request.getTenantId(), ExceptCodeConstants.Special.PARAM_IS_NULL);
		OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(ordOrder==null) {
			logger.error("订单信息不存在[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单信息不存在[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
		}
		OrdOdFeeTotal ordOdFeeTotal = ordOdFeeTotalAtomSV.selectByOrderId(ordOrder.getTenantId(), 
				ordOrder.getOrderId());
		long updateMoney = request.getUpdateMoney();
		/*判断输入费用是否大于之前存在的费用*/
		if(updateMoney>ordOdFeeTotal.getAdjustFee()) {
			logger.error("输入的费用不能大于实际应收的费用,实际应收费用为:"+ordOdFeeTotal.getAdjustFee());
			throw new BusinessException("", "输入的费用不能大于实际应收的费用");
		}
		ordOdFeeTotal.setTotalFee(updateMoney);
		ordOdFeeTotal.setAdjustFee(updateMoney);
		ordOdFeeTotal.setDiscountFee(0);
		ordOdFeeTotal.setOperDiscountFee(0);
		ordOdFeeTotal.setOperDiscountDesc("");
		ordOdFeeTotal.setPaidFee(0);
		ordOdFeeTotal.setPayFee(updateMoney);
		ordOdFeeTotal.setUpdateOperId(request.getOperId());
		//TODO 运费???
		ordOdFeeTotalAtomSV.updateByOrderId(ordOdFeeTotal);
		/* 更新订单状态和写入订单轨迹*/
		String newState = OrdersConstants.OrdOrder.State.FINISH_REPAY;
		String chgDesc = OrdOdStateChg.ChgDesc.ORDER_REVOKE_FINISH_PAY;
		this.updateOrderState(ordOrder, newState, request, chgDesc);
		/* 根据业务类型判断是否退回库存*/
		if(OrdersConstants.OrdOrder.BusiCode.UNSUBSCRIBE_ORDER.equals(ordOrder.getBusiCode())) {
			 /* 库存回退 */
	        List<OrdOdProd> ordOdProds = this.getOrdOdProds(ordOrder.getOrderId());
	        if (CollectionUtil.isEmpty(ordOdProds))
	            throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "商品明细信息["
	                    + ordOrder.getOrderId() + "]");
	        for (OrdOdProd ordOdProd : ordOdProds) {
	            Map<String, Integer> storageNum = JSON.parseObject(ordOdProd.getSkuStorageId(),
	                    new TypeReference<Map<String, Integer>>(){});
	            this.backStorageNum(ordOdProd.getTenantId(), ordOdProd.getSkuId(), storageNum);
	        }
		}
		//TODO
		/*退款到指定的账户中 */
		
	}
	
	@Override
	public void refuseRefund(OrderRefuseRefundRequest request) throws BusinessException, SystemException {
		CommonCheckUtils.checkTenantId(request.getTenantId(), ExceptCodeConstants.Special.PARAM_IS_NULL);
		OrdOrder ordOrder = ordOrderAtomSV.selectByOrderId(request.getTenantId(), request.getOrderId());
		if(ordOrder==null) {
			logger.error("");
			throw new BusinessException(ExceptCodeConstants.Special.NO_RESULT, 
					"订单信息不存在[订单id:"+request.getOrderId()+"租户id:"+request.getTenantId()+"]");
		}
		/* 更新订单状态和写入订单轨迹*/
		String newState = OrdersConstants.OrdOrder.State.AUDIT_FAILURE;
		String chgDesc = OrdOdStateChg.ChgDesc.ORDER_REVOKE_AUDIT_NOT_PASS;
		Timestamp sysDate=DateUtil.getSysDate();
        String orgState = ordOrder.getState();
        ordOrder.setReasonDesc(request.getRefuseReason());
        ordOrder.setState(newState);
        ordOrder.setStateChgTime(sysDate);
        ordOrder.setOperId(request.getOperId());
        ordOrderAtomSV.updateById(ordOrder);
        // 写入订单状态变化轨迹表
        orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState, newState,
        		chgDesc, null, request.getOperId(), null, sysDate);
	}
	
	
	
    /**
     * 更新订单状态
     */
    private void updateOrderState(OrdOrder ordOrder,String newState,
    		OrderRefundRequest request,String chgDesc) {
    	Timestamp sysDate=DateUtil.getSysDate();
        String orgState = ordOrder.getState();
        ordOrder.setReasonDesc(request.getUpdateReason());
        ordOrder.setState(newState);
        ordOrder.setStateChgTime(sysDate);
        ordOrder.setOperId(request.getOperId());
        ordOrderAtomSV.updateById(ordOrder);
        // 写入订单状态变化轨迹表
        orderFrameCoreSV.ordOdStateChg(ordOrder.getOrderId(), ordOrder.getTenantId(), orgState, newState,
        		chgDesc, null, request.getOperId(), null, sysDate);
    }
    
    /**
     * 获取商品明细
     */
    private List<OrdOdProd> getOrdOdProds(Long orderId) throws BusinessException, SystemException {
        OrdOdProdCriteria example = new OrdOdProdCriteria();
        OrdOdProdCriteria.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        return ordOdProdAtomSV.selectByExample(example);
    }
    
    /**
     * 库存回退
     */
    private void backStorageNum(String tenantId, String skuId, Map<String, Integer> storageNum) {
        StorageNumBackReq storageNumBackReq = new StorageNumBackReq();
        storageNumBackReq.setTenantId(tenantId);
        storageNumBackReq.setSkuId(skuId);
        storageNumBackReq.setStorageNum(storageNum);
        IStorageNumSV iStorageNumSV = DubboConsumerFactory.getService(IStorageNumSV.class);
        BaseResponse response = iStorageNumSV.backStorageNum(storageNumBackReq);
        boolean success = response.getResponseHeader().isSuccess();
        String resultMessage = response.getResponseHeader().getResultMessage();
        if (!success)
            throw new BusinessException("", "调用回退库存异常:" + skuId + "错误信息如下:" + resultMessage + "]");
    }
}
