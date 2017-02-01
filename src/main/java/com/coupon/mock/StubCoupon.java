package com.coupon.mock;

public class StubCoupon implements ICoupon {

	public String getName() {
		return "VIP 고객 한가위 감사쿠폰";
	}

	public boolean isValid() {
		return true;
	}

	public int getDiscountPercent() {
		return 7;
	}

	public void doExpire() {

	}

	public boolean isAppliable(Item item) {
		if (item.getCategory().equals("부엌칼")) {
			return true;
		} else if (item.getCategory().equals("알람시계")) {
			return false;
		}
		return false;
	}

}
