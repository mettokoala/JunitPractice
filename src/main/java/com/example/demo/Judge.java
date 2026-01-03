package com.example.demo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Judge {
	private String code;
	private int value;

	public boolean isApprove() {
		return code != null && code.startsWith("A") && value > 10;
	}

	public void notifyResult(String message) {
		System.out.println(message);
	}
}
