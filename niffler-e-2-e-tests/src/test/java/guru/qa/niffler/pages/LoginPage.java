package guru.qa.niffler.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement usernameField = $("input[name='username']");
    private final SelenideElement passwordField = $("input[name='password']");
    private final SelenideElement signInButton = $("button[type='submit']");

    @Step("Log in")
    public void login(String username, String password) {
        usernameField.setValue(username);
        passwordField.setValue(password);
        signInButton.click();
    }
}

