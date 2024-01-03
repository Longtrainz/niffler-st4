package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class WelcomePage {

    private final SelenideElement loginLink = $("a[href*='redirect']");

    public void clickLoginLink() {
        loginLink.click();
    }
}

