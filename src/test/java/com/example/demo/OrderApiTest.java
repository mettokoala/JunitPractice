package com.example.demo;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrderApiTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() {
		jdbcTemplate.execute("TRUNCATE TABLE order_info");
	}

	@Test
	void 注文登録APIを呼び出すとDBに登録されること() throws Exception {
		mockMvc.perform(post("/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "itemName": "ノートPC",
						  "quantity": 2
						}
						"""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.itemName").value("ノートPC"))
				.andExpect(jsonPath("$.quantity").value(2));

		Integer count = jdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM order_info WHERE item_name = ? AND quantity = ?",
				Integer.class,
				"ノートPC",
				2);

		assertThat(count).isEqualTo(1);
	}
}
