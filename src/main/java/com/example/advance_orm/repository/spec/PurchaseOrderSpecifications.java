package com.example.advance_orm.repository.spec;

import org.springframework.data.jpa.domain.Specification;

import com.example.advance_orm.domain.order.OrderStatus;
import com.example.advance_orm.domain.order.PurchaseOrder;

public final class PurchaseOrderSpecifications {

	private PurchaseOrderSpecifications() {
	}

	public static Specification<PurchaseOrder> statusIs(OrderStatus status) {
		return (root, query, cb) -> status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
	}

	public static Specification<PurchaseOrder> userIdIs(Long userId) {
		return (root, query, cb) -> userId == null ? cb.conjunction() : cb.equal(root.get("user").get("id"), userId);
	}
}

