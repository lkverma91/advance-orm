package com.example.advance_orm.testsupport;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public abstract class MySqlTestcontainersBase {

	static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.4")
			.withDatabaseName("advance_orm")
			.withUsername("advance")
			.withPassword("advance");

	static {
		MYSQL.start();
	}

	@DynamicPropertySource
	static void registerMySqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
		registry.add("spring.datasource.username", MYSQL::getUsername);
		registry.add("spring.datasource.password", MYSQL::getPassword);
		registry.add("spring.datasource.driver-class-name", MYSQL::getDriverClassName);

		registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
		registry.add("spring.jpa.properties.hibernate.generate_statistics", () -> "true");
		registry.add("spring.flyway.enabled", () -> "true");
	}
}

