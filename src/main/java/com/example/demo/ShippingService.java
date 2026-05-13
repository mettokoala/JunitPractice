package com.example.demo;

public class ShippingService {

	public int calculateShippingFee(int price) {
		if (price >= 10000) {
			return 0;
		}
		return 500;
	}
}
