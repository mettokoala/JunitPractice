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
public class SchemaInitSmokeTest {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@BeforeEach
	void clean() {
		jdbcTemplate.execute("TRUNCATE TABLE order_info");
	}

	@Test
	void schema_sqlで作ったテーブルにSELECTできる() {
		Integer count = jdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM order_info",
				Integer.class);

		assertThat(count).isEqualTo(0);
	}
}
