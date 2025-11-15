package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class JudgeTest {

	static Stream<Arguments> judgeArugments() {
		return Stream.of(
				Arguments.of(new Judge("AA", 10), false),
				Arguments.of(new Judge("BA", 11), true),
				Arguments.of(new Judge("AA", 11), true));
	}

	@ParameterizedTest
	@MethodSource("judgeArugments")
	void test(Judge judge, boolean expected) {
		assertEquals(expected, judge.isApprove());
	}

}
