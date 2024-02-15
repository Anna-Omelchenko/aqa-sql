package ru.netology.aqa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.aqa.data.DataHelper;
import ru.netology.aqa.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.aqa.data.DataHelper.USER_STATUS_ACTIVE;
import static ru.netology.aqa.data.DataHelper.USER_STATUS_BLOCKED;

public class LoginTest {

    @BeforeEach
    void openLoginPage() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginAsVasya() {
        var vasyaCreds = DataHelper.getVasyaCreds();
        var loginPage = new LoginPage();
        var verifyCodePage = loginPage.login(vasyaCreds);
        verifyCodePage.verifyCode(DataHelper.getVerificationCodeFor(vasyaCreds));
    }

    @Test
    void shouldBlockUserAfterThreeLoginAttemptsWithWrongPassword() {
        var vasyaCreds = DataHelper.getVasyaCreds();
        var loginPage = new LoginPage();
        for (int i = 0; i < 3; i++) {
            loginPage = loginPage.loginWithWrongPassword(vasyaCreds);
            String expectedUserStatus = i < 2 ? USER_STATUS_ACTIVE : USER_STATUS_BLOCKED;
            assertEquals(expectedUserStatus, DataHelper.getUserStatusFor(vasyaCreds));
        }
        DataHelper.unblockUser(vasyaCreds);
    }
}
