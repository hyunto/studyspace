package com.coupon.mock;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void testAddCoupon() throws Exception {
		User user = new User("area88");
		assertEquals("쿠폰 수령 전", 0, user.getTotalCouponCount());
		
		ICoupon coupon = new DummyCoupon();
		
		user.addCoupon(coupon);
		assertEquals("쿠폰 수령 후", 1, user.getTotalCouponCount());
	}

	// 마지막으로 고객이 받은 쿠폰을 꺼내와서
	// 예상 쿠폰과 일치하는지 확인하는 테스트 
	@Test
	public void testGetLastOccupiedCoupon() throws Exception {
		User user = new User("area88");
		ICoupon eventCoupon = new StubCoupon();
		
		user.addCoupon(eventCoupon);
		ICoupon lastCoupon = user.getLastOccupiedCoupon();	// 마지막에 획득한 쿠폰을 조회
		
		assertEquals("쿠폰 할인율", 7, lastCoupon.getDiscountPercent());
		assertEquals("쿠폰 이름", "VIP 고객 한가위 감사쿠폰", lastCoupon.getName());
	}

	// 특정 쿠폰이 구매 제품에 적용되는지 여부에 따라
	// 결제액이 바뀌는 것을 테스트
	@Test
	public void testGetOrderPrice_discountableItem() throws Exception {
		PriceCalculator calculator = new PriceCalculator();
		
		// new Item(이름, 카테고리, 가격)
		Item item = new Item("LightSavor", "부엌칼", 100000);
		ICoupon coupon = new SpyCoupon();
		
		assertEquals("쿠폰으로 인해 할인된 가격", 93000, calculator.getOrderPrice(item, coupon));
		
		int methodCallCount = ((SpyCoupon)coupon).getIsAppliableCallCount();
		assertEquals("coupon.isAppliable 메소드 호출 횟수", 1, methodCallCount);
	}
	
	// 할인 적용이 안되는 아이템에 대한 테스트 케이스
	@Test
	public void testGetOrderPrice_undiscountableItem() throws Exception {
		PriceCalculator calculator = new PriceCalculator();
		Item item = new Item("R2D2", "알람시계", 20000);
		ICoupon coupon = new SpyCoupon();
		
		assertEquals("쿠폰 적용 안 되는 아이템", 20000, calculator.getOrderPrice(item, coupon));
	}
	
}
