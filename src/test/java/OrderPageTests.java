import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.pageObjects.MainPage;
import org.pageObjects.OrderPage;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.containsString;

// Тесты для проверки процесса оформления заказа
@RunWith(Parameterized.class)
public class OrderPageTests {

    private WebDriver webDriver;

    private final String mainPageUrl = "https://qa-scooter.praktikum-services.ru";

    // Тестовые данные для оформления заказа
    private final String name, surname, address, metro, phone, date, term, color, comment;

    // Ожидаемый текст сообщения об успешном оформлении заказа
    private final String expectedOrderSuccessText = "Заказ оформлен";

    // Конструктор для передачи параметров
    public OrderPageTests(
            String name,
            String surname,
            String address,
            String metro,
            String phone,
            String date,
            String term,
            String color,
            String comment
    ) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.term = term;
        this.color = color;
        this.comment = comment;
    }

    // Параметры для запуска теста
    @Parameterized.Parameters(name = "Оформление заказа. Позитивный сценарий. Пользователь: {0} {1}")
    public static Object[][] setDataForOrder() {
        return new Object[][] {
                {
                        "Мария",
                        "Смирнова",
                        "Москва, ул. Арбат, д. 12, кв. 7",
                        "Арбатская",
                        "79005551234",
                        "25.08.2025",
                        "сутки",
                        "чёрный жемчуг",
                        "Позвоните за час до приезда"
                },
                {
                        "Алексей",
                        "Кузнецов",
                        "Москва, проспект Мира, д. 100, кв. 25",
                        "ВДНХ",
                        "79261112233",
                        "10.09.2025",
                        "двое суток",
                        "серая безысходность",
                        "Не звоните в домофон — сразу наберите"
                },
                {
                        "Елена",
                        "Фёдорова",
                        "Москва, Ленинградский проспект, д. 45, кв. 11",
                        "Динамо",
                        "79876543210",
                        "20.08.2025",
                        "пятеро суток",
                        "чёрный жемчуг",
                        "Доставить после 18:00"
                }
        };
    }

    @Before
    public void startUp() {
        // Настройка драйвера Firefox перед запуском тестов
        WebDriverManager.firefoxdriver().setup();
        this.webDriver = new FirefoxDriver();
        this.webDriver.get(mainPageUrl);
    }

    @After
    public void tearDown() {
        // Закрытие браузера после завершения тестов
        if (this.webDriver != null) {
            this.webDriver.quit();
        }
    }

    // Проверка оформления заказа через кнопку "Заказать" в шапке
    @Test
    public void orderWithHeaderButtonWhenSuccess() {
        MainPage mainPage = new MainPage(this.webDriver);
        OrderPage orderPage = new OrderPage(this.webDriver);

        // Принятие куки и переход к форме заказа
        mainPage.clickOnCookieAcceptButton();
        mainPage.clickOrderButtonHeader();

        // Заполнение формы заказа
        orderPage.makeOrder(this.name, this.surname, this.address, this.metro, this.phone, this.date, this.term, this.color, this.comment);

        // Проверка, что заказ успешно оформлен
        MatcherAssert.assertThat(
                "Problem with creating a new order",
                orderPage.getNewOrderSuccessMessage(),
                containsString(this.expectedOrderSuccessText)
        );
    }

    // Проверка оформления заказа через кнопку "Заказать" в теле сайта
    @Test
    public void orderWithBodyButtonWhenSuccess() {
        MainPage mainPage = new MainPage(this.webDriver);
        OrderPage orderPage = new OrderPage(this.webDriver);

        // Принятие куки и переход к форме заказа
        mainPage.clickOnCookieAcceptButton();
        mainPage.clickOrderButtonBody();

        // Заполнение формы заказа
        orderPage.makeOrder(this.name, this.surname, this.address, this.metro, this.phone, this.date, this.term, this.color, this.comment);

        // Проверка, что заказ успешно оформлен
        MatcherAssert.assertThat(
                "Problem with creating a new order",
                orderPage.getNewOrderSuccessMessage(),
                containsString(this.expectedOrderSuccessText)
        );
    }
}