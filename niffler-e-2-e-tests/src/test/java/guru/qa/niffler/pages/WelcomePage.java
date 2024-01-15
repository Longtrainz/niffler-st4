package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class WelcomePage {

    private final SelenideElement loginLink = $("a[href*='redirect']");

    @Step("Click login link")
    public void clickLoginLink() {
        loginLink.click();
    }
}

