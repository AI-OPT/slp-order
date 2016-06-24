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
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.api.ordercancel.interfaces.IOrderCancelSV;
import com.ai.slp.order.constants.OrdersConstants;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;
import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
import com.ai.slp.order.distributedtask.NoPayOrderAutoCancelTask;
import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
import com.ai.slp.order.service.business.interfaces.IOrderCancelBusiSV;
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
            orders = this.getNoPayOrderList(ordOrderAtomSV);
        } catch (Exception ex) {
            log.error("待撤销订单失败", ex);
            throw new SystemException(ex);
        }
        if (CollectionUtil.isEmpty(orders)) {
            return baseResponse;
        }
        for (OrdOrder ordOrder : orders) {
            try {
                orderCancelBusiSV.orderCancel(ordOrder);
            } catch (Exception e) {
                log.error("订单自动撤单失败", e);
                continue;
            }
        }
        return baseResponse;
    }

    /**
     * 获取超过30分钟未支付的订单列表
     * 
     * @return
     * @author zhangxw
     * @param ordOrderAtomSV
     * @ApiDocMethod
     */
    public List<OrdOrder> getNoPayOrderList(IOrdOrderAtomSV ordOrderAtomSV) {
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

}
