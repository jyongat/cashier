package com.test.cashier.active;

import com.test.cashier.cart.Element;
import com.test.cashier.wares.Ware;

public class DiscountActive extends Active {
	
	private float discount;
	
	public DiscountActive(float discount) {
		super();
		this.discount = discount;
		this.ratio = discount;
	}
	
	@Override
	public float calculate(Element element) {
		float quantity = element.getQuantity();
		Ware ware = element.getWare();
		
		float retVal =  ware.getPrice() * discount * quantity;
		element.setTotal(retVal);
		element.setSave(ware.getPrice() * (1 - discount) * quantity);
		
		return retVal;
	}
}
