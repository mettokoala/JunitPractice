package com.example.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

	private final JdbcTemplate jdbcTemplate;

	public OrderRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void save(String itemName, int quantity) {
		jdbcTemplate.update("INSERT INTO order_info(item_name, quantity) VALUES (?, ?)", itemName, quantity);
	}
}
