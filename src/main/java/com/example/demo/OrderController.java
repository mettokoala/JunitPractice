package com.example.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	public OrderResponse create(@RequestBody OrderRequest request) {
		orderService.create(request.getItemName(), request.getQuantity());
		return new OrderResponse(
				request.getItemName(),
				request.getQuantity());
	}
}
