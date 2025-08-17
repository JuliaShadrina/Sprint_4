import org.pageObjects.MainPage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.hamcrest.MatcherAssert;

import static org.hamcrest.CoreMatchers.equalTo;

// Тесты для проверки работы и содержимого блока "Вопросы о важном"
@RunWith(Parameterized.class)
public class MainPageFAQAccordionTests {

    private WebDriver webDriver;
    private final String mainPageUrl = "https://qa-scooter.praktikum-services.ru";

    // Номер элемента аккордеона
    private final int numberOfElement;
    // Ожидаемый текст в заголовке
    private final String expectedHeaderText;
    // Ожидаемый текст в раскрывающемся блоке
    private final String expectedItemText;

    // Конструктор для параметров теста
    public MainPageFAQAccordionTests(int numberOfAccordionItem, String expectedHeaderText, String expectedItemText) {
        this.numberOfElement = numberOfAccordionItem;
        this.expectedHeaderText = expectedHeaderText;
        this.expectedItemText = expectedItemText;
    }

    // Параметры для параметризованных тестов
    @Parameterized.Parameters(name = "Тест аккордеона: {1}")
    public static Object[][] setTestData() {
        return new Object[][] {
                {0, "Сколько это стоит? И как оплатить?",
                        "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},

                {1, "Хочу сразу несколько самокатов! Так можно?",
                        "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},

                {2, "Как рассчитывается время аренды?",
                        "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},

                {3, "Можно ли заказать самокат прямо на сегодня?",
                        "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},

                {4, "Можно ли продлить заказ или вернуть самокат раньше?",
                        "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},

                {5, "Вы привозите зарядку вместе с самокатом?",
                        "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},

                {6, "Можно ли отменить заказ?",
                        "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},

                {7, "Я живу за МКАДом, привезёте?",
                        "Да, обязательно. Всем самокатов! И Москве, и Московской области." },
        };
    }

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

    // Проверка, что элемент аккордеона отображается
    @Test
    public void checkAccordionItemIsDisplayed() {
        MainPage mainPage = new MainPage(this.webDriver);

        mainPage.clickOnCookieAcceptButton();
        mainPage.clickAccordionHeader(this.numberOfElement);
        mainPage.waitForLoadItem(this.numberOfElement);

        MatcherAssert.assertThat("Accordion item #" + this.numberOfElement + " didn't load",
                mainPage.isAccordionItemDisplayed(this.numberOfElement), equalTo(true));
    }

    // Проверка текста в заголовке и раскрывающемся блоке
    @Test
    public void checkAccordionTextIsCorrect() {
        MainPage mainPage = new MainPage(this.webDriver);

        mainPage.clickOnCookieAcceptButton();
        mainPage.clickAccordionHeader(this.numberOfElement);
        mainPage.waitForLoadItem(this.numberOfElement);

        // Проверяем текст заголовка
        MatcherAssert.assertThat("Wrong text in accordion header #" + this.numberOfElement,
                mainPage.getAccordionHeaderText(this.numberOfElement),
                equalTo(this.expectedHeaderText));

        // Проверяем текст блока
        MatcherAssert.assertThat("Wrong text in accordion item #" + this.numberOfElement,
                mainPage.getAccordionItemText(this.numberOfElement),
                equalTo(this.expectedItemText));
    }
}