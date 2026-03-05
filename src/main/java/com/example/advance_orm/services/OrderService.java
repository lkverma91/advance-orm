package com.example.advance_orm.services;

import com.example.advance_orm.dto.request.CreateOrderRequest;
import com.example.advance_orm.dto.response.OrderResponse;

public interface OrderService {
	OrderResponse create(CreateOrderRequest request);

	OrderResponse get(Long id, boolean details);
}

