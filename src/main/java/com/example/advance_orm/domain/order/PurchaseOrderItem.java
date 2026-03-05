package com.example.advance_orm.domain.order;

import java.math.BigDecimal;

import com.example.advance_orm.domain.common.AuditMetadata;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchase_order_items")
@EntityListeners(AuditingEntityListener.class)
public class PurchaseOrderItem {

	@EmbeddedId
	private PurchaseOrderItemId id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@MapsId("orderId")
	@JoinColumn(name = "order_id", nullable = false)
	private PurchaseOrder order;

	@Column(name = "sku", nullable = false, length = 80)
	private String sku;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "unit_price", nullable = false, precision = 19, scale = 2)
	private BigDecimal unitPrice;

	@Embedded
	private AuditMetadata audit = new AuditMetadata();

	protected PurchaseOrderItem() {
	}

	public PurchaseOrderItem(PurchaseOrder order, int lineNo, String sku, int quantity, BigDecimal unitPrice) {
		this.order = order;
		this.id = new PurchaseOrderItemId(null, lineNo);
		this.sku = sku;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

	public PurchaseOrderItemId getId() {
		return id;
	}

	public PurchaseOrder getOrder() {
		return order;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public AuditMetadata getAudit() {
		return audit;
	}
}
