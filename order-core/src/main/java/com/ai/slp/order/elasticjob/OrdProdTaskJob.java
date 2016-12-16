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

@Service
public class OrdProdTaskJob {

	private static final Log LOG = LogFactory.getLog(OrdProdTaskJob.class);

	private final String REDISKEY = "redislock.ordProdImportJob";
	@Autowired
	IOfcBusiSV ofcSV;

	public BlockingQueue<String[]> ordOdProdQueue;

	public static ExecutorService handlePool;

	public void ordProdImportJob() {
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

	public void run() {
		LOG.error("订单商品任务开始执行，当前时间戳：" + DateUtil.getSysDate());
		try {
			ordOdProdQueue = new LinkedBlockingQueue<String[]>(1000);

			handlePool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
			// 生产者
			Thread producerThred = new Thread(new OrdProdReadFileThread(ordOdProdQueue));
			producerThred.start();
			// 消费者
			LOG.error("开始插入订单商品信息，当前时间戳：" + DateUtil.getSysDate());
			for (int i = 0; i < Runtime.getRuntime().availableProcessors() * 2; i++) {
				handlePool.execute(new OrdOdProdThread(ordOdProdQueue, ofcSV));
			}
			// 未消费完等待
			while (!ordOdProdQueue.isEmpty()) {
				Thread.sleep(10 * 1000);
			}
			LOG.error("订单商品信息队列为空");
		} catch (Exception e) {
			LOG.error("订单商品线程报错了,错误信息" + e.getMessage());
		} finally {
			handlePool.shutdown();
			LOG.error("订单商品任务结束，当前时间戳：" + DateUtil.getSysDate());
		}
	}

}
