package com.test.cashier.tools;

import java.util.Map;

import com.test.cashier.cart.Element;
import com.test.cashier.cart.ShopingCart;
import com.test.cashier.utils.ConfUtil;
import com.test.cashier.utils.StringUtil;
import com.test.cashier.wares.Ware;

/**
 * 下述中文名项可在conf/conf.xml中定义，以实现多语言支持，目前此功能暂不实现
 * @author jyongat
 *
 */
public class Printer {
	
	private static final String HINT_SHOPING_MANIFEST = ConfUtil.getInstance().getVale(ConfUtil.HINT_CART_MANIFEST);
	private static final String HINT_WARE_NAME = ConfUtil.getInstance().getVale(ConfUtil.HINT_NAME);
	private static final String HINT_WARE_QUANTITY = ConfUtil.getInstance().getVale(ConfUtil.HINT_QUANTITY);
	private static final String HINT_WARE_PRICE = ConfUtil.getInstance().getVale(ConfUtil.HINT_PRICE);
	private static final String HINT_WARE_TOTAL = ConfUtil.getInstance().getVale(ConfUtil.HINT_SUBTOTAL);
	private static final String HINT_CART_TOTAL = ConfUtil.getInstance().getVale(ConfUtil.HINT_TOTAL);
	private static final String HINT_MONE_NAME = ConfUtil.getInstance().getVale(ConfUtil.HINT_MONE_NAME);
	private static final String HINT_ACTIVE_SAVE = ConfUtil.getInstance().getVale(ConfUtil.HINT_SAVE);
	private static final String HINT_ACTIVE_GIFT = ConfUtil.getInstance().getVale(ConfUtil.HINT_ACTIVE_GIFT);
	private static final String HINT_ACTIVE_JOIN = ConfUtil.getInstance().getVale(ConfUtil.HINT_ACTIVE_JOIN);	
	
	public static void print(String shopName, ShopingCart cart) {
		if (-1 == cart.getTotal()) {
			System.out.println("*****System error.*********");
			return ;
		}
		
		System.out.println("***<" + shopName + ">" + HINT_SHOPING_MANIFEST + "***");
		float totalSave = 0;
		String strGifts = "";
		for (Map.Entry<String, Element> entry : cart.getElements()) {
			Element element = entry.getValue();
			Ware ware = element.getWare();
			String str = HINT_WARE_NAME + ware.getName();
			if (element.getQuantity() == (int)element.getQuantity()) {
				str += HINT_WARE_QUANTITY + (int)element.getQuantity() + ware.getUnit();
			} else {
				str += HINT_WARE_QUANTITY + StringUtil.round2(element.getQuantity()) + ware.getUnit();
			}
			
			str += HINT_WARE_PRICE + StringUtil.round2(ware.getPrice()) + HINT_MONE_NAME
					+ HINT_WARE_TOTAL + StringUtil.round2(element.getTotal()) + HINT_MONE_NAME;
			if (-1 == element.getGift() && element.getSave() > 0) {
				str += StringUtil.CHARACTER_DOT + HINT_ACTIVE_SAVE
						+ StringUtil.round2(element.getSave()) + HINT_MONE_NAME;
			}
			
			System.out.println(str);
			totalSave += element.getSave();
			if (element.getGift() > 0) {
				str= HINT_WARE_NAME + ware.getName()
						+ HINT_WARE_QUANTITY + element.getGift() + ware.getUnit() 
						+ HINT_ACTIVE_JOIN + element.getExtra();
				strGifts += str + StringUtil.CHARACTER_CRLF;
			}
		}
		
		System.out.println("------------------");
		if (!StringUtil.isEmpty(strGifts)) {
			System.out.println(HINT_ACTIVE_GIFT);
			System.out.print(strGifts);
			System.out.println("------------------");
		}
		
		System.out.println(HINT_CART_TOTAL + StringUtil.round2(cart.getTotal()) + HINT_MONE_NAME);
		if (totalSave > 0) {
			System.out.println(HINT_ACTIVE_SAVE + StringUtil.round2(totalSave) + HINT_MONE_NAME); 
		}
		
		System.out.println("******************");
	}
}
