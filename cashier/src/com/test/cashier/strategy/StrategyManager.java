package com.test.cashier.strategy;

import java.util.Set;

import com.test.cashier.active.Active;
import com.test.cashier.utils.ConfUtil;
import com.test.cashier.utils.StringUtil;

/**
 * 从远程数据库中拉数据，并可通过rmi等方式接受远程控制，以支持动态修改选取活动策略
 *
 */
public class StrategyManager {
	
	private ActiveStrategy defaultStrategy = null;
	private static StrategyManager instance = null;
	
	private StrategyManager() {
		String strategy = ConfUtil.getInstance().getVale(ConfUtil.KEY_ACTIVE_STRATEGY);
		Class<?> clazz = null;
		try {
			String packageName = StrategyManager.class.getPackage().getName();
			clazz = Class.forName(packageName +  StringUtil.CHARACTER_DOT + strategy);
			defaultStrategy = (ActiveStrategy) clazz.newInstance();
		} catch (ClassNotFoundException e) {
			System.out.println("Invalid strategy.");
		} catch (InstantiationException e) {
			System.out.println("Invalid strategy.");
		} catch (IllegalAccessException e) {
			System.out.println("Invalid strategy.");
		}
	}
	
	public static StrategyManager getInstance() {
		if (null != instance) {
			return instance;
		}
		
		synchronized (StrategyManager.class) {
			if (null == instance) {
				instance = new StrategyManager();
			}
		}
		
		return instance;
	}
	
	public void setDefaultStrategy(ActiveStrategy strategy) {
		defaultStrategy = strategy;
	}
	
	public Active selectActive(Set<Active> set) {
		return defaultStrategy.selectActive(set);
	}
}
