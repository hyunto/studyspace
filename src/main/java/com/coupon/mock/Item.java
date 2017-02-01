package com.coupon.mock;

public class Item {

	private String name;
	
	private String category;
	
	private int price;
	
	public Item(String name, String category, int price) {
		this.name = name;
		this.category = category;
		this.price = price;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCategory() {
		return this.category;
	}

	public int getPrice() {
		return this.price;
	}

}
