package com.example.advance_orm.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.example.advance_orm.domain.order.OrderStatus;
import com.example.advance_orm.domain.order.PurchaseOrder;
import com.example.advance_orm.domain.order.PurchaseOrderItem;
import com.example.advance_orm.domain.payment.CardPaymentMethod;
import com.example.advance_orm.domain.payment.PaymentMethod;
import com.example.advance_orm.domain.payment.UpiPaymentMethod;

public record OrderResponse(
		Long id,
		Long userId,
		OrderStatus status,
		Map<String, Object> metadata,
		Payment payment,
		List<Item> items,
		Audit audit
) {
	public record Item(int lineNo, String sku, int quantity, BigDecimal unitPrice) {
		static Item from(PurchaseOrderItem i) {
			return new Item(i.getId().getLineNo(), i.getSku(), i.getQuantity(), i.getUnitPrice());
		}
	}

	public record Payment(String type, Map<String, Object> details) {
		static Payment from(PaymentMethod pm) {
			if (pm == null) return null;
			if (pm instanceof CardPaymentMethod c) {
				return new Payment("CARD", Map.of("last4", c.getLast4(), "network", c.getNetwork()));
			}
			if (pm instanceof UpiPaymentMethod u) {
				return new Payment("UPI", Map.of("vpa", u.getVpa()));
			}
			return new Payment(pm.getProvider().name(), Map.of());
		}
	}

	public record Audit(Instant createdAt, String createdBy, Instant updatedAt, String updatedBy) {
	}

	public static OrderResponse from(PurchaseOrder order) {
		return new OrderResponse(
				order.getId(),
				order.getUser().getId(),
				order.getStatus(),
				order.getMetadata(),
				Payment.from(order.getPaymentMethod()),
				order.getItems().stream().map(Item::from).toList(),
				new Audit(
						order.getAudit().getCreatedAt(),
						order.getAudit().getCreatedBy(),
						order.getAudit().getUpdatedAt(),
						order.getAudit().getUpdatedBy()
				)
		);
	}
}

