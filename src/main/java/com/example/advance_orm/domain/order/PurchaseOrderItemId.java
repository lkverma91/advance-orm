package com.example.advance_orm.domain.order;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PurchaseOrderItemId implements Serializable {

	@Column(name = "order_id", nullable = false)
	private Long orderId;

	@Column(name = "line_no", nullable = false)
	private Integer lineNo;

	protected PurchaseOrderItemId() {
	}

	public PurchaseOrderItemId(Long orderId, Integer lineNo) {
		this.orderId = orderId;
		this.lineNo = lineNo;
	}

	public Long getOrderId() {
		return orderId;
	}

	public Integer getLineNo() {
		return lineNo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PurchaseOrderItemId that = (PurchaseOrderItemId) o;
		return Objects.equals(orderId, that.orderId) && Objects.equals(lineNo, that.lineNo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, lineNo);
	}
}
