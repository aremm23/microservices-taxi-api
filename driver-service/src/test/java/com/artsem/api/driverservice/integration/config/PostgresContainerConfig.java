package com.artsem.api.driverservice.integration.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class PostgresContainerConfig {

    @Container
    private static final PostgreSQLContainer<?> postgresSQLContainer =
            new PostgreSQLContainer<>(
                    DockerImageName.parse("postgres:17.0-alpine")
            );

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgresSQLContainer::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        postgresSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgresSQLContainer.stop();
    }

}
