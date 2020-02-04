package com.coupon.mock;

public class DummyCoupon implements ICoupon {

	public String getName() {
		return null;
	}

	public boolean isValid() {
		return false;
	}

	public int getDiscountPercent() {
		return 0;
	}

	public void doExpire() {

	}

	public boolean isAppliable(Item item) {
		return false;
	}

}
