package com.test.cashier.tools;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import com.test.cashier.active.Active;
import com.test.cashier.active.ActiveManager;
import com.test.cashier.cart.Element;
import com.test.cashier.cart.ShopingCart;
import com.test.cashier.common.TPManager;
import com.test.cashier.strategy.StrategyManager;
import com.test.cashier.wares.Ware;

public class Calculator {
	
	private TPManager tp = null;
	
	private static Calculator instance = null;
	
	private Calculator() {
		tp = new TPManager(Runtime.getRuntime().availableProcessors() * 2 + 1);
	}
	
	public static Calculator getInstance() {
		if (null != instance) {
			return instance;
		}
		
		synchronized (Calculator.class) {
			if (null == instance) {
				instance = new Calculator();
			}
		}
		
		return instance;
	}
	
	
	public float calculate(ShopingCart cart) {
		CountDownLatch latch = new CountDownLatch(cart.getElements().size());
		for (Map.Entry<String, Element> entry : cart.getElements()) {
			tp.castTask(new CalTask(entry.getValue(), latch));
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return -1;
		}
		
		float total = 0;
		for (Map.Entry<String, Element> entry : cart.getElements()) {
			Element element = entry.getValue();
			float subtotal = element.getTotal();
			if (-1 == subtotal) {
				total = -1;
				break;
			}
			
			total += subtotal;
		}
		
		cart.setTotal(total);
		return total;
	}
	
	public void shutdown() {
		tp.waitForever();
		tp = null;
	}
	
	public static float round2(float value) {
		return (float)((double)Math.round(value * 100) / 100);
	}
	
	class CalTask implements Callable<Void> {
		private Element element;
		private CountDownLatch latch;
		
		CalTask(Element element, CountDownLatch latch) {
			this.element = element;
			this.latch = latch;
		}
		
		@Override
		public Void call() throws Exception {
			// TODO 自动生成的方法存根
			String barCode = element.getBarCode();
			Set<Active> actives = ActiveManager.getInstance().getActives(barCode);
			Ware ware = element.getWare();
			if (null == ware) {
				element.setTotal(-1);
				latch.countDown();
				return null;
			}
			
			if (actives.isEmpty()) {
				element.setTotal(element.getQuantity() * ware.getPrice());					
			} else {
				Active active = StrategyManager.getInstance().selectActive(actives);
				active.calculate(element);
			}
			
			latch.countDown();
			return null;
		}
		
	}
}
