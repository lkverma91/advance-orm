package com.example.advance_orm.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.advance_orm.domain.user.UserStatus;
import com.example.advance_orm.dto.request.UsersRequest;
import com.example.advance_orm.dto.response.UserResponse;
import com.example.advance_orm.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Validated
public class UsersController {

	private final UserService userService;

	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponse create(@Valid @RequestBody UsersRequest request) {
		return userService.createUser(request);
	}

	@GetMapping
	public Page<UserResponse.UserListItem> list(
			@RequestParam(name = "query", required = false) String query,
			@RequestParam(name = "status", required = false) UserStatus status,
			@PageableDefault(size = 20) Pageable pageable
	) {
		return userService.listUsers(query, status, pageable);
	}

	@GetMapping("/{id}")
	public UserResponse get(
			@PathVariable("id") Long id,
			@RequestParam(name = "details", defaultValue = "false") boolean details
	) {
		return userService.getUser(id, details);
	}

	@GetMapping("/search/criteria")
	public List<UserResponse> criteriaSearch(
			@RequestParam(name = "text", required = false) String text,
			@RequestParam(name = "status", required = false) UserStatus status,
			@RequestParam(name = "limit", defaultValue = "50") int limit
	) {
		return userService.criteriaSearch(text, status, limit);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		userService.deleteUser(id);
	}
}
