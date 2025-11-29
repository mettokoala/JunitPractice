package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class JudgeTest {

	@Value("${test.enabled}")
	boolean testEnabled;

	static Stream<Arguments> judgeArugments() {
		return Stream.of(
				Arguments.of(new Judge("AA", 10), false),
				Arguments.of(new Judge("BA", 11), false),
				Arguments.of(new Judge("AA", 11), true));
	}

	@ParameterizedTest
	@MethodSource("judgeArugments")
	void test(Judge judge, boolean expected) {
		assertEquals(expected, judge.isApprove());
	}

	@Test
	void testAssumeTrue() {
		assumeTrue(testEnabled, "falseなのでスキップしました:" + testEnabled);
		System.out.println("assumeTrue:trueなので実行されました");
	}

	@Test
	void testAssumingThat() {
		assumingThat(testEnabled, () -> {
			System.out.println("assumingThat:trueなので実行されました");
		});
		System.out.println("assumingThat:常に実行されます");
	}
}
