package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ShippingServiceTest {
	private final ShippingService shippingService = new ShippingService();

	@Test
	void testCalculateShippingFee() {

		assertEquals(0, shippingService.calculateShippingFee(12000));
	}

	@Test
	void shippingFeeIs500() {
		assertEquals(500, shippingService.calculateShippingFee(5000));
	}

}
