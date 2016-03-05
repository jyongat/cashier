package com.test.cashier.test;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

import com.test.cashier.active.Active;
import com.test.cashier.active.ActiveManager;
import com.test.cashier.active.DiscountActive;
import com.test.cashier.active.MNActive;
import com.test.cashier.cart.ShopingCart;
import com.test.cashier.strategy.DiscountActiveFirst;
import com.test.cashier.strategy.StrategyManager;
import com.test.cashier.tools.Calculator;
import com.test.cashier.tools.Printer;
import com.test.cashier.utils.ConfUtil;

/**
 * https://github.com/jyongat/cashier.git
 * 也可修改conf/conf.xml配制文件
 * @author jyongat
 *
 */
public class TestCashier {
	
	public static void main(String[] argv) throws Exception {
		String shopName = ConfUtil.getInstance().getVale(ConfUtil.KEY_SHOP_NAME);
		if (StringUtils.isEmpty(shopName)) {
			return ;
		}
		
		String strJson = "['ITEM000001',"
				+ "'ITEM000001',"
				+ "'ITEM000001',"
				+ "'ITEM000001-2',"
				+ "'ITEM000001',"
				+ "'ITEM000003-2',"
				+ "'ITEM000005',"
				+ "'ITEM000005',"
				+ "'ITEM000005']";
		ShopingCart cart = null;
		try {
			cart = new ShopingCart(strJson);
		} catch (Exception e) {
			System.out.println("load wares error.");
			return ;
		}
		
		int opt = 8;
		if (argv.length > 0) {
			opt = Integer.parseInt(argv[0]);
		}
		
		switch(opt) {
		case 0:
			resetActiveManager();
			break;
		case 1:
			testOnlyDiscountActive();
			break;
		case 2:
			testOnlyMNActive();
			break;
		case 3:
			discountActiveFirst();
			resetActiveManager();
			break;
		case 4:
			discountActiveFirst();
			testOnlyDiscountActive();
			break;
		case 5:
			discountActiveFirst();
			testOnlyMNActive();
			break;
		case 6:
			discountActiveFirst();
			break;
		default:
			break;
		}
		
		Calculator calculator = Calculator.getInstance();
		calculator.calculate(cart);		
		Printer.print(shopName, cart);
		calculator.shutdown();
	}
	
	public static void resetActiveManager() {
		ActiveManager.getInstance().reset();
	}
	
	public static void testOnlyDiscountActive() throws Exception {
		ActiveManager.getInstance().reset();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		Active active = new DiscountActive(0.95f);
		active.setActiveName("active01");
		active.setActiveTime(sdf.parse("2016-01-01"), sdf.parse("2016-12-31"));
		
		active.setAll(true);
		ActiveManager.getInstance().addActive(active);
	}
	
	public static void testOnlyMNActive() throws Exception {
		ActiveManager.getInstance().reset();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
		Active active = new MNActive(2, 1);
		active.setActiveName("active02");
		active.setActiveTime(sdf.parse("2016-01-01"), sdf.parse("2016-12-31"));
		
		active.addWare("ITEM000005");
		active.addWare("ITEM000001");
		
		ActiveManager.getInstance().addActive(active);
	}
	
	public static void discountActiveFirst() {
		StrategyManager.getInstance().setDefaultStrategy(new DiscountActiveFirst());
	}
}
