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

import static org.junit.Assert.assertTrue;

@Epic("Stellar Burgers")
@Feature("Выход из аккаунта")
public class LogoutTest extends BaseTest {

    private UserData user;

    @Before
    public void loginAsTestUser() {
        user = createUserViaApi();
        new LoginPage(driver).open().login(user.getEmail(), user.getPassword());
        assertTrue("Не удалось авторизоваться перед тестом", new MainPage(driver).isUserLoggedIn());
    }

    @Test
    @Story("Выход из аккаунта")
    @Description("Проверяем, что по кнопке «Выход» пользователь выходит из аккаунта")
    public void logoutFromProfilePage() {
        new MainPage(driver).openProfile();
        ProfilePage profilePage = new ProfilePage(driver);
        assertTrue("Не открылся личный кабинет", profilePage.isOpened());

        LoginPage loginPage = profilePage.logout();

        assertTrue("После выхода должна открыться страница входа", loginPage.isOpened());
        assertTrue("После выхода должен открыться адрес страницы входа",
                driver.getCurrentUrl().endsWith("/login"));
    }
}
