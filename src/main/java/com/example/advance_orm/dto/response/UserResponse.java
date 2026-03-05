package com.example.advance_orm.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.advance_orm.domain.user.User;
import com.example.advance_orm.domain.user.UserAddress;
import com.example.advance_orm.domain.user.UserProfile;
import com.example.advance_orm.domain.user.UserRole;
import com.example.advance_orm.domain.user.UserStatus;

public record UserResponse(
		Long id,
		String email,
		String displayName,
		UserStatus status,
		long version,
		Map<String, Object> preferences,
		Set<String> phoneNumbers,
		Profile profile,
		List<Address> addresses,
		Set<RoleRef> roles,
		Audit audit
) {
	public record Profile(String bio, String avatarUrl) {
		static Profile from(UserProfile profile) {
			if (profile == null) return null;
			return new Profile(profile.getBio(), profile.getAvatarUrl());
		}
	}

	public record Address(Long id, String label, String line1, String city, String postalCode, String country) {
		static Address from(UserAddress a) {
			return new Address(a.getId(), a.getLabel(), a.getLine1(), a.getCity(), a.getPostalCode(), a.getCountry());
		}
	}

	public record RoleRef(Long id, String code, String name) {
		static RoleRef from(UserRole ur) {
			return new RoleRef(ur.getRole().getId(), ur.getRole().getCode(), ur.getRole().getName());
		}
	}

	public record Audit(Instant createdAt, String createdBy, Instant updatedAt, String updatedBy) {
	}

	public record UserListItem(Long id, String email, String displayName, UserStatus status) {
	}

	public static UserResponse from(User user) {
		return new UserResponse(
				user.getId(),
				user.getEmail(),
				user.getDisplayName(),
				user.getStatus(),
				user.getVersion(),
				user.getPreferences(),
				user.getPhoneNumbers(),
				Profile.from(user.getProfile()),
				user.getAddresses().stream().map(Address::from).toList(),
				user.getRoles().stream().map(RoleRef::from).collect(java.util.stream.Collectors.toSet()),
				new Audit(
						user.getAudit().getCreatedAt(),
						user.getAudit().getCreatedBy(),
						user.getAudit().getUpdatedAt(),
						user.getAudit().getUpdatedBy()
				)
		);
	}

	public static UserListItem toListItem(User user) {
		return new UserListItem(user.getId(), user.getEmail(), user.getDisplayName(), user.getStatus());
	}
}
