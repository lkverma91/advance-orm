package com.example.advance_orm.domain.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;

import com.example.advance_orm.domain.common.AuditMetadata;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.persistence.EntityListeners;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(
		name = "users",
		indexes = {
				@Index(name = "idx_users_email", columnList = "email"),
				@Index(name = "idx_users_status", columnList = "status")
		}
)
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ? AND deleted = false")
@SQLRestriction("deleted = false")
@EntityListeners(AuditingEntityListener.class)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email", nullable = false, unique = true, length = 200)
	private String email;

	@Column(name = "display_name", nullable = false, length = 200)
	private String displayName;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private UserStatus status = UserStatus.ACTIVE;

	@Version
	@Column(name = "version", nullable = false)
	private long version;

	@Column(name = "deleted", nullable = false)
	private boolean deleted = false;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "preferences", columnDefinition = "json", nullable = false)
	private Map<String, Object> preferences = new HashMap<>();

	@Embedded
	private AuditMetadata audit = new AuditMetadata();

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private UserProfile profile;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserAddress> addresses = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserRole> roles = new HashSet<>();

	@ElementCollection
	@CollectionTable(name = "user_phone_numbers", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "phone_number", nullable = false, length = 30)
	private Set<String> phoneNumbers = new HashSet<>();

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public long getVersion() {
		return version;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public Map<String, Object> getPreferences() {
		return preferences;
	}

	public void setPreferences(Map<String, Object> preferences) {
		this.preferences = preferences;
	}

	public AuditMetadata getAudit() {
		return audit;
	}

	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}

	public List<UserAddress> getAddresses() {
		return addresses;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public Set<String> getPhoneNumbers() {
		return phoneNumbers;
	}
}
