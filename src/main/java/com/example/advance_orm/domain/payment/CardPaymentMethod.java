package com.example.advance_orm.domain.payment;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CARD")
public class CardPaymentMethod extends PaymentMethod {

	@Column(name = "card_last4", length = 4)
	private String last4;

	@Column(name = "card_network", length = 20)
	private String network;

	protected CardPaymentMethod() {
		super(PaymentProvider.CARD);
	}

	public CardPaymentMethod(String last4, String network) {
		super(PaymentProvider.CARD);
		this.last4 = last4;
		this.network = network;
	}

	public String getLast4() {
		return last4;
	}

	public String getNetwork() {
		return network;
	}
}
