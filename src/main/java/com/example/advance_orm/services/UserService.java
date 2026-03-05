package com.example.advance_orm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import com.example.advance_orm.domain.user.UserStatus;
import com.example.advance_orm.dto.request.UsersRequest;
import com.example.advance_orm.dto.response.UserResponse;

public interface UserService {
	UserResponse createUser(UsersRequest request);

	Page<UserResponse.UserListItem> listUsers(String query, UserStatus status, @NonNull Pageable pageable);

	UserResponse getUser(@NonNull Long id, boolean details);

	List<UserResponse> criteriaSearch(String text, UserStatus status, int limit);

	void deleteUser(@NonNull Long id);
}
