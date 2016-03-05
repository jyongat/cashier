package com.test.cashier.active;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.test.cashier.cart.Element;

public abstract class Active {
	
	/**
	 * 活动名
	 */
	protected String activeName;
	
	/**
	 * 活动开始日期
	 */
	protected Date start;
	
	/**
	 * 活动截止日期
	 */
	protected Date end;
	protected float ratio = 0;
	
	private boolean all = false;
	/**
	 * 参与活动的商品
	 */
	protected Set<String> set = new HashSet<String>();

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public void setActiveTime(Date start, Date end) throws Exception {
		if (end.before(start)) {
			throw new Exception("Invalid argument.");
		}
		
		this.start = start;
		this.end = end;
	}
	
	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}
	
	public boolean addWare(String barCode) {
		return set.add(barCode);
	}
	
	public boolean removeWare(String barCode) {
		return set.remove(barCode);
	}
	
	public void setAll(boolean value) {
		all = value;
	}
	
	public boolean ActiveWare(String barCode, Date date) {
		if (date.before(start) || date.after(end)) {
			return false;
		}
		
		if (all) {
			return true;
		}
		
		return set.contains(barCode);
	}
	
	public float getRatio() {
		return ratio;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activeName == null) ? 0 : activeName.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
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
		Active other = (Active) obj;
		if (activeName == null) {
			if (other.activeName != null)
				return false;
		} else if (!activeName.equals(other.activeName))
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}
	
	public abstract float calculate(Element element);
}
