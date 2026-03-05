package com.example.advance_orm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.advance_orm.testsupport.MySqlTestcontainersBase;

@SpringBootTest
@Testcontainers
class AdvanceOrmApplicationTests extends MySqlTestcontainersBase {

	@Test
	void contextLoads() {
	}

}
