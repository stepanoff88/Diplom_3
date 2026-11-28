package ru.yandex.practicum.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.stellarburgers.api.UserData;
import ru.yandex.practicum.stellarburgers.pages.LoginPage;
import ru.yandex.practicum.stellarburgers.pages.MainPage;
import ru.yandex.practicum.stellarburgers.pages.PasswordRecoveryPage;
import ru.yandex.practicum.stellarburgers.pages.RegistrationPage;

import static org.junit.Assert.assertTrue;

@Epic("Stellar Burgers")
@Feature("Вход в аккаунт")
public class LoginTest extends BaseTest {

    private UserData user;

    @Before
    public void createTestUser() {
        user = createUserViaApi();
    }

    @Test
    @Story("Кнопка «Войти в аккаунт» на главной")
    @Description("Проверяем вход по кнопке «Войти в аккаунт» на главной странице")
    public void loginFromMainPage() {
        MainPage mainPage = new MainPage(driver);
        assertTrue("Не открылась главная страница", mainPage.isOpened());

        LoginPage loginPage = mainPage.openLoginPage();
        assertTrue("Не открылась страница входа", loginPage.isOpened());

        loginPage.login(user.getEmail(), user.getPassword());

        assertTrue("Пользователь не авторизовался", new MainPage(driver).isUserLoggedIn());
    }

    @Test
    @Story("Кнопка «Личный Кабинет»")
    @Description("Проверяем вход через кнопку «Личный Кабинет» в шапке сайта")
    public void loginFromProfileButton() {
        MainPage mainPage = new MainPage(driver);
        assertTrue("Не открылась главная страница", mainPage.isOpened());

        mainPage.openProfile();
        LoginPage loginPage = new LoginPage(driver);
        assertTrue("Не открылась страница входа", loginPage.isOpened());

        loginPage.login(user.getEmail(), user.getPassword());

        assertTrue("Пользователь не авторизовался", new MainPage(driver).isUserLoggedIn());
    }

    @Test
    @Story("Кнопка «Войти» в форме регистрации")
    @Description("Проверяем вход через кнопку «Войти» на странице регистрации")
    public void loginFromRegistrationPage() {
        LoginPage loginPage = new RegistrationPage(driver).open().openLoginPage();
        assertTrue("Не открылась страница входа", loginPage.isOpened());

        loginPage.login(user.getEmail(), user.getPassword());

        assertTrue("Пользователь не авторизовался", new MainPage(driver).isUserLoggedIn());
    }

    @Test
    @Story("Кнопка «Войти» в форме восстановления пароля")
    @Description("Проверяем вход через кнопку «Войти» на странице восстановления пароля")
    public void loginFromPasswordRecoveryPage() {
        LoginPage loginPage = new PasswordRecoveryPage(driver).open().openLoginPage();
        assertTrue("Не открылась страница входа", loginPage.isOpened());

        loginPage.login(user.getEmail(), user.getPassword());

        assertTrue("Пользователь не авторизовался", new MainPage(driver).isUserLoggedIn());
    }
}
