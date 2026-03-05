package com.example.advance_orm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.advance_orm.dto.request.CreateOrderRequest;
import com.example.advance_orm.dto.response.OrderResponse;
import com.example.advance_orm.services.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrdersController {

	private final OrderService orderService;

	public OrdersController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderResponse create(@Valid @RequestBody CreateOrderRequest request) {
		return orderService.create(request);
	}

	@GetMapping("/{id}")
	public OrderResponse get(
			@PathVariable("id") Long id,
			@RequestParam(name = "details", defaultValue = "false") boolean details
	) {
		return orderService.get(id, details);
	}
}

