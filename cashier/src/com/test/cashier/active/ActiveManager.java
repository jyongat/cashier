package com.test.cashier.active;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.test.cashier.db.DBHelper;

/**
 * 从远程数据库中拉数据，并可通过rmi等方式接受远程控制，以支持动态添加、修改、删除系统支持的活动类型
 *
 */
public class ActiveManager {

	private Set<Active> set = null;
	
	private static ActiveManager instance = null;
	
	private ActiveManager() {
		Date today = new Date();
		set = DBHelper.getInstance().loadValidActivity(today);
		
		// 临时添加几个活动供测试使用
		addTestCase();
	}

	private void addTestCase() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		Active active = new DiscountActive(0.95f);
		active.setActiveName("active01");
		try {
			active.setActiveTime(sdf.parse("2016-01-01"), sdf.parse("2016-12-31"));
			active.addWare("ITEM000003");
			active.addWare("ITEM000005");
			set.add(active);
			
			active = new MNActive(2, 1);
			active.setActiveName("active02");		
			active.setActiveTime(sdf.parse("2016-01-01"), sdf.parse("2016-12-31"));
			active.addWare("ITEM000005");
			active.addWare("ITEM000001");
			set.add(active);
		} catch (Exception e) {
			System.out.println("Fail to create active for invalid argument.");
		}
	}
	
	public static ActiveManager getInstance() {
		if (null != instance) {
			return instance;
		}
		
		synchronized (ActiveManager.class) {
			if (null == instance) {
				instance = new ActiveManager();
			}
		}
		
		return instance;
	}
	
	/**
	 * 获取某种商品的所有优惠活动
	 * @param barCode
	 * @return
	 */
	public Set<Active> getActives(String barCode) {
		Set<Active> result = new HashSet<Active>();
		Date date = new Date();
		for (Active active : set) {
			if (!active.ActiveWare(barCode, date)) {
				continue ;
			}
			
			result.add(active);
		}
		
		return result;
	}
	
	public boolean removeActive(Active active) {
		return set.remove(active);
	}
	
	public boolean addActive(Active active) {
		return set.add(active);
	}
	
	public void reset() {
		set.clear();
	}
}
