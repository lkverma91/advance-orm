package com.example.advance_orm.repository.spec;

import org.springframework.data.jpa.domain.Specification;

import com.example.advance_orm.domain.user.User;
import com.example.advance_orm.domain.user.UserStatus;

public final class UserSpecifications {

	private UserSpecifications() {
	}

	public static Specification<User> emailContainsIgnoreCase(String q) {
		return (root, query, cb) -> {
			if (q == null || q.isBlank()) {
				return cb.conjunction();
			}
			return cb.like(cb.lower(root.get("email")), "%" + q.trim().toLowerCase() + "%");
		};
	}

	public static Specification<User> statusIs(UserStatus status) {
		return (root, query, cb) -> status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
	}
}

