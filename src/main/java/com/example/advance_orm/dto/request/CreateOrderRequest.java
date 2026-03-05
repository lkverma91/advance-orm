package com.example.advance_orm.dto.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateOrderRequest(
		@NotNull Long userId,
		@NotNull Map<String, Object> metadata,
		@NotNull @Size(min = 1) List<@Valid Item> items,
		@NotNull @Valid Payment payment
) {
	public record Item(
			@NotBlank @Size(max = 80) String sku,
			@Min(1) int quantity,
			@NotNull BigDecimal unitPrice
	) {
	}

	public enum PaymentType {
		CARD,
		UPI
	}

	public record Payment(
			@NotNull PaymentType type,
			@Size(min = 4, max = 4) String cardLast4,
			@Size(max = 20) String cardNetwork,
			@Size(max = 200) String vpa
	) {
	}
}

