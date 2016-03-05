package com.test.cashier.strategy;

import java.util.Iterator;
import java.util.Set;

import com.test.cashier.active.Active;
import com.test.cashier.active.DiscountActive;

public class DiscountActiveFirst extends ActiveStrategy {

	@Override
	public Active selectActive(Set<Active> set) {
		Iterator<Active> iter = set.iterator();
		Active active = null;
		while (iter.hasNext()) {
			active = iter.next();
			if (active instanceof DiscountActive) {
				break;
			}
		}
		
		return active;
	}

}
