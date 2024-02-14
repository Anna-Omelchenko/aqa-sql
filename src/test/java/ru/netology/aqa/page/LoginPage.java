package ru.netology.aqa.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.aqa.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement loginInput = $("[data-test-id=login] input");
    private final SelenideElement passwordInput = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");

    public LoginPage() {
        loginButton.shouldBe(visible);
    }

    public VerifyCodePage login(DataHelper.LoginCredentials credentials) {
        loginInput.setValue(credentials.getUsername());
        passwordInput.setValue(credentials.getPassword());
        loginButton.click();
        return new VerifyCodePage();
    }
}
