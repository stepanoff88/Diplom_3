package ru.yandex.practicum.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Личный кабинет пользователя.
 */
public class ProfilePage extends BasePage {

    @FindBy(xpath = "//button[text()='Выход']")
    private WebElement logoutButton;

    @FindBy(xpath = "//a[@href='/account/profile']")
    private WebElement profileTab;

    @FindBy(xpath = "//input[@name='name']")
    private WebElement emailInput;

    public ProfilePage(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Step("Проверить, что открыт личный кабинет")
    public boolean isOpened() {
        return isVisible(logoutButton) && isVisible(profileTab);
    }

    @Step("Получить email пользователя из личного кабинета")
    public String getEmail() {
        return wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(emailInput))
                .getAttribute("value");
    }

    @Step("Выйти из аккаунта")
    public LoginPage logout() {
        click(logoutButton);
        return new LoginPage(driver);
    }
}
