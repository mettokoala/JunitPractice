package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

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
	void test() {
		fail("まだ実装されていません");
	}
}
