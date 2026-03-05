package com.example.advance_orm.domain.common;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {

	@Bean
	AuditorAware<String> auditorAware() {
		return () -> Optional.of("system");
	}
}
