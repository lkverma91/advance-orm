package com.example.advance_orm.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.example.advance_orm.domain.user.User;
import com.example.advance_orm.domain.user.UserStatus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
class UserRepositoryImpl implements UserRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<User> searchUsers(String text, UserStatus status, int limit) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> user = cq.from(User.class);

		List<Predicate> predicates = new ArrayList<>();

		if (StringUtils.hasText(text)) {
			String like = "%" + text.trim().toLowerCase() + "%";
			predicates.add(cb.or(
					cb.like(cb.lower(user.get("email")), like),
					cb.like(cb.lower(user.get("displayName")), like)
			));
		}

		if (status != null) {
			predicates.add(cb.equal(user.get("status"), status));
		}

		cq.where(predicates.toArray(Predicate[]::new));
		cq.orderBy(cb.asc(user.get("id")));

		return entityManager.createQuery(cq)
				.setMaxResults(Math.max(limit, 1))
				.getResultList();
	}
}

