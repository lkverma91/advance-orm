package com.example.advance_orm.domain.payment;

import com.example.advance_orm.domain.common.AuditMetadata;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment_methods")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "pm_type", discriminatorType = DiscriminatorType.STRING, length = 20)
@EntityListeners(AuditingEntityListener.class)
public abstract class PaymentMethod {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "provider", nullable = false, length = 20)
	private PaymentProvider provider;

	@Embedded
	private AuditMetadata audit = new AuditMetadata();

	protected PaymentMethod() {
	}

	protected PaymentMethod(PaymentProvider provider) {
		this.provider = provider;
	}

	public Long getId() {
		return id;
	}

	public PaymentProvider getProvider() {
		return provider;
	}

	public AuditMetadata getAudit() {
		return audit;
	}
}
