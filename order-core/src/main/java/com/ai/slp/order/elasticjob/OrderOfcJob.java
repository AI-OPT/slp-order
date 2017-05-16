package com.ai.slp.order.elasticjob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * ofc定时任务,触发订单任务和订单商品任务
 * 
 * @author Zh
 *
 */

@Service
public class OrderOfcJob implements SimpleJob {

	private static final Logger log = LoggerFactory.getLogger(OrderOfcJob.class);

	@Autowired
	OrderTaskJob orderTaskJob;

	@Override
	public void execute(ShardingContext context) {
		log.error("执行订单ofc任务");
		orderTaskJob.run();
		log.error("结束执行订单ofc任务..");

	}

}
