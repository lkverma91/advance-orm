package com.example.advance_orm.domain.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.example.advance_orm.domain.common.AuditMetadata;
import com.example.advance_orm.domain.payment.PaymentMethod;
import com.example.advance_orm.domain.user.User;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(
		name = "purchase_orders",
		indexes = {
				@Index(name = "idx_purchase_orders_user_id", columnList = "user_id"),
				@Index(name = "idx_purchase_orders_status", columnList = "status")
		}
)
@EntityListeners(AuditingEntityListener.class)
public class PurchaseOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private OrderStatus status = OrderStatus.CREATED;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "metadata", columnDefinition = "json", nullable = false)
	private Map<String, Object> metadata = new HashMap<>();

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PurchaseOrderItem> items = new ArrayList<>();

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "payment_method_id")
	private PaymentMethod paymentMethod;

	@Embedded
	private AuditMetadata audit = new AuditMetadata();

	@Version
	@Column(name = "version", nullable = false)
	private long version;

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Map<String, Object> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, Object> metadata) {
		this.metadata = metadata;
	}

	public List<PurchaseOrderItem> getItems() {
		return items;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public AuditMetadata getAudit() {
		return audit;
	}

	public long getVersion() {
		return version;
	}
}
