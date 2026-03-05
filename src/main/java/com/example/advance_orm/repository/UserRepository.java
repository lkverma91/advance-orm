package com.example.advance_orm.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.advance_orm.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>, UserRepositoryCustom {

	Optional<User> findByEmail(String email);

	Page<UserSummary> findByIdIn(Iterable<Long> ids, Pageable pageable);

	@EntityGraph(attributePaths = { "profile", "addresses", "roles", "roles.role", "phoneNumbers" })
	Optional<User> findWithDetailsById(Long id);

	@Query("select u from User u left join fetch u.addresses where u.id = :id")
	Optional<User> findByIdFetchAddresses(@Param("id") Long id);
}

