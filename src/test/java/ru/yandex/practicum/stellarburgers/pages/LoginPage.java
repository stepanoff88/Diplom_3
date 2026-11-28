package ru.yandex.practicum.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.practicum.stellarburgers.config.TestConfig;

/**
 * Страница входа в аккаунт.
 */
public class LoginPage extends BasePage {

    @FindBy(xpath = "//h2[text()='Вход']")
    private WebElement pageTitle;

    @FindBy(xpath = "//label[text()='Email']/following-sibling::input")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@name='Пароль']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[text()='Войти']")
    private WebElement submitButton;

    @FindBy(xpath = "//a[text()='Зарегистрироваться']")
    private WebElement registrationLink;

    @FindBy(xpath = "//a[text()='Восстановить пароль']")
    private WebElement passwordRecoveryLink;

    public LoginPage(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Step("Открыть страницу входа")
    public LoginPage open() {
        driver.get(TestConfig.BASE_URL + "/login");
        return this;
    }

    @Step("Проверить, что открыта страница входа")
    public boolean isOpened() {
        return isVisible(pageTitle);
    }

    @Step("Войти в аккаунт с email {email}")
    public MainPage login(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        click(submitButton);
        return new MainPage(driver);
    }

    @Step("Перейти на страницу регистрации")
    public RegistrationPage openRegistrationPage() {
        click(registrationLink);
        return new RegistrationPage(driver);
    }

    @Step("Перейти на страницу восстановления пароля")
    public PasswordRecoveryPage openPasswordRecoveryPage() {
        click(passwordRecoveryLink);
        return new PasswordRecoveryPage(driver);
    }
}
