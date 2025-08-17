package org.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

// Главная страница
public class MainPage {
    private final WebDriver webDriver;

    // Локаторы элементов главной страницы
    private final By accordionHeaders = By.className("accordion__heading"); // Заголовки аккордеона
    private final By accordionItems = By.xpath(".//div[@class='accordion__panel']/p"); // Текстовые блоки аккордеона
    private final By orderButtonHeader = By.xpath(".//div[starts-with(@class, 'Header_Nav')]//button[starts-with(@class, 'Button_Button')]"); // Кнопка "Заказать" в шапке
    private final By orderButtonBody = By.xpath(".//div[starts-with(@class, 'Home_RoadMap')]//button[starts-with(@class, 'Button_Button')]"); // Кнопка "Заказать" внизу
    private final By cookieAcceptButton = By.id("rcc-confirm-button"); // Кнопка принятия cookies
    private final By yandexLogoLink = By.xpath(".//a[starts-with(@class,'Header_LogoYandex')]"); // Логотип Яндекса
    private final By scooterLogoLink = By.xpath(".//a[starts-with(@class,'Header_LogoScooter')]"); // Логотип Самоката

    // Конструктор
    public MainPage(WebDriver driver) {
        this.webDriver = driver;
    }

    // Ждём загрузки текста аккордеона
    public void waitForLoadItem(int index) {
        new WebDriverWait(this.webDriver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(this.webDriver.findElements(this.accordionItems).get(index)));
    }

    // Клик по кнопке принятия cookies
    public void clickOnCookieAcceptButton() {
        this.webDriver.findElement(this.cookieAcceptButton).click();
    }

    // Получение текста заголовка аккордеона
    public String getAccordionHeaderText(int index) {
        return this.webDriver.findElements(this.accordionHeaders).get(index).getText();
    }

    // Получение текста раскрытого элемента аккордеона
    public String getAccordionItemText(int index) {
        return this.webDriver.findElements(this.accordionItems).get(index).getText();
    }

    // Клик по заголовку аккордеона
    public void clickAccordionHeader(int index) {
        this.webDriver.findElements(this.accordionHeaders).get(index).click();
    }

    // Проверка, отображается ли элемент аккордеона
    public boolean isAccordionItemDisplayed(int index) {
        return this.webDriver.findElements(this.accordionItems).get(index).isDisplayed();
    }

    // Клик по кнопке "Заказать" в шапке
    public void clickOrderButtonHeader() {
        this.webDriver.findElement(this.orderButtonHeader).click();
    }

    // Клик по кнопке "Заказать" внизу страницы
    public void clickOrderButtonBody() {
        this.webDriver.findElement(this.orderButtonBody).click();
    }

    // Получение ссылки с логотипа Яндекса
    public String getYandexLogoLink() {
        return this.webDriver.findElement(this.yandexLogoLink).getAttribute("href");
    }

    // Получение ссылки с логотипа Самоката
    public String getScooterLogoLink() {
        return this.webDriver.findElement(this.scooterLogoLink).getAttribute("href");
    }

    // Проверка, что логотип Яндекса открывается в новой вкладке
    public boolean isYandexLogoLinkOpenedInNewTab() {
        String blank = "_blank";
        String value = this.webDriver.findElement(this.yandexLogoLink).getAttribute("target");
        return blank.equals(value);
    }
}