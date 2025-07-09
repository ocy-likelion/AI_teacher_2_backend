package com.ll.ilta.setup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Disabled("전체 테스트 제외")
@SpringBootTest
class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @DisplayName("DB 연결 테스트")
    @Test
    void testDatabaseConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            assertThat(connection).isNotNull();

            String dbName = connection.getMetaData().getDatabaseProductName();
            String dbVersion = connection.getMetaData().getDatabaseProductVersion();
            System.out.println("DB 연결 성공: " + dbName + " " + dbVersion);
        }
    }
}
