package com.ai.slp.order.api.ordercancel.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.ordercancel.interfaces.IOrderCancelSV;
import com.ai.slp.order.api.ordercancel.param.OrderCancelRequest;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderCancelBusiSV;
import com.ai.slp.order.util.CommonCheckUtils;
import com.alibaba.dubbo.config.annotation.Service;

@Service(validation = "true")
@Component
public class OrderCancelSVImpl implements IOrderCancelSV {
    
    private static final Log log = LogFactory.getLog(OrderCancelSVImpl.class);

    @Autowired
    private IOrdOrderAtomSV ordOrderAtomSV;

    @Autowired
    private IOrderCancelBusiSV orderCancelBusiSV;

    @Override
    public BaseResponse noPayOrderCancel() throws BusinessException, SystemException {
        BaseResponse baseResponse = new BaseResponse();
        List<OrdOrder> orders = null;
        try {
            orders = this.getNoPayOrderListInTime(ordOrderAtomSV);
        } catch (Exception ex) {
            log.error("待撤销订单失败", ex);
            throw new SystemException(ex);
        }
        if (CollectionUtil.isEmpty(orders)) {
            return baseResponse;
        }
        for (OrdOrder ordOrder : orders) {
            try {
            	log.info("存在订单信息,订单id:"+ordOrder.getOrderId());
                orderCancelBusiSV.orderCancel(ordOrder);
            } catch (Exception e) {
                log.error("订单自动撤单失败", e);
                continue;
            }
        }
        return baseResponse;
    }
    
    @Override
	public BaseResponse handCancelNoPayOrder(OrderCancelRequest request) throws BusinessException, SystemException {
		/* 1.参数检验*/
		CommonCheckUtils.checkTenantId(request.getTenantId(), ExceptCodeConstants.Special.PARAM_IS_NULL);
		if(request.getOrderId()==0) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "订单Id为空");
		}
		BaseResponse response=new BaseResponse();
		List<OrdOrder> orders=null;
		/* 2.获取未支付的订单*/
		try {
			orders = this.getNoPayOrderList(ordOrderAtomSV,request);
		} catch (Exception e) {
			log.error("待取消订单失败", e);
			throw new SystemException(e);
		}
		/* 3.判断订单是否存在*/
		if(CollectionUtil.isEmpty(orders)) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, 
					"待取消订单为空[orderId:"+request.getOrderId()+"]");
		}
		OrdOrder ordOrder = orders.get(0);
		/* 4.取消订单*/
		orderCancelBusiSV.orderCancel(ordOrder);
		ResponseHeader header=new ResponseHeader(true, ExceptCodeConstants.Special.SUCCESS, "关闭未支付订单");
		response.setResponseHeader(header);
		return response;
	}
    

    /**
     * 获取超过30分钟未支付的订单列表(自动)
     * 
     * @return
     * @author zhangxw
     * @param ordOrderAtomSV
     * @ApiDocMethod
     */
    public List<OrdOrder> getNoPayOrderListInTime(IOrdOrderAtomSV ordOrderAtomSV) {
        Timestamp sysDate = DateUtil.getSysDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sysDate);
        calendar.add(Calendar.MINUTE, -30);
        OrdOrderCriteria example = new OrdOrderCriteria();
        OrdOrderCriteria.Criteria criteria = example.createCriteria();
        criteria.andOrderTimeLessThan(new Timestamp(calendar.getTimeInMillis()));
        criteria.andStateEqualTo(OrdersConstants.OrdOrder.State.WAIT_PAY);
        criteria.andBusiCodeEqualTo(OrdersConstants.OrdOrder.BusiCode.NORMAL_ORDER);
        List<OrdOrder> list = ordOrderAtomSV.selectByExample(example);
        return list;
    }
    
    
    /**
     * 获取未支付的订单列表(手动)
     * 
     * @return
     * @author caofz
     * @param ordOrderAtomSV,request
     * @ApiDocMethod
     */
    public List<OrdOrder> getNoPayOrderList(IOrdOrderAtomSV ordOrderAtomSV,OrderCancelRequest request) {
    	OrdOrderCriteria example=new OrdOrderCriteria();
    	OrdOrderCriteria.Criteria criteria = example.createCriteria();
    	criteria.andTenantIdEqualTo(request.getTenantId());
    	criteria.andBusiCodeEqualTo(OrdersConstants.OrdOrder.BusiCode.NORMAL_ORDER);
    	criteria.andStateEqualTo(OrdersConstants.OrdOrder.State.WAIT_PAY);
    	criteria.andOrderIdEqualTo(request.getOrderId());
    	List<OrdOrder> list = ordOrderAtomSV.selectByExample(example);
    	return list;
    }
}
