package com.example.advance_orm.repository;

import java.util.List;

import com.example.advance_orm.domain.user.User;
import com.example.advance_orm.domain.user.UserStatus;

public interface UserRepositoryCustom {

	List<User> searchUsers(String text, UserStatus status, int limit);
}
