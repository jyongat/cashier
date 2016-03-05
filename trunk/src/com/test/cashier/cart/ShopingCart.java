package com.test.cashier.cart;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.test.cashier.utils.JsonUtil;
import com.test.cashier.wares.Ware;
import com.test.cashier.wares.WareManager;

public class ShopingCart {
	
	private float total = 0;
	Map<String, Element> map = new HashMap<String, Element>();
	
	/**
	 * 
	 * @param elements : json format elements
	 */
	public ShopingCart(String strJson) throws Exception {
		loadWares(strJson);
	}
	
	private void loadWares(String strJson) throws Exception {
		Object[] jsonArray = JsonUtil.parseJsonArray(strJson);
		for (int i=0; i<jsonArray.length; i++) {
			if (!(jsonArray[i] instanceof String)) {
				return ;
			}
			
			Element eNew = createElement((String)jsonArray[i]);
			String barCode = eNew.getBarCode();
			Element element = map.get(barCode);
			if (null == element) {
				map.put(barCode, eNew);
				Ware ware = WareManager.getInstance().getWare(barCode);
				if (null == ware) {
					System.out.println("invalid barCode:" + barCode);
				}
				
				eNew.bindWare(ware);
			} else {
				element.setQuantity(element.getQuantity() + eNew.getQuantity());
			}			
		}
	}
	
	private Element createElement(String str) throws Exception {
		String barCode;
		float quantity;
		int pos = str.indexOf("-");
		if (-1 == pos) {
			barCode = str;
			quantity = 1;
		} else {
			barCode = str.substring(0, pos);
			quantity = Float.parseFloat(str.substring(pos + 1));
		}
		
		Element e = new Element(barCode);
		e.setQuantity(quantity);
		
		return e;
	}
	
	public Set<Map.Entry<String, Element>> getElements() {
		return map.entrySet();
	}
	
	public void setTotal(float total) {
		this.total = total;
	}
	
	public float getTotal() {
		return total;
	}
}
