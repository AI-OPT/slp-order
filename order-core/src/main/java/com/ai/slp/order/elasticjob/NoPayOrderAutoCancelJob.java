package com.ai.slp.order.elasticjob;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.order.api.ordercancel.interfaces.IOrderCancelSV;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 未支付订单超过30分钟自动关闭 Date: 2016年6月23日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author gucl
 */
public class NoPayOrderAutoCancelJob implements SimpleJob {

    private static final Log log = LogFactory.getLog(NoPayOrderAutoCancelJob.class);

    @Override
    public void execute(ShardingContext context)  {
        log.error("开始执行订单待支付状态超过30分钟自动关闭定时任务..");
        DubboConsumerFactory.getService(IOrderCancelSV.class).noPayOrderCancel();
        log.error("结束执行订单待支付状态超过30分钟自动撤单定时任务..");

    }

}
