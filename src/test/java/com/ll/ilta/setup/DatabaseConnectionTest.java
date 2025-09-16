package com.ll.ilta.setup;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@Disabled("전체 테스트 제외")
@SpringBootTest
class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("DB 연결 테스트 & PasswordEncoder 테스트")
    @Test
    void testDatabaseConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            assertThat(connection).isNotNull();

            String dbName = connection.getMetaData().getDatabaseProductName();
            String dbVersion = connection.getMetaData().getDatabaseProductVersion();
            System.out.println("DB 연결 성공: " + dbName + " " + dbVersion);

            String rawPassword = "de1234";
            String encoded = passwordEncoder.encode(rawPassword);
            System.out.println("비밀번호 인코딩 결과 = " + encoded);

            boolean matches = passwordEncoder.matches(rawPassword, encoded);
            System.out.println("비밀번호 일치 여부 = " + matches);
        }
    }
}
