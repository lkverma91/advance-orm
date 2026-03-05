package com.example.advance_orm.domain.user;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserRoleId implements Serializable {

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "role_id", nullable = false)
	private Long roleId;

	protected UserRoleId() {
	}

	public UserRoleId(Long userId, Long roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserRoleId that = (UserRoleId) o;
		return Objects.equals(userId, that.userId) && Objects.equals(roleId, that.roleId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, roleId);
	}
}
