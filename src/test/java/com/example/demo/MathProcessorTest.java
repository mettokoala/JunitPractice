package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MathProcessorTest {

	@Spy
	Calculator calculator = new Calculator();

	@Mock
	Judge judge;

	@InjectMocks
	MathProcessor mathProcessor;

	@Test
	void testAddAndDouble() {
		doReturn(10).when(calculator).add(2, 3);
		int actual = mathProcessor.addAndDouble(2, 3);
		assertEquals(20, actual);
	}

	@Test
	void testDivideAndDouble() {
		doThrow(new IllegalArgumentException("0で割れません")).when(calculator).divide(10, 0);
		int actual = mathProcessor.divideAndDouble(10, 0);
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
