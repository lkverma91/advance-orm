package com.example.advance_orm;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.advance_orm.domain.user.Role;
import com.example.advance_orm.domain.user.User;
import com.example.advance_orm.domain.user.UserAddress;
import com.example.advance_orm.domain.user.UserProfile;
import com.example.advance_orm.domain.user.UserRole;
import com.example.advance_orm.domain.user.UserStatus;
import com.example.advance_orm.repository.RoleRepository;
import com.example.advance_orm.repository.UserRepository;
import com.example.advance_orm.repository.spec.UserSpecifications;
import com.example.advance_orm.testsupport.MySqlTestcontainersBase;

import jakarta.persistence.EntityManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class UserRepositoryIT extends MySqlTestcontainersBase {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	EntityManagerFactory emf;

	@Test
	@Transactional
	void entityGraph_fetchesDetails_withoutNPlusOneExplosion() {
		Role admin = new Role();
		admin.setCode("ADMIN");
		admin.setName("Admin");
		admin = roleRepository.save(admin);

		User user = new User();
		user.setEmail("a@example.com");
		user.setDisplayName("Alice");
		user.setStatus(UserStatus.ACTIVE);
		user.setPreferences(Map.of("theme", "dark"));
		user.getPhoneNumbers().addAll(Set.of("111", "222"));

		UserProfile profile = new UserProfile();
		profile.setUser(user);
		profile.setBio("hello");
		user.setProfile(profile);

		UserAddress addr = new UserAddress();
		addr.setUser(user);
		addr.setLabel("home");
		addr.setLine1("Street 1");
		addr.setCity("City");
		addr.setPostalCode("12345");
		addr.setCountry("IN");
		user.getAddresses().add(addr);

		user = userRepository.save(user);
		user.getRoles().add(new UserRole(user, admin));
		user = userRepository.save(user);

		Statistics stats = emf.unwrap(SessionFactory.class).getStatistics();
		stats.clear();

		User loaded = userRepository.findWithDetailsById(user.getId()).orElseThrow();
		assertThat(loaded.getProfile()).isNotNull();
		assertThat(loaded.getAddresses()).hasSize(1);
		assertThat(loaded.getRoles()).hasSize(1);
		assertThat(loaded.getPhoneNumbers()).hasSize(2);

		// EntityGraph should keep this bounded (not 1 + N queries).
		assertThat(stats.getPrepareStatementCount()).isLessThan(10);
	}

	@Test
	@Transactional
	void specifications_and_criteria_repo_work() {
		User u1 = new User();
		u1.setEmail("bob@example.com");
		u1.setDisplayName("Bob");
		u1.setStatus(UserStatus.ACTIVE);
		u1.setPreferences(Map.of());
		u1 = userRepository.save(u1);

		User u2 = new User();
		u2.setEmail("sue@example.com");
		u2.setDisplayName("Sue");
		u2.setStatus(UserStatus.SUSPENDED);
		u2.setPreferences(Map.of());
		u2 = userRepository.save(u2);

		Specification<User> spec = Specification.allOf(
				UserSpecifications.emailContainsIgnoreCase("example"),
				UserSpecifications.statusIs(UserStatus.ACTIVE)
		);
		assertThat(userRepository.findAll(spec)).extracting(User::getId).contains(u1.getId()).doesNotContain(u2.getId());

		List<User> criteria = userRepository.searchUsers("bo", null, 10);
		assertThat(criteria).extracting(User::getId).contains(u1.getId());
	}

	@Test
	@Transactional
	void softDelete_hidesRowsFromQueries() {
		User u = new User();
		u.setEmail("del@example.com");
		u.setDisplayName("Del");
		u.setStatus(UserStatus.ACTIVE);
		u.setPreferences(Map.of());
		u = userRepository.save(u);

		userRepository.delete(u);

		assertThat(userRepository.findAll()).extracting(User::getEmail).doesNotContain("del@example.com");
	}
}

