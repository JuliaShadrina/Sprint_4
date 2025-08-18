package ru.yandex.praktikum.scooter.scooter.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

// Страница оформления заказа
public class OrderPage {
    private final WebDriver webDriver;
    private final WebDriverWait wait;

    // Локаторы элементов формы
    private final By orderForm = By.xpath(".//div[starts-with(@class, 'Order_Form')]");
    private final By nameInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Имя')]");
    private final By surnameInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Фамилия')]");
    private final By addressInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Адрес')]");
    private final By metroInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Станция метро')]");
    private final By phoneInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Телефон')]");
    private final By commentInput = By.xpath(".//div[starts-with(@class, 'Order_Form')]//input[contains(@placeholder,'Комментарий')]");
    private final By metroList = By.className("select-search__select");
    private final By metroListItems = By.xpath(".//div[@class='select-search__select']//div[starts-with(@class,'Order_Text')]");
    private final By nextButton = By.xpath(".//div[starts-with(@class, 'Order_NextButton')]/button");
    private final By dateSelected = By.className("react-datepicker__day--selected");
    private final By dateInput = By.xpath(".//div[starts-with(@class, 'react-datepicker__input-container')]//input");
    private final By termDropdownRoot = By.className("Dropdown-root");
    private final By termDropdownOption = By.className("Dropdown-option");
    private final By colorLabels = By.xpath(".//div[starts-with(@class, 'Order_Checkboxes')]//label");
    private final By orderButton = By.xpath(".//div[starts-with(@class, 'Order_Buttons')]/button[not(contains(@class,'Button_Inverted'))]");
    private final By acceptOrderButton = By.xpath(".//div[starts-with(@class, 'Order_Modal')]//button[not(contains(@class,'Button_Inverted'))]");
    private final By newOrderSuccessMessage = By.xpath(".//div[starts-with(@class, 'Order_Modal')]//div[(starts-with(@class,'Order_ModalHeader'))]");

    // Конструктор
    public OrderPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
    }

    // Ждём пока загрузится форма заказа
    public void waitForLoadForm() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderForm));
    }

    // Универсальный ввод текста в поле
    private void setInputValue(By locator, String value) {
        WebElement element = webDriver.findElement(locator);
        element.clear();
        element.sendKeys(value);
    }

    // Заполнение полей формы
    public void setName(String name) {
        setInputValue(nameInput, name);
    }

    public void setSurname(String surname) {
        setInputValue(surnameInput, surname);
    }

    public void setAddress(String address) {
        setInputValue(addressInput, address);
    }

    public void setPhone(String phone) {
        setInputValue(phoneInput, phone);
    }

    public void setComment(String comment) {
        setInputValue(commentInput, comment);
    }

    // Ввод станции метро и выбор из списка
    public void setMetro(String metro) {
        setInputValue(metroInput, metro);
        wait.until(ExpectedConditions.visibilityOfElementLocated(metroList));
        chooseElementFromDropdown(metroListItems, metro);
    }

    // Кнопка "Далее"
    public void clickNextButton() {
        webDriver.findElement(nextButton).click();
    }

    // Ввод даты и подтверждение выбора
    public void setDate(String date) {
        setInputValue(dateInput, date);
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateSelected));
        webDriver.findElement(dateSelected).click();
    }

    // Выбор срока аренды
    public void setTerm(String termToChoose) {
        webDriver.findElement(termDropdownRoot).click();
        chooseElementFromDropdown(termDropdownOption, termToChoose);
    }

    // Выбор цвета
    public void setColor(String colorToChoose) {
        chooseElementFromDropdown(colorLabels, colorToChoose);
    }

    // Полный сценарий оформления заказа
    public void makeOrder(String name, String surname, String address, String metro, String phone, String date, String term, String color, String comment) {
        waitForLoadForm();
        setName(name);
        setSurname(surname);
        setAddress(address);
        setMetro(metro);
        setPhone(phone);

        clickNextButton();
        setDate(date);
        setTerm(term);
        setColor(color);
        setComment(comment);

        webDriver.findElement(orderButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(acceptOrderButton));
        webDriver.findElement(acceptOrderButton).click();
    }

    // Текст уведомления об успешном заказе
    public String getNewOrderSuccessMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(newOrderSuccessMessage));
        return webDriver.findElement(newOrderSuccessMessage).getText();
    }

    // Поиск и выбор элемента из списка
    private void chooseElementFromDropdown(By dropdownElements, String elementToChoose) {
        List<WebElement> elements = webDriver.findElements(dropdownElements);
        for (WebElement element : elements) {
            if (element.getText().trim().equalsIgnoreCase(elementToChoose.trim())) {
                element.click();
                return;
            }
        }
        throw new RuntimeException("Элемент '" + elementToChoose + "' не найден в списке.");
    }
}