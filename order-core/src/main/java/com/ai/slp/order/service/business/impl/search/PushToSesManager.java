package com.ai.slp.order.service.business.impl.search;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.platform.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.order.aync.AyncExector;
import com.ai.slp.order.aync.AyncTask;
import com.ai.slp.order.dao.mapper.bo.OrdOrder;


/**
 * 从数据库推送数据到es(性能备用)
 * @date 2017年5月3日 
 * @author caofz
 */
public class PushToSesManager {
	private static ReentrantLock lock = new ReentrantLock();
	private static int taskNum = 0;
	
	//线程推送数据
	public static void push(final long orderId, final String tenantId, final List<OrdOrder> ordOrderDatas,
			final OrderIndexBusiSVImpl orderIndexBusiSVImpl) {
		AyncExector.submit(new AyncTask() {

			@Override
			public void run() {
				try {
					final ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
					orderIndexBusiSVImpl.pushToSes(ordOrderDatas, orderId, iCacheSV, tenantId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				descTask();
			}
		});
		incrTask();
	}
	
	private static void incrTask() {
		try {
			lock.lock();
			taskNum++;
			printTaskNum();
		} finally {
			lock.unlock();
		}
	}

	private static void descTask() {
		try {
			lock.lock();
			taskNum--;
			printTaskNum();
		} finally {
			lock.unlock();
		}
	}
	
	public static boolean isFinished(){
		try{
			lock.lock();
			return taskNum <= 0;
		} finally {
			lock.unlock();
		}
	}
	
	private static void printTaskNum() {
		System.out.println("当前任务:taskNum="+taskNum);
	}

}
