package com.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

/**
 * ЗАДАЧА 3: Расширенные UI-тесты на Selenide
 */
@DisplayName("Задача 3: Расширенные UI-тесты")
public class ATestsnum9 {

    private static final String BASE_URL = "http://localhost:8080";
    private static final String LOGIN_URL = BASE_URL + "/login";
    private static final String ADMIN_URL = BASE_URL + "/admin";
    private static final String API_URL = "http://localhost:8080";

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "secret123";

    // Для 3.6 — храним ID товара, созданного через API
    private String createdProductId;

    @BeforeAll
    static void setUpAll() {
        // Настройка Selenide
        Configuration.baseUrl = BASE_URL;
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
        Configuration.headless = false;

        // Настройка REST Assured
        RestAssured.baseURI = API_URL;
    }

    @AfterEach
    void tearDown() {
        //   3.6: Удаляем товар, созданный через API
        if (createdProductId != null) {
            given()
                    .auth().basic(ADMIN_USERNAME, ADMIN_PASSWORD)
                    .pathParam("id", createdProductId)
                    .when()
                    .delete("/goods/{id}")
                    .then()
                    .log().ifValidationFails()
                    .statusCode(200);

            System.out.println("🗑Товар с ID " + createdProductId + " удалён");
            createdProductId = null;
        }

        // Закрываем браузер
        Selenide.closeWebDriver();
    }

    // ============================================================
    // 3.1: Добавить 3 единицы товара → оплатить
    // ============================================================
    @Test
    @DisplayName("3.1 Добавить 3 единицы товара и оплатить (сумма ≤ 300)")
    void testOrderThreeItems() {
        open(BASE_URL);

        //   ЖДЁМ ЗАГРУЗКИ ВИТРИНЫ
        $$(".product-card").first().shouldBe(visible, Duration.ofSeconds(15));

        //   СОБИРАЕМ ВСЕ ЦЕНЫ ТОВАРОВ
        List<Double> prices = new ArrayList<>();
        for (SelenideElement card : $$(".product-card")) {
            String priceStr = card.getAttribute("data-price");
            if (priceStr != null) {
                prices.add(Double.parseDouble(priceStr));
            }
        }

        System.out.println("Доступные цены: " + prices);

        //   НАХОДИМ ПОДХОДЯЩИЙ ТОВАР (цена × 3 ≤ 300)
        int suitableIndex = -1;
        for (int i = 0; i < prices.size(); i++) {
            if (prices.get(i) * 3 <= 300) {
                suitableIndex = i;
                break;
            }
        }

        Assertions.assertTrue(suitableIndex >= 0,
                "Не найден товар, у которого 3 шт ≤ 300 руб. Все цены: " + prices);

        double expectedPrice = prices.get(suitableIndex);
        System.out.println("Выбран товар с ценой: " + expectedPrice
                + " (3 шт = " + (expectedPrice * 3) + ")");

        //   ДОБАВЛЯЕМ ТОВАР 3 РАЗА В КОРЗИНУ
        SelenideElement suitableCard = $$(".product-card").get(suitableIndex);
        suitableCard.$("button[data-action='add-to-cart']").click();
        suitableCard.$("button[data-action='add-to-cart']").click();
        suitableCard.$("button[data-action='add-to-cart']").click();

        // Открываем корзину
        $("#open-cart-btn").click();
        $("#cart-items").shouldBe(visible);

        //   ПРОВЕРЯЕМ, ЧТО КОЛИЧЕСТВО = 3
        $("#cart-items .qty-controls span").shouldHave(text("3"));

        //   ПРОВЕРЯЕМ СУММУ
        double total = Double.parseDouble($("#total-price").getText());
        System.out.println("Сумма заказа: " + total);
        Assertions.assertTrue(total <= 300,
                "Сумма должна быть ≤ 300, но была " + total);

        //   ОФОРМЛЯЕМ ЗАКАЗ
        $("#makeOrder").click();

        //   ПРОВЕРЯЕМ ТОСТ
        $(".toast").shouldBe(visible)
                .shouldHave(text("Заказ принят в обработку!"));

        System.out.println("  Уведомление об обработке заказа показано");
    }
    // ============================================================
    // 3.2: Разные товары → проверить сумму
    // ============================================================
    @Test
    @DisplayName("3.2 Добавить разные товары и проверить сумму")
    void testSumOfDifferentProducts() {
        open(BASE_URL);

        // Добавляем 3 разных товара
        $$(".product-card button[data-action='add-to-cart']").get(0).click();
        $$(".product-card button[data-action='add-to-cart']").get(1).click();
        $$(".product-card button[data-action='add-to-cart']").get(2).click();

        // Открываем корзину
        $("#open-cart-btn").click();
        $("#cart-items").shouldBe(visible);

        // Считаем ожидаемую сумму из data-price
        double expectedTotal = 0;
        for (int i = 0; i < 3; i++) {
            String price = $$(".product-card").get(i).getAttribute("data-price");
            expectedTotal += Double.parseDouble(price);
        }

        // Получаем фактическую сумму
        double actualTotal = Double.parseDouble($("#total-price").getText());

        System.out.println("Ожидаемая сумма: " + expectedTotal);
        System.out.println("Фактическая сумма: " + actualTotal);

        Assertions.assertEquals(expectedTotal, actualTotal, 0.01,
                "Сумма в корзине должна быть " + expectedTotal);
    }

    // ============================================================
    // 3.3: Добавить товар через админку → уведомление
    // ============================================================
    @Test
    @DisplayName("3.3 Добавить товар через админку и проверить уведомление")
    void testAddProductNotification() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String productName = "ExtendedProduct_" + uniqueId;

        // Логинимся в админку
        loginToAdmin();

        // Добавляем товар
        $("#n-name").setValue(productName);
        $("#n-price").setValue("99");
        $("#add-btn").click();

        //   Проверяем уведомление
        $(".toast").shouldBe(visible)
                .shouldHave(text("Товар успешно добавлен"));

        System.out.println("  Уведомление о добавлении товара показано");
    }

    // ============================================================
    // 3.4: Отредактировать товар → проверить изменения
    // ============================================================
    @Test
    @DisplayName("3.4 Отредактировать товар и проверить изменения")
    void testEditProduct() {
        //   Создаём товар через API
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String originalName = "EditMe_" + uniqueId;
        String newName = "Edited_" + uniqueId;

        createdProductId = createProductViaApi(originalName, 100.0);
        System.out.println("Товар создан с ID: " + createdProductId);

        //   Логинимся в админку
        loginToAdmin();

        //   ЖДЁМ ПОЯВЛЕНИЯ ИНПУТА ИМЕНИ ПО ID
        $("#nm-" + createdProductId)
                .shouldBe(visible, Duration.ofSeconds(20));

        System.out.println("  Товар найден по ID: " + createdProductId);

        //   МЕНЯЕМ ЗНАЧЕНИЕ ИМЕНИ
        $("#nm-" + createdProductId).setValue(newName);

        //   КЛИКАЕМ "СОХРАНИТЬ" ПО data-id
        $("button[data-action='update'][data-id='" + createdProductId + "']")
                .click();

        //   ЖДЁМ, ЧТО НОВОЕ ЗНАЧЕНИЕ ПРИМЕНИЛОСЬ
        // (проверяем через API — самый надёжный способ)
        Response check = given()
                .auth().basic(ADMIN_USERNAME, ADMIN_PASSWORD)
                .pathParam("id", createdProductId)
                .when()
                .get("/goods/{id}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String actualName = check.jsonPath().getString("name");
        System.out.println("Имя в API после обновления: " + actualName);

        Assertions.assertEquals(newName, actualName,
                "Имя товара должно было измениться на " + newName);

        System.out.println(" Изменения применены: " + originalName + " → " + newName);
    }

// ============================================================
// ВСПОМОГАТЕЛЬНЫЙ МЕТОД: создание товара через API
// ============================================================
    private String createProductViaApi(String name, double price) {
        Response response = given()
                .auth().basic(ADMIN_USERNAME, ADMIN_PASSWORD)
                .contentType("application/json")
                .body(Map.of("name", name, "price", price))
                .when()
                .post("/goods/add")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String id = String.valueOf(response.jsonPath().getInt("data.id"));
        System.out.println("Создан товар '" + name + "' через API с ID: " + id);
        return id;
    }
    // ============================================================
    // ВСПОМОГАТЕЛЬНЫЙ МЕТОД: логин в админку
    // ============================================================
    private void loginToAdmin() {
        open(LOGIN_URL);
        $("#username").setValue(ADMIN_USERNAME);
        $("#password").setValue(ADMIN_PASSWORD);
        $("button.primary").click();
        $("#n-name").shouldBe(visible);
    }
}