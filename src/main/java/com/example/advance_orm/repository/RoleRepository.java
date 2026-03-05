package com.example.advance_orm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.advance_orm.domain.user.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByCode(String code);
}

