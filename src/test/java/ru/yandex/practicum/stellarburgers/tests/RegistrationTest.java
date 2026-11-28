package ru.yandex.practicum.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.Test;
import ru.yandex.practicum.stellarburgers.api.UserApiClient;
import ru.yandex.practicum.stellarburgers.api.UserData;
import ru.yandex.practicum.stellarburgers.pages.LoginPage;
import ru.yandex.practicum.stellarburgers.pages.RegistrationPage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Epic("Stellar Burgers")
@Feature("Регистрация")
public class RegistrationTest extends BaseTest {

    @Test
    @Story("Успешная регистрация")
    @Description("Проверяем, что после успешной регистрации открывается страница входа")
    public void successfulRegistrationRedirectsToLoginPage() {
        UserData user = UserData.random();
        deleteUserAfterTest(user);

        RegistrationPage registrationPage = new RegistrationPage(driver).open();
        assertTrue("Не открылась страница регистрации", registrationPage.isOpened());

        LoginPage loginPage = registrationPage.register(user);

        assertTrue("После успешной регистрации должна открыться страница входа", loginPage.isOpened());
    }

    @Test
    @Story("Некорректный пароль")
    @Description("Проверяем, что пользователь с паролем короче шести символов не регистрируется")
    public void registrationWithShortPasswordIsRejected() {
        UserData user = UserData.randomWithPassword("12345");

        RegistrationPage registrationPage = new RegistrationPage(driver).open();
        assertTrue("Не открылась страница регистрации", registrationPage.isOpened());

        registrationPage.fillRegistrationForm(user);
        registrationPage.submitRegistration();

        assertTrue("Пользователь должен остаться на странице регистрации",
                registrationPage.isOpened());
        assertFalse("Пользователь с паролем короче шести символов не должен быть создан",
                UserApiClient.isUserRegistered(user));
    }
}
