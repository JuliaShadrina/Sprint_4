package ru.yandex.praktikum.scooter;

import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.praktikum.scooter.scooter.pageobjects.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertTrue;

// Тесты для проверки корректности ссылок на главной странице
public class MainPageFAQLinksTests {

    private WebDriver webDriver;

    private final String mainPageUrl = "https://qa-scooter.praktikum-services.ru";
    // Ожидаемый URL для логотипа "Яндекс"
    private final String yandexUrl = "https://yandex.ru";
    // Ожидаемый URL для логотипа "Самокат"
    private final String scooterUrl = "https://qa-scooter.praktikum-services.ru";

    @Before
    public void startUp() {
        // Настройка драйвера перед запуском теста
        WebDriverManager.chromedriver().setup();
        this.webDriver = new ChromeDriver();
        this.webDriver.get(this.mainPageUrl);
    }

    @After
    public void tearDown() {
        // Закрытие браузера после тестов
        if (this.webDriver != null) {
            this.webDriver.quit();
        }
    }

    // Проверка ссылки на логотип "Яндекс"
    @Test
    public void checkYandexLinkIsCorrectTest() {
        MainPage mainPage = new MainPage(this.webDriver);

        // Проверяем, что ссылка ведет на ожидаемый URL
        assertTrue(
                "Yandex logo link doesn't go to " + this.yandexUrl,
                mainPage.getYandexLogoLink().contains(this.yandexUrl)
        );

        // Проверяем, что ссылка открывается в новой вкладке
        assertTrue(
                "Yandex logo link doesn't open in new tab",
                mainPage.isYandexLogoLinkOpenedInNewTab()
        );
    }

    // Проверка ссылки на логотип "Самокат"
    @Test
    public void checkScooterLinkIsCorrectTest() {
        MainPage mainPage = new MainPage(this.webDriver);

        // Проверяем, что ссылка ведет на ожидаемый URL
        assertTrue(
                "Scooter logo link doesn't go to " + this.scooterUrl,
                mainPage.getScooterLogoLink().contains(this.scooterUrl)
        );
    }
}