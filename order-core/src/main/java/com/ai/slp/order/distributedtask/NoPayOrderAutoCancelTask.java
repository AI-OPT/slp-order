//package com.ai.slp.order.distributedtask;
//
//import java.sql.Timestamp;
//import java.util.Calendar;
//import java.util.List;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.ai.opt.base.exception.SystemException;
//import com.ai.opt.sdk.util.CollectionUtil;
//import com.ai.opt.sdk.util.DateUtil;
//import com.ai.slp.order.constants.OrdersConstants;
//import com.ai.slp.order.dao.mapper.bo.OrdOrder;
//import com.ai.slp.order.dao.mapper.bo.OrdOrderCriteria;
//import com.ai.slp.order.service.atom.interfaces.IOrdOrderAtomSV;
//import com.ai.slp.order.service.business.interfaces.OrderCancelBusiSV;
//
///**
// * 未支付订单超过30分钟自动关闭 Date: 2016年6月23日 <br>
// * Copyright (c) 2016 asiainfo.com <br>
// * 
// * @author zhangxw
// */
//public class NoPayOrderAutoCancelTask implements IBaseTask {
//
//    private static final Log log = LogFactory.getLog(NoPayOrderAutoCancelTask.class);
//
//    @Autowired
//    private IOrdOrderAtomSV ordOrderAtomSV;
//
//    @Autowired
//    private OrderCancelBusiSV orderCancelBusiSV;
//
//    @Override
//    public void execute(JobExecutionContext context) throws JobExecutionException {
//        log.debug("开始执行订单待支付状态超过30分钟自动关闭定时任务..");
//        List<OrdOrder> orders = null;
//        try {
//
//            orders = this.getNoPayOrderList();
//        } catch (Exception ex) {
//            log.error("待撤销订单失败", ex);
//            throw new SystemException(ex);
//        }
//        if (CollectionUtil.isEmpty(orders)) {
//            return;
//        }
//        for (OrdOrder ordOrder : orders) {
//            try {
//                orderCancelBusiSV.orderCancel(ordOrder);
//            } catch (Exception e) {
//                log.error("订单自动撤单失败", e);
//                continue;
//            }
//        }
//        log.debug("结束执行订单待支付状态超过30分钟自动撤单定时任务..");
//
//    }
//
//    /**
//     * 获取超过30分钟未支付的订单列表
//     * 
//     * @return
//     * @author zhangxw
//     * @ApiDocMethod
//     */
//    public List<OrdOrder> getNoPayOrderList() {
//        Timestamp sysDate = DateUtil.getSysDate();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(sysDate);
//        calendar.add(Calendar.MINUTE, -30);
//        OrdOrderCriteria example = new OrdOrderCriteria();
//        OrdOrderCriteria.Criteria criteria = example.createCriteria();
//        criteria.andOrderTimeLessThan(new Timestamp(calendar.getTimeInMillis()));
//        criteria.andStateEqualTo(OrdersConstants.OrdOrder.State.WAIT_PAY);
//        criteria.andBusiCodeEqualTo(OrdersConstants.OrdOrder.BusiCode.NORMAL_ORDER);
//        List<OrdOrder> list = ordOrderAtomSV.selectByExample(example);
//        return list;
//    }
//
//}
