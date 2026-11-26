package ru.yandex.practicum.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.practicum.stellarburgers.api.UserData;
import ru.yandex.practicum.stellarburgers.config.TestConfig;

/**
 * Страница регистрации нового пользователя.
 */
public class RegistrationPage extends BasePage {

    @FindBy(xpath = "//h2[text()='Регистрация']")
    private WebElement pageTitle;

    @FindBy(xpath = "//label[text()='Имя']/following-sibling::input")
    private WebElement nameInput;

    @FindBy(xpath = "//label[text()='Email']/following-sibling::input")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@name='Пароль']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[text()='Зарегистрироваться']")
    private WebElement submitButton;

    @FindBy(xpath = "//a[text()='Войти']")
    private WebElement loginLink;

    public RegistrationPage(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Step("Открыть страницу регистрации")
    public RegistrationPage open() {
        driver.get(TestConfig.BASE_URL + "/register");
        return this;
    }

    @Step("Проверить, что открыта страница регистрации")
    public boolean isOpened() {
        return isVisible(pageTitle);
    }

    @Step("Заполнить форму регистрации данными пользователя {user}")
    public void fillRegistrationForm(UserData user) {
        type(nameInput, user.getName());
        type(emailInput, user.getEmail());
        type(passwordInput, user.getPassword());
    }

    @Step("Нажать кнопку «Зарегистрироваться»")
    public void submitRegistration() {
        click(submitButton);
    }

    @Step("Зарегистрировать пользователя {user}")
    public LoginPage register(UserData user) {
        fillRegistrationForm(user);
        submitRegistration();
        return new LoginPage(driver);
    }

    @Step("Перейти на страницу входа по ссылке «Войти»")
    public LoginPage openLoginPage() {
        click(loginLink);
        return new LoginPage(driver);
    }
}
