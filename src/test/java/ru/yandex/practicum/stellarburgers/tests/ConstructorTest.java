package ru.yandex.practicum.stellarburgers.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.stellarburgers.pages.MainPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Epic("Stellar Burgers")
@Feature("Конструктор")
public class ConstructorTest extends BaseTest {

    @Before
    public void openMainPage() {
        assertTrue("Не открылась главная страница", new MainPage(driver).isOpened());
    }

    @Test
    @Story("Переход к разделу «Булки»")
    @Description("Проверяем, что при клике на «Булки» открывается раздел с булками")
    public void bunsSectionOpens() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openSection("Начинки");

        mainPage.openSection("Булки");

        assertEquals("Активным должен стать раздел «Булки»", "Булки", mainPage.getActiveSectionName());
        assertTrue("Раздел «Булки» не виден в списке ингредиентов", mainPage.isSectionVisible("Булки"));
    }

    @Test
    @Story("Переход к разделу «Соусы»")
    @Description("Проверяем, что при клике на «Соусы» открывается раздел с соусами")
    public void saucesSectionOpens() {
        MainPage mainPage = new MainPage(driver);

        mainPage.openSection("Соусы");

        assertEquals("Активным должен стать раздел «Соусы»", "Соусы", mainPage.getActiveSectionName());
        assertTrue("Раздел «Соусы» не виден в списке ингредиентов", mainPage.isSectionVisible("Соусы"));
    }

    @Test
    @Story("Переход к разделу «Начинки»")
    @Description("Проверяем, что при клике на «Начинки» открывается раздел с начинками")
    public void fillingsSectionOpens() {
        MainPage mainPage = new MainPage(driver);

        mainPage.openSection("Начинки");

        assertEquals("Активным должен стать раздел «Начинки»", "Начинки", mainPage.getActiveSectionName());
        assertTrue("Раздел «Начинки» не виден в списке ингредиентов", mainPage.isSectionVisible("Начинки"));
    }
}
