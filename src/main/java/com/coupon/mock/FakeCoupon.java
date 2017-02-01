package com.coupon.mock;

import java.util.ArrayList;
import java.util.List;

public class FakeCoupon implements ICoupon {
	
	List<String> categoryList = new ArrayList<String>();
	
	public FakeCoupon() {
		categoryList.add("부엌칼");
		categoryList.add("아동 장난감");
		categoryList.add("조리기구");
	}
	
	public boolean isAppliable(Item item) {
		if (this.categoryList.contains(item.getCategory())) {
			return true;
		}
		return false;
	}

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

}
