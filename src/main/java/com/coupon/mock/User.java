package com.coupon.mock;

import java.util.ArrayList;
import java.util.List;

public class User {

	private String name;
	
	private List<ICoupon> coupons = new ArrayList<ICoupon>();
	
	public User(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public int getTotalCouponCount() {
		return coupons.size();
	}

	public void addCoupon(ICoupon coupon) {
		coupons.add(coupon);
		
	}

	public ICoupon getLastOccupiedCoupon() {
		return coupons.get(coupons.size() -1);
	}
}
