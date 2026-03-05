package com.example.advance_orm.domain.user;

import java.time.Instant;

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
@Table(name = "user_roles")
@EntityListeners(AuditingEntityListener.class)
public class UserRole {

	@EmbeddedId
	private UserRoleId id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@MapsId("userId")
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@MapsId("roleId")
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@Column(name = "assigned_at", nullable = false)
	private Instant assignedAt = Instant.now();

	@Embedded
	private AuditMetadata audit = new AuditMetadata();

	protected UserRole() {
	}

	public UserRole(User user, Role role) {
		this.user = user;
		this.role = role;
		this.id = new UserRoleId();
	}

	public UserRoleId getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public Role getRole() {
		return role;
	}

	public Instant getAssignedAt() {
		return assignedAt;
	}

	public void setAssignedAt(Instant assignedAt) {
		this.assignedAt = assignedAt;
	}

	public AuditMetadata getAudit() {
		return audit;
	}
}
