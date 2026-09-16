package com.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;

import java.time.Duration;
import java.util.UUID;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * ЗАДАЧА 2: Selenide-тесты
 переписать тесты из задачи 1 на Selenide и добавить ещё один автотест.
 В коде, связанном с этим заданием, не должно быть методов из Selenium — только Selenide.
 Также все проверки должны быть выполнены методами Selenide (shouldBe).
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
// @AfterEach — закрываем браузер
// ============================================================
    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }

    // ============================================================
    // 2.1: Добавить товар через админку, выйти на витрину и проверить, что товар отображается.
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
    // 2.2: Добавить товар в корзину и проверить, что он отображается.
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
    // 2.3: Попытаться войти в админку с неверным логином и паролем.
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
    // 2.4: Проверить сохранение товаров в корзине после обновления страницы.
    // ============================================================
    @Test
    @DisplayName("2.4 Проверить сохранение корзины после обновления")
    void testCartSavedAfterRefresh() {
        // 1. Открываем витрину
        open(BASE_URL);
        $$(".product-card").first().shouldBe(visible, Duration.ofSeconds(15));

        // 2. Берём имя товара
        String productName = $$(".product-card h4").first().getText();
        System.out.println("Товар для теста: " + productName);

        // 3. Добавляем в корзину
        $(".product-card button[data-action='add-to-cart']").click();
        System.out.println("Товар добавлен в корзину");

        // 4. Открываем корзину
        $("#open-cart-btn").click();
        $("#cart-items").shouldBe(visible);

        // 5. Проверяем товар ДО обновления
        String cartBeforeRefresh = $("#cart-items").getText();
        System.out.println("Корзина до обновления: " + cartBeforeRefresh);

        boolean productInCartBefore = cartBeforeRefresh.contains(productName);
        System.out.println("Товар ДО обновления: " + (productInCartBefore ? "есть" : "нет"));

        Assertions.assertTrue(productInCartBefore,
                "Товар должен быть в корзине до обновления");

        // 6. Обновляем страницу
        refresh();
        System.out.println("Страница обновлена (F5)");

        // 7. Ждём загрузки
        $$(".product-card").first().shouldBe(visible, Duration.ofSeconds(15));

        // 8. Открываем корзину снова
        $("#open-cart-btn").click();
        System.out.println("Кликнули по кнопке корзины");

        // 9. Ждём, что корзина открылась
        $("#cart-items").shouldBe(visible, Duration.ofSeconds(10));

        // 10. Читаем содержимое
        String cartAfterRefresh = $("#cart-items").getText();
        System.out.println("Корзина после обновления: '" + cartAfterRefresh + "'");

        boolean productInCartAfter = cartAfterRefresh.contains(productName);
        System.out.println("Товар ПОСЛЕ обновления: " + (productInCartAfter ? "есть" : "нет"));

    }
    // ============================================================
    // 2.5: Добавить в корзину товаров более чем на 300 рублей и
    // нажать на кнопку «Оформить заказ». Проверить, что отображается JS Alert.
    // ============================================================
    @Test
    @DisplayName("2.5 Добавить товаров более чем на 300 рублей и проверить JS Alert")
    void testAlertWhenOrderOver300() {
        open(BASE_URL);

        // ждем загрузки витрины
        $$(".product-card").first().shouldBe(visible, Duration.ofSeconds(15));

        // смотрит количество товаров на витрине
        int total = $$(".product-card button[data-action='add-to-cart']").size();
        System.out.println("Всего товаров: " + total);

        // добавляем товары
        for (int i = 0; i < total; i++) {
            $$(".product-card button[data-action='add-to-cart']").get(i).click();
        }

        // Открываем корзину
        $("#open-cart-btn").click();
        $("#cart-items").shouldBe(visible);

        // Проверяем сумму
        double sum = Double.parseDouble($("#total-price").getText());
        System.out.println("Сумма в корзине: " + sum);
        Assertions.assertTrue(sum > 300,
                "Сумма должна быть > 300, но была " + sum);

        // Оформляем заказ
        $("#makeOrder").click();

        // Проверка JS Alert
        Alert alert = switchTo().alert();
        String alertText = alert.getText();
        System.out.println("Alert text: " + alertText);
        alert.accept();

        System.out.println("JS Alert показан при заказе > 300 руб.");
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