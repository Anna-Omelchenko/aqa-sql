package ru.netology.aqa.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.aqa.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement loginInput = $("[data-test-id=login] input");
    private final SelenideElement passwordInput = $("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");

    public LoginPage() {
        this(false);
    }

    public LoginPage(boolean withErrorNotification) {
        clearInputElement(loginInput);
        clearInputElement(passwordInput);
        loginButton.shouldBe(visible);
        if (withErrorNotification) {
            $("[data-test-id=error-notification]").shouldBe(visible);
        }
    }

    public VerifyCodePage login(DataHelper.LoginCredentials credentials) {
        loginInput.setValue(credentials.getUsername());
        passwordInput.setValue(credentials.getPassword());
        loginButton.click();
        return new VerifyCodePage();
    }

    public LoginPage loginWithWrongPassword(DataHelper.LoginCredentials credentials) {
        loginInput.setValue(credentials.getUsername());
        passwordInput.setValue(DataHelper.generatePassword());
        loginButton.click();
        return new LoginPage(true);
    }

    private static void clearInputElement(SelenideElement input) {
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
    }
}
