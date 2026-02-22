package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
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
		doReturn(10).when(calculator).add(anyInt(), anyInt());
		int actual = mathProcessor.addAndDouble(2, 3);
		assertEquals(20, actual);
		verify(calculator).add(anyInt(), anyInt());
	}

	@Test
	void testDivideAndDouble() {
		doThrow(new IllegalArgumentException("0で割れません")).when(calculator).divide(anyInt(), eq(0));
		int actual = mathProcessor.divideAndDouble(10, 0);
		assertEquals(-2, actual);
		verify(calculator, atLeastOnce()).divide(anyInt(), eq(0));
		verify(judge, never()).isApprove();
	}

	@Test
	void testNotifyAndApprove() {
		doNothing().when(judge).notifyResult(anyString());
		when(judge.isApprove()).thenReturn(true);

		boolean actual = mathProcessor.notifyAndApprove("OK");
		assertTrue(actual);

		InOrder inOrder = inOrder(judge);
		inOrder.verify(judge).notifyResult(anyString());
		inOrder.verify(judge).isApprove();
	}

	@Test
	void testDoAnswer() {
		doAnswer(inv -> {
			int a = inv.getArgument(0);
			int b = inv.getArgument(1);
			return a + b + 100;
		}).when(calculator).add(anyInt(), anyInt());

		int actual = mathProcessor.addAndDouble(1, 2);
		assertEquals(206, actual);

		verify(calculator).add(intThat(a -> a > 0), intThat(b -> b > 0));
	}
}
