/*package com.ai.slp.order.distributedtask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import com.ai.opt.sdk.dts.base.ITask;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.order.api.ordercancel.interfaces.IOrderCancelSV;

*//**
 * 未支付订单超过30分钟自动关闭 Date: 2016年6月23日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 *//*
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class NoPayOrderAutoCancelTask implements ITask {

    private static final Log log = LogFactory.getLog(NoPayOrderAutoCancelTask.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.error("开始执行订单待支付状态超过30分钟自动关闭定时任务..");
        DubboConsumerFactory.getService(IOrderCancelSV.class).noPayOrderCancel();
        log.error("结束执行订单待支付状态超过30分钟自动撤单定时任务..");

    }

}
*/