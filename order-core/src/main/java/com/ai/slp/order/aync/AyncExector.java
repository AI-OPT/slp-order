package com.ai.slp.order.aync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AyncExector {
	private static ExecutorService pool = Executors.newFixedThreadPool(10);
	
	public static void submit(AyncTask task){
		try{
			pool.execute(task);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
