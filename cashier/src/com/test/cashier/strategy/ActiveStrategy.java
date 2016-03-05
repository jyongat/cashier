package com.test.cashier.strategy;

import java.util.Set;

import com.test.cashier.active.Active;

public abstract class ActiveStrategy {
	public abstract Active selectActive(Set<Active> set);
}
