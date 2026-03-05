package com.example.advance_orm.domain.common;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AuditMetadata {

	@CreatedDate
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@CreatedBy
	@Column(name = "created_by", nullable = false, updatable = false, length = 100)
	private String createdBy;

	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	@LastModifiedBy
	@Column(name = "updated_by", nullable = false, length = 100)
	private String updatedBy;

	public Instant getCreatedAt() {
		return createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
}
