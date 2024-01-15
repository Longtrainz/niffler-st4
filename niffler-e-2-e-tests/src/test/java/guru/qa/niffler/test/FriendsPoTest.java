package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.User;
import guru.qa.niffler.jupiter.UsersQueueExtension;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.$;
import static guru.qa.niffler.jupiter.User.UserType.WITH_FRIENDS;

@ExtendWith(UsersQueueExtension.class)
public class FriendsPoTest extends BaseWebTest {

//    @BeforeEach
//    void doLogin(@User(WITH_FRIENDS) UserJson user) {
//        Selenide.open("http://127.0.0.1:3000/main");
//        welcomePage.clickLoginLink();
//        loginPage.login(user.username(), user.testData().password());
//        mainPage.clickFriendsBtn();
//    }

  @BeforeEach
  void doLogin(@User(WITH_FRIENDS) UserJson user) {
    Selenide.open("http://127.0.0.1:3000/main");
    welcomePage.clickLoginLink();
    loginPage.login(user.username(),user.testData().password());
    mainPage.clickFriendsBtn();
  }

    @DisplayName("Проверим, что есть друг и его имя совпадает с ожидаемым")
    @Test
    void friendsTableShouldNotBeEmpty(@User(WITH_FRIENDS) UserJson user) {
        friendsPage.findRecordInFriendsTable(user.testData().friendName());
    }

    @DisplayName("Проверим, что есть запись о друзьях")
    @Test
    void friendsTableShouldHaveRecordAboutFriends(@User(WITH_FRIENDS) UserJson user) {
        friendsPage.checkFriendsStatus(user.testData().friendName());
    }

    @DisplayName("Проверим, что количество друзей равно 1")
    @Test
    void friendsTableShouldHaveOneRecord() {
        friendsPage.checkCountFriends(1);
    }

    @DisplayName("Проверим, что есть кнопка Удалить")
    @Test
    void friendsTableShouldHaveRemobeBtn(@User(WITH_FRIENDS) UserJson user) {
        friendsPage.checkRemoveBtn(user.testData().friendName());
    }

    @DisplayName("Проверим, что есть аватар")
    @Test
    void friendsTableShouldHaveAvatar(@User(WITH_FRIENDS) UserJson user) {
        friendsPage.checkAvatar(user.testData().friendName());
    }
}
