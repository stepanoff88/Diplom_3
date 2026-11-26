package ru.yandex.practicum.stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Главная страница — конструктор бургеров.
 */
public class MainPage extends BasePage {

    @FindBy(xpath = "//h1[text()='Соберите бургер']")
    private WebElement pageTitle;

    @FindBy(xpath = "//button[text()='Войти в аккаунт']")
    private WebElement loginButton;

    @FindBy(xpath = "//button[text()='Оформить заказ']")
    private WebElement orderButton;

    public MainPage(WebDriver driver) {
        super(driver);
        initElements();
    }

    @Step("Проверить, что открыта главная страница")
    public boolean isOpened() {
        return isVisible(pageTitle);
    }

    @Step("Проверить, что пользователь авторизован")
    public boolean isUserLoggedIn() {
        return isVisible(orderButton);
    }

    @Step("Открыть страницу входа по кнопке «Войти в аккаунт»")
    public LoginPage openLoginPage() {
        click(loginButton);
        return new LoginPage(driver);
    }

    @Step("Открыть раздел конструктора «{sectionName}»")
    public void openSection(String sectionName) {
        click(driver.findElement(sectionTab(sectionName)));
        waitForSectionScrollingToFinish(sectionName);
    }

    @Step("Получить название активного раздела конструктора")
    public String getActiveSectionName() {
        return activeSectionName();
    }

    @Step("Проверить, что раздел «{sectionName}» виден в списке ингредиентов")
    public boolean isSectionVisible(String sectionName) {
        WebElement scrollContainer = driver.findElement(
                By.cssSelector("[class*='BurgerIngredients_ingredients__menuContainer']"));
        WebElement sectionTitle = driver.findElement(By.xpath("//h2[text()='" + sectionName + "']"));

        Rectangle containerRect = scrollContainer.getRect();
        Rectangle titleRect = sectionTitle.getRect();

        return titleRect.getY() >= containerRect.getY()
                && titleRect.getY() + titleRect.getHeight()
                <= containerRect.getY() + containerRect.getHeight();
    }

    private By sectionTab(String sectionName) {
        return By.xpath("//div[contains(@class,'tab_tab__')][.//span[text()='" + sectionName + "']]");
    }

    private String activeSectionName() {
        return wait.until(ExpectedConditions
                        .visibilityOfElementLocated(By.xpath("//div[contains(@class,'tab_tab_type_current__')]")))
                .getText();
    }

    private void waitForSectionScrollingToFinish(String sectionName) {
        int[] previousSectionPosition = {Integer.MIN_VALUE};
        wait.until(webDriver -> {
            int currentSectionPosition = driver.findElement(By.xpath("//h2[text()='" + sectionName + "']"))
                    .getRect().getY();
            boolean scrollingFinished = currentSectionPosition == previousSectionPosition[0];
            previousSectionPosition[0] = currentSectionPosition;
            return scrollingFinished && sectionName.equals(activeSectionName());
        });
    }
}
