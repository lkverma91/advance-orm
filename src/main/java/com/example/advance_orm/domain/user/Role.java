package com.example.advance_orm.domain.user;

import com.example.advance_orm.domain.common.AuditMetadata;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(
		name = "roles",
		indexes = {
				@Index(name = "idx_roles_code", columnList = "code")
		}
)
@EntityListeners(AuditingEntityListener.class)
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code", nullable = false, unique = true, length = 50)
	private String code;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Embedded
	private AuditMetadata audit = new AuditMetadata();

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AuditMetadata getAudit() {
		return audit;
	}
}
