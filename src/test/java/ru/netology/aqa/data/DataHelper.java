package ru.netology.aqa.data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Locale;

public class DataHelper {

    private static final String DB_CONNECTION_STRING = "jdbc:mysql://localhost:33060/deadline";
    private static final String DB_USER = "deadline-app";
    private static final String DB_PASSWORD = "6aDhk037a2e";

    private static final String QUERY_VERIFICATION_CODE =
            "SELECT code FROM auth_codes " +
            "WHERE user_id IN (SELECT id FROM users WHERE login = ?)" +
            "ORDER BY created DESC LIMIT 1";

    private static final Faker FAKER_EN = new Faker(Locale.ENGLISH);
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private DataHelper() {}

    @Value
    public static class LoginCredentials {
        String username;
        String password;
    }

    public static LoginCredentials getVasyaCreds() {
        return new LoginCredentials("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    @SneakyThrows
    public static VerificationCode getVerificationCodeFor(LoginCredentials loginCredentials) {
        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STRING, DB_USER, DB_PASSWORD)) {
            return new VerificationCode(
                    QUERY_RUNNER.query(conn, QUERY_VERIFICATION_CODE, new ScalarHandler<>(), loginCredentials.getUsername()));
        }
    }

    public static String generatePassword() {
        return FAKER_EN.internet().password();
    }
}
