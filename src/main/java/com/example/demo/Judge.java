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
		// 実際はメール送信・ログ出力などを想定
		System.out.println(message + ", approve=" + isApprove());
	}
}
