package com.example.advance_orm.domain.user;

import com.example.advance_orm.domain.common.AuditMetadata;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(
		name = "user_addresses",
		indexes = {
				@Index(name = "idx_user_addresses_user_id", columnList = "user_id")
		}
)
@EntityListeners(AuditingEntityListener.class)
public class UserAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "label", nullable = false, length = 100)
	private String label;

	@Column(name = "line1", nullable = false, length = 200)
	private String line1;

	@Column(name = "city", nullable = false, length = 100)
	private String city;

	@Column(name = "postal_code", nullable = false, length = 20)
	private String postalCode;

	@Column(name = "country", nullable = false, length = 2)
	private String country;

	@Embedded
	private AuditMetadata audit = new AuditMetadata();

	public Long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public AuditMetadata getAudit() {
		return audit;
	}
}
