package ru.yandex.practicum.stellarburgers.tests;

import io.qameta.allure.Allure;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import ru.yandex.practicum.stellarburgers.api.UserApiClient;
import ru.yandex.practicum.stellarburgers.api.UserData;
import ru.yandex.practicum.stellarburgers.config.TestConfig;
import ru.yandex.practicum.stellarburgers.driver.WebDriverFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Базовый класс тестов: создание драйвера и удаление тестовых данных.
 */
public abstract class BaseTest {

    protected WebDriver driver;

    private final List<UserData> usersToDelete = new ArrayList<>();

    @Before
    public void setUp() {
        driver = WebDriverFactory.createDriver();
        driver.get(TestConfig.BASE_URL);

        String browser = System.getProperty("browser", "chrome");
        Allure.label("browser", browser);
        Allure.parameter("Браузер", browser);
    }

    @After
    public void tearDown() {
        usersToDelete.forEach(UserApiClient::deleteUser);
        if (driver != null) {
            driver.quit();
        }
    }

    protected void deleteUserAfterTest(UserData user) {
        usersToDelete.add(user);
    }

    protected UserData createUserViaApi() {
        UserData user = UserData.random();
        UserApiClient.createUser(user);
        deleteUserAfterTest(user);
        return user;
    }
}
