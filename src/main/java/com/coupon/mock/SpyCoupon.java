package com.coupon.mock;

import java.util.ArrayList;
import java.util.List;

import sun.security.x509.IssuingDistributionPointExtension;

public class SpyCoupon implements ICoupon {
	
	List<String> categoryList = new ArrayList<String>();
	
	private int isAppliableCallCount;
	
	public SpyCoupon() {
		categoryList.add("부엌칼");
		categoryList.add("아동 장난감");
		categoryList.add("조리기구");
	}
	
	public boolean isAppliable(Item item) {
		isAppliableCallCount++;	// 호출되면 증가
		if (this.categoryList.contains(item.getCategory())) {
			return true;
		}
		return false;
	}
	
	public int getIsAppliableCallCount() {	// 몇 번 호출됬나?
		return this.isAppliableCallCount;
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
