package com.ai.slp.order.elasticjob;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.opt.sdk.components.lock.AbstractMutexLock;
import com.ai.opt.sdk.components.lock.RedisMutexLockFactory;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.slp.order.service.business.interfaces.IOfcBusiSV;
import com.alibaba.fastjson.JSON;

/**
 * 订单Task Date: 2017年1月6日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author zhangqiang7
 */
@Service
public class OrderTaskJob {

	private static final Log LOG = LogFactory.getLog(OrderTaskJob.class);

	private final String REDISKEY = "redislock.orderImportJob";

	@Autowired
	IOfcBusiSV ofcSV;

	public BlockingQueue<String[]> ordOrderQueue;

	public BlockingQueue<String[]> ordOdProdQueue;

	public static ExecutorService handlePool;

	/**
	 * 分布式锁
	 * 
	 * @author zhangqiang7
	 * @UCUSER
	 */
	public void orderImportJob() {
		AbstractMutexLock lock = null;
		boolean lockflag = false;
		try {
			lock = RedisMutexLockFactory.getRedisMutexLock(REDISKEY);
			// lock.acquire();//争锁，无限等待
			lockflag = lock.acquire(10, TimeUnit.SECONDS);// 争锁，超时时间10秒。
			if (lockflag) {
				LOG.info("SUCESS线程【" + Thread.currentThread().getName() + "】获取到分布式锁，执行任务");
				run();
			} else {
				LOG.info("FAILURE线程【" + Thread.currentThread().getName() + "】未获取到分布式锁，不执行任务");
			}
		} catch (Exception e) {
			LOG.error("获取分布式锁出错：" + e.getMessage(), e);
		} finally {
			if (lock != null && lockflag) {
				try {
					lock.release();
					LOG.error("释放分布式锁OK");
				} catch (Exception e) {
					LOG.error("释放分布式锁出错：" + e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 现场启动
	 * 
	 * @author zhangqiang7
	 * @UCUSER
	 */
	public void run() {
		LOG.error("订单信息任务开始执行，当前时间戳：" + DateUtil.getSysDate());
		try {
			ordOrderQueue = new LinkedBlockingQueue<String[]>(1000);

			ordOdProdQueue = new LinkedBlockingQueue<String[]>(1000);

			handlePool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
			LOG.error("开始启动读取订单生产者线程");
			// 生产者
			Thread producerThred = new Thread(new OrderReadFileThread(ordOrderQueue));
			producerThred.start();
			// 消费者
			LOG.error("开始插入订单信息，当前时间戳：" + DateUtil.getSysDate());
			while(true){
				String[] queue = ordOrderQueue.poll(30, TimeUnit.SECONDS);
				if (null == queue) {
					LOG.error("订单信息队列为空");
					break;
				}
				handlePool.execute(new OrderThread(queue, ofcSV));
			}
			LOG.error("开始启动订单商品生产者线程");
			// 生产者
			Thread prodProducerThred = new Thread(new OrdProdReadFileThread(ordOdProdQueue));
			prodProducerThred.start();
			// 消费者
			LOG.error("开始插入订单商品信息，当前时间戳：" + DateUtil.getSysDate());
			while(true){
				String[] queue = ordOdProdQueue.poll(30, TimeUnit.SECONDS);
				if (null == queue) {
					LOG.error("订单信息队列为空");
					break;
				}
				handlePool.execute(new OrdOdProdThread(queue, ofcSV));
			}
			// 未消费完等待
			LOG.error("订单商品信息队列为空");
		} catch (Exception e) {
			LOG.error("订单信息任务出错了,错误原因" + JSON.toJSONString(e));
		} finally {
			handlePool.shutdown();
			LOG.error("订单信息任务结束，当前时间戳：" + DateUtil.getSysDate());
		}
	}

}
