package com.example.demo;

public class Calculator {

	public int add(int a, int b) {
		return a + b;
	}

	public int subtract(int a, int b) {
		return a - b;
	}

	public Integer multiply(Integer a, Integer b) {
		if (a == null || b == null) {
			return null;
		}
		return a * b;
	}

	public int divide(int a, int b) {
		if (b == 0) {
			throw new IllegalArgumentException("0で割れません");
		}
		return a / b;
	}
}
