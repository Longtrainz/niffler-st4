package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.User;
import guru.qa.niffler.jupiter.UsersQueueExtension;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.User.UserType.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(UsersQueueExtension.class)
public class InvitationSendTest extends BaseWebTest {

    @BeforeEach
    void doLogin() {
        Selenide.open("http://127.0.0.1:3000/main");
        welcomePage.clickLoginLink();
    }

    @DisplayName("Проверим, что есть отправленная заявка в друзья и имя совпадает с ожидаемым")
    @Test
    void invitationTableShouldNotBeEmpty(@User(INVITATION_SEND) UserJson user) {
        loginPage.login(user.username(),user.testData().password());
        mainPage.clickAllPeopleBtn();
        allPeoplePage.findRecordInFriendsTable(user.testData().friendName());
    }

    @DisplayName("Проверим, что есть запись о друзьях, ожидающая активации")
    @Test
    void invitationTableShouldHaveRecordAboutFriends(@User(INVITATION_SEND) UserJson user) {
        loginPage.login(user.username(),user.testData().password());
        mainPage.clickAllPeopleBtn();
        allPeoplePage.checkPendingInvitation(user.testData().friendName());
    }

    @DisplayName("Проверим, что есть аватар")
    @Test
    void invitationTableShouldHaveAvatar(@User(INVITATION_SEND) UserJson user,
                                         @User(WITH_FRIENDS) UserJson withFriendsUser) {
        assertAll( "Проверка, что пользователь с друзьями выбрался или duck или dima",
                ()-> assertTrue(withFriendsUser.username().equals("duck") || withFriendsUser.username().equals("dima"))
        );
        loginPage.login(user.username(),user.testData().password());
        mainPage.clickAllPeopleBtn();
        allPeoplePage.checkAvatar(user.testData().friendName());
    }
}
