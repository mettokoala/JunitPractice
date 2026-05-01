package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class OrderRepositoryTest {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	OrderRepository orderRepository;

	@BeforeEach
	void clean() {
		jdbcTemplate.execute("TRUNCATE TABLE order_info");
	}

	@Test
	void test() {
		orderRepository.save("りんご", 10);

		Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM order_info", Integer.class);
		assertThat(count).isEqualTo(1);
	}

}
