package com.test.cashier.db;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.test.cashier.active.Active;
import com.test.cashier.wares.Ware;

/**
 * 数据库操作单例
 *
 */
public class DBHelper {
	
	private static DBHelper instance = null;
	
	public static DBHelper getInstance() {
		if (null != instance) {
			return instance;
		}
		
		synchronized (DBHelper.class) {
			if (null == instance) {
				instance = new DBHelper();
				return instance;
			}
		}
		
		return instance;
	}
	
	/**
	 * 更 新商品
	 * @param ware
	 * @return
	 */
	public boolean updateWare(Ware ware) {
		return true;
	}
	
	/**
	 * 插入新商品
	 * @param ware
	 * @return
	 */
	public boolean insertWare(Ware ware) {
		return true;
	}
	
	/**
	 * 根据日期加载有效活动
	 * @param date
	 * @return
	 */
	public Set<Active> loadValidActivity(Date date) {
		return new HashSet<Active>();
	}
}
