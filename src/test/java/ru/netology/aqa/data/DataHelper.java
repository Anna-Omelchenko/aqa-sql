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

    public static final String USER_STATUS_ACTIVE = "active";
    public static final String USER_STATUS_BLOCKED = "blocked";

    private static final String QUERY_VERIFICATION_CODE =
            "SELECT code FROM auth_codes " +
            "WHERE user_id IN (SELECT id FROM users WHERE login = ?)" +
            "ORDER BY created DESC LIMIT 1";
    private static final String QUERY_USER_GET_STATUS = "SELECT status FROM users WHERE login = ?";
    private static final String QUERY_USER_SET_STATUS = "UPDATE users SET status = ? WHERE login = ?";

    private static final String QUERY_DELETE_CARD_TRANSACTIONS  = "DELETE FROM card_transactions";
    private static final String QUERY_DELETE_CARDS              = "DELETE FROM cards";
    private static final String QUERY_DELETE_AUTH_CODES         = "DELETE FROM auth_codes";
    private static final String QUERY_DELETE_USERS              = "DELETE FROM users";

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

    @SneakyThrows
    public static String getUserStatusFor(LoginCredentials loginCredentials) {
        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STRING, DB_USER, DB_PASSWORD)) {
            return QUERY_RUNNER.query(conn, QUERY_USER_GET_STATUS, new ScalarHandler<>(), loginCredentials.getUsername());
        }
    }

    @SneakyThrows
    public static void unblockUser(LoginCredentials loginCredentials) {
        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STRING, DB_USER, DB_PASSWORD)) {
            QUERY_RUNNER.update(conn, QUERY_USER_SET_STATUS, USER_STATUS_ACTIVE, loginCredentials.getUsername());
        }
    }

    public static String generatePassword() {
        return FAKER_EN.internet().password();
    }

    @SneakyThrows
    public static void deleteTestData() {
        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STRING, DB_USER, DB_PASSWORD)) {
            QUERY_RUNNER.update(conn, QUERY_DELETE_CARD_TRANSACTIONS);
            QUERY_RUNNER.update(conn, QUERY_DELETE_CARDS);
            QUERY_RUNNER.update(conn, QUERY_DELETE_AUTH_CODES);
            QUERY_RUNNER.update(conn, QUERY_DELETE_USERS);
        }
    }
}
