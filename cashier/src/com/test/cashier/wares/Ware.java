package com.test.cashier.wares;

import java.io.Serializable;

import com.test.cashier.tools.Calculator;

public class Ware implements Serializable {
	
	private static final long serialVersionUID = -2045522791780275037L;
	
	private String barCode;
	private String name;
	private String type;
	private float price;
	private String unit;
	
	Ware(String barCode) {
		this.barCode = barCode;
	}
	
	String getBarCode() {
		return barCode;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = Calculator.round2(price);
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((barCode == null) ? 0 : barCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		Ware other = (Ware) obj;
		if (barCode == null) {
			if (other.barCode != null)
				return false;
		} else if (!barCode.equals(other.barCode))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "WareVO [barCode=" + barCode + ", name=" + name + ", type="
				+ type + ", price=" + price + ", unit=" + unit + "]";
	}
}
