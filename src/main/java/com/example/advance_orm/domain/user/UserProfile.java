package com.example.advance_orm.domain.user;

import com.example.advance_orm.domain.common.AuditMetadata;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_profiles")
@EntityListeners(AuditingEntityListener.class)
public class UserProfile {

	@Id
	@Column(name = "user_id")
	private Long userId;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "bio", nullable = false, length = 1000)
	private String bio = "";

	@Column(name = "avatar_url", length = 500)
	private String avatarUrl;

	@Embedded
	private AuditMetadata audit = new AuditMetadata();

	public Long getUserId() {
		return userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public AuditMetadata getAudit() {
		return audit;
	}
}
