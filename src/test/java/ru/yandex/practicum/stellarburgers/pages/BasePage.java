package ru.yandex.practicum.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Базовый Page Object: общая шапка сайта и вспомогательные методы работы с элементами.
 */
public abstract class BasePage {

    protected static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    @FindBy(xpath = "//a[.//p[text()='Конструктор']]")
    private WebElement constructorLink;

    @FindBy(xpath = "//div[contains(@class,'AppHeader_header__logo')]/a")
    private WebElement logoLink;

    @FindBy(xpath = "//a[.//p[text()='Личный Кабинет']]")
    private WebElement profileLink;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    protected void initElements() {
        PageFactory.initElements(driver, this);
    }

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected void type(WebElement element, String text) {
        WebElement field = wait.until(ExpectedConditions.visibilityOf(element));
        field.clear();
        field.sendKeys(text);
    }

    protected boolean isVisible(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (TimeoutException exception) {
            return false;
        }
    }

    @Step("Перейти в конструктор по ссылке «Конструктор»")
    public MainPage openConstructor() {
        click(constructorLink);
        return new MainPage(driver);
    }

    @Step("Перейти в конструктор по логотипу Stellar Burgers")
    public MainPage openConstructorByLogo() {
        click(logoLink);
        return new MainPage(driver);
    }

    @Step("Открыть личный кабинет по ссылке «Личный Кабинет»")
    public void openProfile() {
        click(profileLink);
    }
}
