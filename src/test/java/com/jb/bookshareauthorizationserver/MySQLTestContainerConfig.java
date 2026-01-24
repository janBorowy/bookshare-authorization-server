package com.jb.bookshareauthorizationserver;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
@Testcontainers
public class MySQLTestContainerConfig {

    @Container
    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:5.7.34"))
            .withDatabaseName("bookshare-authorization");

    @Bean
    @ServiceConnection
    public MySQLContainer<?> mySQLContainer() {
        return MY_SQL_CONTAINER;
    }
}
