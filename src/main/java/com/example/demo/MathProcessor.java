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

	// 引数マッチャ（any/argThat）用
	public int multiplyIfPositive(Integer a, Integer b) {
		if (a == null || b == null) {
			return 0;
		}
		if (a <= 0 || b <= 0) {
			return 0;
		}
		Integer result = calculator.multiply(a, b);
		return result == null ? 0 : result;
	}

	// when-thenThrow / doThrow 用（例外スタブ）
	public int safeDivide(int a, int b) {
		try {
			return calculator.divide(a, b);
		} catch (RuntimeException e) {
			return -1;
		}
	}

	// Judge 単体のモック用
	public boolean canApprove() {
		return judge.isApprove();
	}

	// InOrder 検証用
	public boolean processScore(int base, int bonus) {
		int total = calculator.add(base, bonus);
		boolean zero = calculator.equalZero(total);
		if (zero) {
			return false;
		}
		return judge.isApprove();
	}

	// void メソッド＋verify/do系の題材
	public boolean notifyAndApprove(String message) {
		judge.notifyResult(message);
		return judge.isApprove();
	}
}
