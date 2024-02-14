package ru.netology.aqa.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.aqa.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerifyCodePage {

    private final SelenideElement codeInput = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public VerifyCodePage() {
        verifyButton.shouldBe(visible);
    }

    public DashboardPage verifyCode(DataHelper.VerificationCode code) {
        codeInput.setValue(code.getCode());
        verifyButton.click();
        return new DashboardPage();
    }
}
