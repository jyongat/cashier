package com.test.cashier.wares;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.test.cashier.db.DBHelper;

/**
 * 从远程数据库中拉数据，并可通过rmi等方式接受远程控制，以支动动态添加、修改、删除不同种类商品
 *
 */
public class WareManager {
	
	//key为barCode;
	private Map<String, Ware> map = new ConcurrentHashMap<String, Ware>();
	
	private static WareManager instance = null;
	
	private WareManager() {
		init();
	}
	// 从远程数据库中加载所有商品类型
	private void init() {
		Ware ware = new Ware("ITEM000001");
		ware.setName("羽毛球");
		ware.setPrice(1.00f);
		ware.setUnit("个");
		map.put(ware.getBarCode(), ware);
		
		ware = new Ware("ITEM000002");
		ware.setName("大米");
		ware.setPrice(2.35f);
		ware.setUnit("斤");
		map.put(ware.getBarCode(), ware);
		
		ware = new Ware("ITEM000003");
		ware.setName("苹果");
		ware.setPrice(5.50f);
		ware.setUnit("斤");
		map.put(ware.getBarCode(), ware);
		
		ware = new Ware("ITEM000004");
		ware.setName("香蕉");
		ware.setPrice(2.80f);
		ware.setUnit("斤");
		map.put(ware.getBarCode(), ware);
		
		ware = new Ware("ITEM000005");
		ware.setName("可口可乐");
		ware.setPrice(3.00f);
		ware.setUnit("瓶");
		map.put(ware.getBarCode(), ware);
	}
	
	public void uninit() {
		map.clear();
	}
	
	public static WareManager getInstance() {
		if (null != instance) {
			return instance;
		}
		
		synchronized (WareManager.class) {
			if (null == instance) {
				instance = new WareManager();
			}
		}
		return instance;
	}
	
	public Ware getWare(String barCode) {
		return map.get(barCode);
	}
	
	/**
	 * 调整商品价格
	 * @param barCode
	 * @param price
	 */
	public boolean adjustPrice(String barCode, float price) {
		// 无效条码
		Ware ware = getWare(barCode);
		if (null == ware) {
			return false;
		}
		
		if (ware.getPrice() == price) {
			return true;
		}
		
		float oldPrice = ware.getPrice();
		ware.setPrice(price);
		if (DBHelper.getInstance().updateWare(ware)) {
			return true;
		}
		
		ware.setPrice(oldPrice);
		return false;
	}
	
	public boolean insertWare(Ware ware) {
		if (map.containsKey(ware.getBarCode())) {
			return false;
		}
		
		if (!insertWare(ware)) {
			return false;
		}
		
		map.put(ware.getBarCode(), ware);
		
		return true;
	}
}
