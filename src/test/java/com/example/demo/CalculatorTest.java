package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CalculatorTest {

	@Test
	void testAdd() {
		Calculator calculator = new Calculator();
		assertEquals(3, calculator.add(1, 2));
	}

	@Test
	void testTrue() {
		Calculator calculator = new Calculator();
		assertTrue(calculator.add(3, 5) == 8);
	}

	@Test
	void testFalse() {
		Calculator calculator = new Calculator();
		assertFalse(calculator.add(3, 5) < 8);
	}

	@Test
	void testSubtract() {
		Calculator calculator = new Calculator();
		assertEquals(2, calculator.subtract(3, 1));
	}

	@Test
	void testMultiply() {
		Calculator calculator = new Calculator();
		assertAll(
				() -> assertEquals(2, calculator.multiply(1, 2)),
				() -> assertNull(calculator.multiply(0, null)),
				() -> assertNotNull(calculator.multiply(2, 3)));
	}

	@Test
	void testDivide() {
		Calculator calculator = new Calculator();
		assertEquals(2, calculator.divide(4, 2));
	}

	@Test
	void testDivideByZero() {
		Calculator calculator = new Calculator();
		assertThrows(IllegalArgumentException.class, () -> calculator.divide(3, 0));
	}
}
