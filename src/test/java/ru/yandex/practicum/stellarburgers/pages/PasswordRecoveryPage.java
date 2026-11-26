package ru.yandex.practicum.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.practicum.stellarburgers.config.TestConfig;

/**
 * Страница восстановления пароля.
 */
public class PasswordRecoveryPage extends BasePage {

    @FindBy(xpath = "//h2[text()='Восстановление пароля']")
    private WebElement pageTitle;

    @FindBy(xpath = "//label[text()='Email']/following-sibling::input")
    private WebElement emailInput;

    @FindBy(xpath = "//button[text()='Восстановить']")
    private WebElement restoreButton;

    @FindBy(xpath = "//a[text()='Войти']")
    private WebElement loginLink;

    public PasswordRecoveryPage(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Step("Открыть страницу восстановления пароля")
    public PasswordRecoveryPage open() {
        driver.get(TestConfig.BASE_URL + "/forgot-password");
        return this;
    }

    @Step("Проверить, что открыта страница восстановления пароля")
    public boolean isOpened() {
        return isVisible(pageTitle);
    }

    @Step("Перейти на страницу входа по ссылке «Войти»")
    public LoginPage openLoginPage() {
        click(loginLink);
        return new LoginPage(driver);
    }
}
