package guru.qa.niffler.test;

import guru.qa.niffler.jupiter.BrowserExtension;
import guru.qa.niffler.pages.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({BrowserExtension.class})
public abstract class BaseWebTest {
    WelcomePage welcomePage = new WelcomePage();
    LoginPage loginPage = new LoginPage();
    MainPage mainPage = new MainPage();
    FriendsPage friendsPage = new FriendsPage();
    AllPeoplePage allPeoplePage = new AllPeoplePage();
}
