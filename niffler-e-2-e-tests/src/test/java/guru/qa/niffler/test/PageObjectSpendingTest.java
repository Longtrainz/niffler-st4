package guru.qa.niffler.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.commands.TakeScreenshot;
import guru.qa.niffler.jupiter.BrowserExtension;
import guru.qa.niffler.jupiter.DisabledByIssue;
import guru.qa.niffler.jupiter.GenerateCategory;
import guru.qa.niffler.jupiter.GenerateSpend;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.pages.LoginPage;
import guru.qa.niffler.pages.MainPage;
import guru.qa.niffler.pages.WelcomePage;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

public class PageObjectSpendingTest extends BaseWebTest {

  public WelcomePage welcomePage = new WelcomePage();
  public LoginPage loginPage = new LoginPage();
  public MainPage mainPage = new MainPage();

  static {
    Configuration.browserSize = "1980x1024";
  }

  @BeforeEach
  void doLogin() {
    Selenide.open("http://127.0.0.1:3000/main");
    welcomePage.clickLoginLink();
    loginPage.login("duck", "12345");
  }

  @GenerateCategory(
          username = "duck",
          category = "education"
  )
  @GenerateSpend(username = "duck",
          description = "QA.GURU Advanced 4",
          amount = 72500.00,
          category = "education",
          currency = CurrencyValues.RUB
  )
  @DisabledByIssue("3")
  @Test
  void spendingShouldBeDeletedByButtonDeleteSpending(SpendJson spend) {
    mainPage.selectSpendingByDescription(spend.description());
    mainPage.clickDeleteSelectedButton();
    mainPage.spendingsTableShouldHaveSize(0);
  }


  @AfterEach
  void closeBrowser() {
    Allure.addAttachment("Screen after test",
    new ByteArrayInputStream(
            ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES)));

    Selenide.closeWebDriver();
  }

}