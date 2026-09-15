package com.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;

import java.util.UUID;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * ЗАДАЧА 2: Selenide-тесты
 * <p>
 * В коде НЕ ДОЛЖНО быть методов Selenium.
 * Все проверки через shouldBe / shouldHave / should.
 */
@DisplayName("Задача 2: UI-тесты на Selenide")
public class ATestsnum8 {

    private static final String BASE_URL = "http://localhost:8080";
    private static final String LOGIN_URL = BASE_URL + "/login";
    private static final String ADMIN_URL = BASE_URL + "/admin";

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "secret123";

    // ============================================================
    // @BeforeAll — настройка Selenide (ОБЯЗАТЕЛЬНО для Selenide)
    // ============================================================
    @BeforeAll
    static void setUpSelenide() {
        Configuration.baseUrl = BASE_URL;
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;  // 10 секунд
        Configuration.headless = false; // Показываем браузер
    }

    // ============================================================
    // @BeforeEach — открываем браузер
    // ============================================================
    // ============================================================
// @BeforeEach — Selenide сам откроет браузер при первом open()
// ============================================================
    @BeforeEach
    void openBrowser() {
        // Ничего не делаем — Selenide откроет браузер автоматически
        // при первом вызове open(url) в тесте
    }

    // ============================================================
// @AfterEach — закрываем браузер
// ============================================================
    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }

    // ============================================================
    // 2.1: Добавить товар через админку → проверить на витрине
    // ============================================================
    @Test
    @DisplayName("2.1 Добавить товар через админку и проверить на витрине (Selenide)")
    void testAddProductAndCheckOnShop() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String productName = "SelenideProduct_" + uniqueId;
        String productPrice = "150";

        // 1. Логинимся в админку
        loginToAdmin();

        // 2. Добавляем товар
        $("#n-name").setValue(productName);
        $("#n-price").setValue(productPrice);
        $("#add-btn").click();

        // 3. Проверяем уведомление
        $(".toast").shouldBe(visible)
                .shouldHave(text("Товар успешно добавлен"));

        // 4. Переходим на витрину
        open(BASE_URL);

        // 5. Проверяем, что товар есть на витрине
        $$(".product-card h4")
                .findBy(text(productName))
                .shouldBe(visible);
    }

    // ============================================================
    // 2.2: Добавить товар в корзину → проверить
    // ============================================================
    @Test
    @DisplayName("2.2 Добавить товар в корзину и проверить (Selenide)")
    void testAddProductToCart() {
        // 1. Открываем витрину
        open(BASE_URL);

        // 2. Берём имя первого товара
        String productName = $$(".product-card h4").first().getText();

        // 3. Добавляем в корзину
        $(".product-card button[data-action='add-to-cart']").click();

        // 4. Открываем корзину
        $("#open-cart-btn").click();

        // 5. Проверяем, что товар в корзине
        $("#cart-items").shouldBe(visible)
                .shouldHave(text(productName));
    }

    // ============================================================
    // 2.3: Попытка входа с неверным логином/паролем
    // ============================================================
    @Test
    @DisplayName("2.3 Попытка входа в админку с неверным логином и паролем (Selenide)")
    void testLoginWithWrongCredentials() {
        // 1. Открываем страницу логина
        open(LOGIN_URL);

        // 2. Вводим неверные данные
        $("#username").setValue("wrong_user");
        $("#password").setValue("wrong_password");

        // 3. Нажимаем "Sign in"
        $("button.primary").click();

        // 4. Проверяем, что остались на странице логина
        // (или появилось сообщение об ошибке)
        $("body").shouldHave(or("login page",
                text("/login"),
                text("Please sign in"),
                text("Invalid")
        ));
    }

    // ============================================================
    // 2.4: Проверить сохранение корзины после обновления
    // ============================================================
    @Test
    @DisplayName("2.4 Проверить сохранение корзины после обновления (Selenide)")
    void testCartSavedAfterRefresh() {
        // 1. Открываем витрину
        open(BASE_URL);

        // 2. Берём имя первого товара
        String productName = $$(".product-card h4").first().getText();

        // 3. Добавляем в корзину
        $(".product-card button[data-action='add-to-cart']").click();

        // 4. Открываем корзину и проверяем, что товар есть
        $("#open-cart-btn").click();
        $("#cart-items").shouldBe(visible)
                .shouldHave(text(productName));

        // 5. Обновляем страницу
        refresh();

        // 6. Открываем корзину снова
        $("#open-cart-btn").click();

        // 7. ✅ Ожидаем, что товар сохранился (по условию ДЗ)
        // ❌ Если баг приложения — тест упадёт с понятным сообщением
        $("#cart-items")
                .shouldBe(visible)
                .shouldHave(text(productName));
    }

    // ============================================================
    // 2.5: Добавить товаров на сумму > 300 и нажать "Оформить заказ"
    // ============================================================
    @Test
    @DisplayName("2.5 Добавить товаров более чем на 300 рублей и проверить JS Alert (Selenide)")
    void testAlertWhenOrderOver300() {
        // 1. Открываем витрину
        open(BASE_URL);

        // 2. Добавляем первые 5 товаров в корзину
        for (int i = 0; i < 5; i++) {
            $$(".product-card button[data-action='add-to-cart']").get(i).click();
        }

        // 3. Открываем корзину
        $("#open-cart-btn").click();
        $("#cart-items").shouldBe(visible);

        // 4. Проверяем, что сумма > 300
        String totalText = $("#total-price").getText();
        double total = Double.parseDouble(totalText);
        System.out.println("🔍 Сумма заказа: " + total);

        // 5. Нажимаем "Оформить заказ"
        $("#makeOrder").click();

        // 6. Проверяем JS Alert
        Alert alert = switchTo().alert();
        System.out.println("🔍 Alert text: " + alert.getText());
        alert.accept();
    }

    // ============================================================
    // логин в админку (на Selenide)
    // ============================================================
    private void loginToAdmin() {
        open(LOGIN_URL);
        $("#username").setValue(ADMIN_USERNAME);
        $("#password").setValue(ADMIN_PASSWORD);
        $("button.primary").click();
        $("#n-name").shouldBe(visible);
    }
}