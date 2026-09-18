package com.example;


import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;


import java.time.Duration;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ATestsnum10 {
    private static final String BASE_URL = "http://localhost:8080";
//1.1 Перетащить элемент в корзину с помощью Drag-and-Drop.
    @Test
    @DisplayName("1.1 Перетащить элемент в корзину через Drag-and-Drop")
    void testDragAndDropToCart() {
        // 1. Открываем витрину
        open(BASE_URL);
        $$(".product-card").first().shouldBe(visible, Duration.ofSeconds(15));

        // 2. Берём товар (что тащим)
        SelenideElement productCard = $$(".product-card").first();
        String productName = productCard.$("h4").getText();
        System.out.println("Перетаскиваем товар: " + productName);

        // 3. Кнопка корзины (куда тащим)
        SelenideElement cartButton = $("#open-cart-btn");

        // 4. Проверяем счётчик ДО
        String countBefore = $("#cart-count").getText();
        System.out.println("Счётчик корзины до: " + countBefore);

        // 5. Перетаскиваем товар в корзину
        actions().dragAndDrop(productCard, cartButton).perform();
        System.out.println("Drag-and-Drop выполнен");

        // 6. Проверяем счётчик ПОСЛЕ
        $("#cart-count").shouldNotHave(text(countBefore));
        String countAfter = $("#cart-count").getText();
        System.out.println("Счётчик корзины после: " + countAfter);

        // 7. Открываем корзину и проверяем товар
        cartButton.click();
        $("#cart-items").shouldBe(visible);
        $("#cart-items").shouldHave(text(productName));

        System.out.println("Товар в корзине: " + productName);
    }
// 1.2 Удалить добавленный элемент из корзины и проверить, что он там больше не отображается.
    @Test
    @DisplayName("1.2 Удалить товар из корзины")
    void testRemoveFromCart() {
        // 1. Открываем витрину
        open(BASE_URL);
        $$(".product-card").first().shouldBe(visible, Duration.ofSeconds(15));

        // 2. Добавляем товар в корзину (кликом или drag-and-drop)
        String productName = $$(".product-card h4").first().getText();
        $(".product-card button[data-action='add-to-cart']").click();
        System.out.println("Товар добавлен: " + productName);

        // 3. Открываем корзину
        $("#open-cart-btn").click();
        $("#cart-items").shouldBe(visible);

        // 4. Проверяем, что товар в корзине
        $("#cart-items").shouldHave(text(productName));
        System.out.println("Товар найден в корзине");

        // 5. Удаляем товар
        $("#cart-items button[data-action='remove']").click();
        System.out.println("Нажали 'Удалить'");

        // 6. Проверяем, что товара больше нет
        $("#cart-items").shouldNotHave(text(productName));
        System.out.println("Товар удалён из корзины");
    }
}
