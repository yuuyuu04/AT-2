package com.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class ATestsnum11 {

    private static final String BASE_URL = ATestsConfig.getUiUrl();

    @BeforeAll
    static void setUp() {
        // Выводим конфиг
        ATestsConfig.printConfig();

        // Настраиваем Selenide
        Configuration.baseUrl = ATestsConfig.getUiUrl();
        Configuration.timeout = ATestsConfig.getTimeout();
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.headless = false;

        // Настраиваем REST Assured
        RestAssured.baseURI = ATestsConfig.getApiUrl();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        if (ATestsConfig.isLoggingEnabled()) {
            Configuration.reportsFolder = "build/reports/tests";
        }

        // Создаём стартовый товар
        createStartProduct();
    }

    //  Метод создания стартового товара
    private static void createStartProduct() {
        String name = ATestsConfig.getTestProductName();
        double price = ATestsConfig.getTestProductPrice();

        given()
                .auth().basic(ATestsConfig.getAdminUsername(), ATestsConfig.getAdminPassword())
                .contentType("application/json")
                .body(Map.of("name", name, "price", price))
                .when()
                .post("/goods/add")
                .then()
                .statusCode(200);

        System.out.println("Стартовый товар создан: " + name + " за " + price);
    }

    // ============================================================
    // 1.1 Перетащить элемент в корзину через Drag-and-Drop
    // ============================================================
    @Test
    @DisplayName("1.1 Перетащить элемент в корзину через Drag-and-Drop")
    void testDragAndDropToCart() {
        open(BASE_URL);
        $$(".product-card").first().shouldBe(visible, Duration.ofSeconds(15));

        SelenideElement productCard = $$(".product-card").first();
        String productName = productCard.$("h4").getText();
        System.out.println("Перетаскиваем товар: " + productName);

        SelenideElement cartButton = $("#open-cart-btn");

        String countBefore = $("#cart-count").getText();
        System.out.println("Счётчик корзины до: " + countBefore);

        actions().dragAndDrop(productCard, cartButton).perform();
        System.out.println("Drag-and-Drop выполнен");

        $("#cart-count").shouldNotHave(text(countBefore));
        String countAfter = $("#cart-count").getText();
        System.out.println("Счётчик корзины после: " + countAfter);

        cartButton.click();
        $("#cart-items").shouldBe(visible);
        $("#cart-items").shouldHave(text(productName));

        System.out.println("Товар в корзине: " + productName);
    }

    // ============================================================
    // 1.2 Удалить добавленный элемент из корзины
    // ============================================================
    @Test
    @DisplayName("1.2 Удалить товар из корзины")
    void testRemoveFromCart() {
        open(BASE_URL);
        $$(".product-card").first().shouldBe(visible, Duration.ofSeconds(15));

        String productName = $$(".product-card h4").first().getText();
        $(".product-card button[data-action='add-to-cart']").click();
        System.out.println("Товар добавлен: " + productName);

        $("#open-cart-btn").click();
        $("#cart-items").shouldBe(visible);

        $("#cart-items").shouldHave(text(productName));
        System.out.println("Товар найден в корзине");

        $("#cart-items button[data-action='remove']").click();
        System.out.println("Нажали 'Удалить'");

        $("#cart-items").shouldNotHave(text(productName));
        System.out.println("Товар удалён из корзины");
    }
}