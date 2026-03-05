package com.example.advance_orm.services.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.advance_orm.domain.user.Role;
import com.example.advance_orm.domain.user.User;
import com.example.advance_orm.domain.user.UserAddress;
import com.example.advance_orm.domain.user.UserProfile;
import com.example.advance_orm.domain.user.UserRole;
import com.example.advance_orm.domain.user.UserStatus;
import com.example.advance_orm.dto.request.UsersRequest;
import com.example.advance_orm.dto.response.UserResponse;
import com.example.advance_orm.repository.RoleRepository;
import com.example.advance_orm.repository.UserRepository;
import com.example.advance_orm.repository.spec.UserSpecifications;
import com.example.advance_orm.services.UserService;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public UserResponse createUser(UsersRequest request) {
		User user = new User();
		user.setEmail(request.email());
		user.setDisplayName(request.displayName());
		user.setStatus(request.status() == null ? UserStatus.ACTIVE : request.status());
		user.setPreferences(request.preferences());
		user.getPhoneNumbers().addAll(request.phoneNumbers());

		UserProfile profile = new UserProfile();
		profile.setUser(user);
		profile.setBio(request.profileBio() == null ? "" : request.profileBio());
		profile.setAvatarUrl(request.profileAvatarUrl());
		user.setProfile(profile);

		for (UsersRequest.Address a : request.addresses()) {
			UserAddress addr = new UserAddress();
			addr.setUser(user);
			addr.setLabel(a.label());
			addr.setLine1(a.line1());
			addr.setCity(a.city());
			addr.setPostalCode(a.postalCode());
			addr.setCountry(a.country());
			user.getAddresses().add(addr);
		}

		user = userRepository.save(user);

		for (String roleCode : request.roleCodes()) {
			Role role = roleRepository.findByCode(roleCode)
					.orElseGet(() -> {
						Role r = new Role();
						r.setCode(roleCode);
						r.setName(roleCode);
						return roleRepository.save(r);
					});
			user.getRoles().add(new UserRole(user, role));
		}

		user = userRepository.save(user);
		return UserResponse.from(user);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserResponse.UserListItem> listUsers(String query, UserStatus status, @NonNull Pageable pageable) {
		Specification<User> spec = Specification.allOf(
				UserSpecifications.emailContainsIgnoreCase(query),
				UserSpecifications.statusIs(status)
		);
		return userRepository.findAll(spec, pageable).map(UserResponse::toListItem);
	}

	@Override
	@Transactional(readOnly = true)
	public UserResponse getUser(@NonNull Long id, boolean details) {
		User user = details
				? userRepository.findWithDetailsById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND))
				: userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
		return UserResponse.from(user);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserResponse> criteriaSearch(String text, UserStatus status, int limit) {
		return userRepository.searchUsers(text, status, limit).stream().map(UserResponse::from).toList();
	}

	@Override
	public void deleteUser(@NonNull Long id) {
		if (!userRepository.existsById(id)) {
			throw new ResponseStatusException(NOT_FOUND);
		}
		userRepository.deleteById(id);
	}
}
