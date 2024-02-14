package ru.netology.aqa;

import org.junit.jupiter.api.Test;
import ru.netology.aqa.data.DataHelper;
import ru.netology.aqa.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    @Test
    void shouldLoginAsVasya() {
        open("http://localhost:9999");
        var vasyaCreds = DataHelper.getVasyaCreds();
        var loginPage = new LoginPage();
        var verifyCodePage = loginPage.login(vasyaCreds);
        verifyCodePage.verifyCode(DataHelper.getVerificationCodeFor(vasyaCreds));
    }
}
