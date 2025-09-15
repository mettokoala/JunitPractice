package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CalculatorTest {

	private static Calculator calculator;

	@BeforeAll
	static void beforeAll() {
		calculator = new Calculator();
	}

	@AfterAll
	static void afterAll() {
		System.out.println("afterAll");
	}

	@BeforeEach
	void beforeEach() {
		System.out.println("beforeEach");
	}

	@AfterEach
	void afterEach() {
		System.out.println("afterEach");
	}

	@Test
	@Tag("add")
	void testAdd() {
		assertEquals(3, calculator.add(1, 2));
	}

	@Test
	@Tag("add")
	void testTrue() {
		assertTrue(calculator.add(3, 5) == 8);
	}

	@Test
	@Tag("add")
	void testFalse() {
		assertFalse(calculator.add(3, 5) < 8);
	}

	@Test
	void testSubtract() {
		assertEquals(2, calculator.subtract(3, 1));
	}

	@Test
	void testMultiply() {
		assertAll(
				() -> assertEquals(2, calculator.multiply(1, 2)),
				() -> assertNull(calculator.multiply(0, null)),
				() -> assertNotNull(calculator.multiply(2, 3)));
	}

	@Test
	void testDivide() {
		assertEquals(2, calculator.divide(4, 2));
	}

	@Test
	void testDivideByZero() {
		assertThrows(IllegalArgumentException.class, () -> calculator.divide(3, 0));
	}
}
