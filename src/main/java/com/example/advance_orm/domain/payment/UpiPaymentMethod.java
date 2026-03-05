package com.example.advance_orm.domain.payment;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("UPI")
public class UpiPaymentMethod extends PaymentMethod {

	@Column(name = "vpa", length = 200)
	private String vpa;

	protected UpiPaymentMethod() {
		super(PaymentProvider.UPI);
	}

	public UpiPaymentMethod(String vpa) {
		super(PaymentProvider.UPI);
		this.vpa = vpa;
	}

	public String getVpa() {
		return vpa;
	}
}
