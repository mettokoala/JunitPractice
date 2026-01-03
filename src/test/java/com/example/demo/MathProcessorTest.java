package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MathProcessorTest {

	@Mock
	Calculator calculator;

	@Mock
	Judge judge;

	@InjectMocks
	MathProcessor mathProcessor;

	@Test
	void testAddAndDouble() {
		when(calculator.add(2, 3)).thenReturn(5);
		int actual = mathProcessor.addAndDouble(2, 3);
		assertEquals(10, actual);
	}

	@Test
	void testDivideAndDouble() {
		when(calculator.divide(10, 2)).thenThrow(new IllegalArgumentException("0で割れません"));
		int actual = mathProcessor.divideAndDouble(10, 2);
		assertEquals(-2, actual);
	}

	@Test
	void testNotifyAndApprove() {
		doNothing().when(judge).notifyResult("OK");
		when(judge.isApprove()).thenReturn(true);

		boolean actual = mathProcessor.notifyAndApprove("OK");
		assertTrue(actual);
	}

	@Test
	void testDoAnswer() {
		doAnswer(inv -> {
			int a = inv.getArgument(0);
			int b = inv.getArgument(1);
			return a + b + 100;
		}).when(calculator).add(1, 2);

		int actual = mathProcessor.addAndDouble(1, 2);
		assertEquals(206, actual);
	}
}
