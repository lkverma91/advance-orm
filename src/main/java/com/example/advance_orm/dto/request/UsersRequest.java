package com.example.advance_orm.dto.request;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.advance_orm.domain.user.UserStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsersRequest(
		@NotBlank @Email @Size(max = 200) String email,
		@NotBlank @Size(max = 200) String displayName,
		UserStatus status,
		@NotNull Map<String, Object> preferences,
		@NotNull Set<@Size(max = 30) String> phoneNumbers,
		@NotNull List<@Valid Address> addresses,
		@NotNull Set<@NotBlank @Size(max = 50) String> roleCodes,
		@Size(max = 1000) String profileBio,
		@Size(max = 500) String profileAvatarUrl
) {
	public record Address(
			@NotBlank @Size(max = 100) String label,
			@NotBlank @Size(max = 200) String line1,
			@NotBlank @Size(max = 100) String city,
			@NotBlank @Size(max = 20) String postalCode,
			@NotBlank @Size(min = 2, max = 2) String country
	) {
	}
}
