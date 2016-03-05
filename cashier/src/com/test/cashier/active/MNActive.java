package com.test.cashier.active;

import com.test.cashier.cart.Element;
import com.test.cashier.utils.ConfUtil;
import com.test.cashier.wares.Ware;

/**
 * 买m送n活动
 *
 */
public class MNActive extends Active {
	
	private static String format = ConfUtil.getInstance().getVale(ConfUtil.HINT_MNACTIVE_FORMAT);
	
	private int m;
	private int n;
	private String hint;
	
	public MNActive(int m, int n) throws Exception {
		if (m <= 0 || n <= 0) {
			throw new Exception("Invalid argument.");
		}
		
		this.m = m;
		this.n = n;
		
		this.ratio = n * 1.0f / m;
		this.hint = String.format(format, m, n);
	}
	
	@Override
	public float calculate(Element element) {
		Ware ware = element.getWare();
		float quantity = element.getQuantity();
		int gift = (int)(quantity / (m + n)) * n;		
		float retVal =  ware.getPrice() * (quantity - gift);
		element.setTotal(retVal);
		element.setGift(gift);
		element.setSave(ware.getPrice() *  gift);
		element.setExtra(hint);		
		return retVal;
	}
}
