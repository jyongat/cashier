package com.test.cashier.cart;

import com.test.cashier.tools.Calculator;
import com.test.cashier.wares.Ware;

public class Element {
	
	/**
	 * barCode即为某种类型商品Id
	 */
	private String barCode;
	private float quantity;
	private int gift = -1;
	private float save = 0;
	private float total = 0;
	private String extra = null;
	private Ware ware = null;
	
	public Element(String barCode) {
		this.barCode = barCode;
	}

	public String getBarCode() {
		return barCode;
	}
	
	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public int getGift() {
		return gift;
	}

	public void setGift(int gift) {
		this.gift = gift;
	}

	public float getSave() {
		return save;
	}

	public void setSave(float save) {
		this.save = Calculator.round2(save);
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = Calculator.round2(total);
	}
	
	public String getExtra() {
		return extra;
	}
	
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
	public void bindWare(Ware ware) {
		this.ware = ware;
	}
	
	public Ware getWare() {
		return ware;
	}
}
