package com.test.cashier.common;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TPManager {
	
	private ExecutorService executor = null;
	
	public TPManager(int count) {
		if (count < 5) {
			count = 5;
		} else if (count > 16) {
			count = 16;
		}
		
		executor = Executors.newFixedThreadPool(count);
	}
	
	public void castTask(Callable<Void> task) {
		if (executor.isShutdown()) {
			return ;
		}
		
		executor.submit(task);
	}
	
	public void waitForever() {
		executor.shutdown();
		try {
			executor.awaitTermination(24, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			System.out.println("Error to wait tp over.");
		}
	}
}
