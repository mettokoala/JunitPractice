package com.example.demo;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Judge {
	private String code;
	private int value;

	public boolean isApprove() {
		return code != null && code.startsWith("A") && value > 10;
	}
}
