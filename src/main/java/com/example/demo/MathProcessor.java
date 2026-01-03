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

	public int divideAndDouble(int a, int b) {
		try {
			int divide = calculator.divide(a, b);
			if (judge.isApprove()) {
				return divide * 2;
			}
			return -1;
		} catch (IllegalArgumentException e) {
			return -2;
		}
	}

	public boolean notifyAndApprove(String message) {
		judge.notifyResult(message);
		return judge.isApprove();
	}
}