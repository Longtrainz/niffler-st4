package guru.qa.niffler.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    private final ElementsCollection spendings = $(".spendings-table tbody").$$("tr");
    private final SelenideElement deleteSelectedButton = $(byText("Delete selected"));

    public void selectSpendingByDescription(String description) {
        spendings.find(text(description))
                .$$("td")
                .first()
                .scrollIntoView(true)
                .click();
    }

    public void clickDeleteSelectedButton() {
        deleteSelectedButton.click();
    }

    public void spendingsTableShouldHaveSize(int expectedSize) {
        spendings.shouldHave(size(expectedSize));
    }
}

