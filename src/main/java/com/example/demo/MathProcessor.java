package com.example.demo;

public class MathProcessor {

	private final Calculator calculator;
	private final Judge judge;

	public MathProcessor(Calculator calculator, Judge judge) {
		this.calculator = calculator;
		this.judge = judge;
	}

	public int addAndDouble(int a, int b) {
		int sum = calculator.add(a, b);
		return sum * 2;
	}
}
