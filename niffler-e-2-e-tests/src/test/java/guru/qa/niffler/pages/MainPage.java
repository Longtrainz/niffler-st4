package guru.qa.niffler.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    private final ElementsCollection spendings = $(".spendings-table tbody").$$("tr");
    private final SelenideElement deleteSelectedButton = $(byText("Delete selected"));

    @Step("Select spending by {0} description")
    public void selectSpendingByDescription(String description) {
        spendings.find(text(description))
                .$$("td")
                .first()
                .scrollIntoView(true)
                .click();
    }

    @Step("Click 'Delete selected' button")
    public void clickDeleteSelectedButton() {
        deleteSelectedButton.click();
    }

    @Step("Assert Spendings table have {0} size")
    public void spendingsTableShouldHaveSize(int expectedSize) {
        spendings.shouldHave(size(expectedSize));
    }

    public MainPage clickAllPeopleBtn() {
        $("a[href*='people']").click();
        return this;
    }

    public MainPage clickFriendsBtn() {
        $("a[href*='friends']").click();
        return this;
    }
}

