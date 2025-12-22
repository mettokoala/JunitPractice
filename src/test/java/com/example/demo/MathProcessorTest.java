package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Execution(ExecutionMode.CONCURRENT)
class MathProcessorTest {

	@Mock
	Calculator calculator;

	@Mock
	Judge judge;

	@Spy
	Calculator spyCalculator = new Calculator();

	MathProcessor processor;
	MathProcessor spyProcessor;

	@BeforeEach
	void setup() {
		processor = new MathProcessor(calculator, judge);
		spyProcessor = new MathProcessor(spyCalculator, judge);
	}

	// ① when-thenReturn
	@Test
	void when_thenReturn_基本とverify() {
		System.out.println("THREAD=" + Thread.currentThread().getName());

		when(calculator.add(2, 3)).thenReturn(5);

		int actual = processor.addAndDouble(2, 3);

		assertEquals(10, actual);
		verify(calculator).add(2, 3);
		verifyNoMoreInteractions(calculator, judge);
	}

	// ② when-thenThrow
	@Test
	void when_thenThrow_例外スタブ() {
		System.out.println("THREAD=" + Thread.currentThread().getName());

		when(calculator.divide(anyInt(), eq(0)))
				.thenThrow(new IllegalArgumentException("0で割れません"));

		int result = processor.safeDivide(10, 0);

		assertEquals(-1, result);
		verify(calculator).divide(10, 0);
		verifyNoMoreInteractions(calculator, judge);
	}

	// ③ any / argThat / eq
	@Test
	void 引数マッチャ_any_eq_argThat() {
		System.out.println("THREAD=" + Thread.currentThread().getName());

		when(judge.isApprove()).thenReturn(true);
		when(calculator.multiply(10, 2)).thenReturn(20);

		int multi = processor.multiplyIfPositive(10, 2);
		boolean approved = processor.canApprove();

		assertEquals(20, multi);
		assertTrue(approved);

		verify(calculator).multiply(
				argThat(a -> a > 0),
				argThat(b -> b > 0));
		verify(judge).isApprove();
		verifyNoMoreInteractions(calculator, judge);
	}

	// ④ verify バリエーション
	@Test
	void verify_バリエーション() {
		System.out.println("THREAD=" + Thread.currentThread().getName());

		when(calculator.add(anyInt(), anyInt())).thenReturn(3);
		when(judge.isApprove()).thenReturn(true);

		processor.addAndDouble(1, 2);
		processor.addAndDouble(2, 3);
		processor.canApprove();

		verify(calculator, times(2)).add(anyInt(), anyInt());
		verify(calculator, never()).divide(anyInt(), anyInt());
		verify(judge, atLeastOnce()).isApprove();
		verify(calculator, atMost(3)).add(anyInt(), anyInt());
		verifyNoMoreInteractions(calculator, judge);
	}

	// ⑤ InOrder
	@Test
	void InOrderで呼び出し順を検証() {
		System.out.println("THREAD=" + Thread.currentThread().getName());

		when(calculator.add(5, 6)).thenReturn(11);
		when(calculator.equalZero(11)).thenReturn(false);
		when(judge.isApprove()).thenReturn(true);

		boolean result = processor.processScore(5, 6);

		assertTrue(result);

		InOrder order = inOrder(calculator, judge);
		order.verify(calculator).add(5, 6);
		order.verify(calculator).equalZero(11);
		order.verify(judge).isApprove();

		verifyNoMoreInteractions(calculator, judge);
	}

	// ⑥ Spy + doReturn
	@Test
	void spy_doReturn_実メソッド抑制() {
		System.out.println("THREAD=" + Thread.currentThread().getName());

		doReturn(10).when(spyCalculator).add(1, 2);

		int result = spyProcessor.addAndDouble(1, 2);

		assertEquals(20, result);

		verify(spyCalculator).add(1, 2);
		verifyNoMoreInteractions(spyCalculator, judge);
	}

	// ⑦ Spy + doThrow
	@Test
	void spy_doThrow_例外スタブ() {
		System.out.println("THREAD=" + Thread.currentThread().getName());

		doThrow(new RuntimeException("テストエラー"))
				.when(spyCalculator).divide(10, 0);

		int result = spyProcessor.safeDivide(10, 0);

		assertEquals(-1, result);
		verify(spyCalculator).divide(10, 0);
		verifyNoMoreInteractions(spyCalculator, judge);
	}

	// ⑧ doNothing
	@Test
	void doNothing_voidメソッド抑制() {
		System.out.println("THREAD=" + Thread.currentThread().getName());

		when(judge.isApprove()).thenReturn(true);
		doNothing().when(judge).notifyResult(anyString());

		boolean result = processor.notifyAndApprove("OK");

		assertTrue(result);
		verify(judge).notifyResult("OK");
		verify(judge).isApprove();
		verifyNoMoreInteractions(calculator, judge);
	}

	// ⑨ void doThrow
	@Test
	void doThrow_voidメソッドで例外() {
		System.out.println("THREAD=" + Thread.currentThread().getName());

		doThrow(new RuntimeException("通知失敗"))
				.when(judge).notifyResult(anyString());

		assertThrows(RuntimeException.class,
				() -> processor.notifyAndApprove("NG"));

		verify(judge).notifyResult("NG");
		verifyNoMoreInteractions(calculator, judge);
	}

	// ⑩ doAnswer
	@Test
	void doAnswer_動的スタブ() {
		System.out.println("THREAD=" + Thread.currentThread().getName());

		doAnswer(inv -> {
			int a = inv.getArgument(0, Integer.class);
			int b = inv.getArgument(1, Integer.class);
			return a + b + 100;
		}).when(calculator).add(anyInt(), anyInt());

		int result = processor.addAndDouble(1, 2);

		assertEquals(206, result);
		verify(calculator).add(1, 2);
		verifyNoMoreInteractions(calculator, judge);
	}
}
