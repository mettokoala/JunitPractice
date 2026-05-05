package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
	private OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public void create(String itemName, int quantity) {
		orderRepository.save(itemName, quantity);
	}
}
