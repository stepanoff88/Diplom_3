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
import ru.yandex.practicum.stellarburgers.pages.ProfilePage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Epic("Stellar Burgers")
@Feature("Личный кабинет")
public class ProfileTest extends BaseTest {

    private UserData user;

    @Before
    public void loginAsTestUser() {
        user = createUserViaApi();
        new LoginPage(driver).open().login(user.getEmail(), user.getPassword());
        assertTrue("Не удалось авторизоваться перед тестом", new MainPage(driver).isUserLoggedIn());
    }

    @Test
    @Story("Переход в личный кабинет")
    @Description("Проверяем переход в личный кабинет по кнопке «Личный Кабинет»")
    public void profileOpensFromHeader() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openProfile();

        ProfilePage profilePage = new ProfilePage(driver);
        assertTrue("Не открылся личный кабинет", profilePage.isOpened());
        assertEquals("В личном кабинете отображается другой email",
                user.getEmail(), profilePage.getEmail());
    }

    @Test
    @Story("Переход из личного кабинета в конструктор")
    @Description("Проверяем переход в конструктор по ссылке «Конструктор» из личного кабинета")
    public void constructorOpensFromProfileByHeaderLink() {
        ProfilePage profilePage = openProfilePage();

        MainPage mainPage = profilePage.openConstructor();

        assertTrue("Не открылась главная страница", mainPage.isOpened());
    }

    @Test
    @Story("Переход из личного кабинета в конструктор")
    @Description("Проверяем переход в конструктор по логотипу Stellar Burgers из личного кабинета")
    public void constructorOpensFromProfileByLogo() {
        ProfilePage profilePage = openProfilePage();

        MainPage mainPage = profilePage.openConstructorByLogo();

        assertTrue("Не открылась главная страница", mainPage.isOpened());
    }

    @io.qameta.allure.Step("Открыть личный кабинет из шапки сайта")
    private ProfilePage openProfilePage() {
        new MainPage(driver).openProfile();
        ProfilePage profilePage = new ProfilePage(driver);
        assertTrue("Не открылся личный кабинет", profilePage.isOpened());
        return profilePage;
    }
}
