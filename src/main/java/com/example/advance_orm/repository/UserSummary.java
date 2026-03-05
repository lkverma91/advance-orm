package com.example.advance_orm.repository;

import com.example.advance_orm.domain.user.UserStatus;

public interface UserSummary {
	Long getId();

	String getEmail();

	String getDisplayName();

	UserStatus getStatus();
}
