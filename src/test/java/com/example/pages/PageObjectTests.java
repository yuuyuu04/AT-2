package com.example.pages;

import com.example.ATestsConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("PageObject Tests")
class PageObjectTests extends BaseTest {

    private static final String UI_URL = ATestsConfig.getUiUrl();
    private static final String ADMIN_USER = ATestsConfig.getAdminUsername();
    private static final String ADMIN_PASS = ATestsConfig.getAdminPassword();

    // ============================================================
    // 2.1: 3 единицы товара, оплата ≤ 300
    // ============================================================
    @Test
    @DisplayName("2.1 Добавить 3 единицы товара и оплатить")
    void testOrderThreeItems() {
        mainPage.openPage(UI_URL);

        // Ищем дешёвый товар
        int index = -1;
        for (int i = 0; i < mainPage.getProductCount(); i++) {
            if (mainPage.getProductPriceByIndex(i) * 3 <= 300) {
                index = i;
                break;
            }
        }
        assertThat(index).as("Не найден товар ≤ 100 руб.").isGreaterThanOrEqualTo(0);

        // Добавляем 3 раза
        mainPage.addToCartByIndex(index)
                .addToCartByIndex(index)
                .addToCartByIndex(index)
                .clickCartButton();

        // Проверяем сумму
        mainPage.check()
                .cartIsVisible()
                .cartTotalIs(mainPage.getProductPriceByIndex(index) * 3);

        // Оформляем
        mainPage.cart().clickMakeOrder();
        // Проверяем alert или тост
        // (в реальном тесте — switchTo().alert().accept() или проверка тоста)
    }

    // ============================================================
    // 2.2: Разные товары, проверка суммы
    // ============================================================
    @Test
    @DisplayName("2.2 Разные товары, проверка суммы")
    void testSumOfDifferentProducts() {
        mainPage.openPage(UI_URL);

        // Считаем ожидаемую сумму
        double expected = mainPage.getProductPriceByIndex(0)
                + mainPage.getProductPriceByIndex(1)
                + mainPage.getProductPriceByIndex(2);

        mainPage.addToCartByIndex(0)
                .addToCartByIndex(1)
                .addToCartByIndex(2)
                .clickCartButton();

        mainPage.check().cartTotalIs(expected);
    }

    // ============================================================
    // 2.3: Добавить товар через админку
    // ============================================================
    @Test
    @DisplayName("2.3 Добавить товар через админку")
    void testAddProductViaAdmin() {
        String name = "PO_" + UUID.randomUUID().toString().substring(0, 8);

        loginPage.openPage(UI_URL)
                .login(ADMIN_USER, ADMIN_PASS);

        adminPage.shouldBeLoaded()
                .addProduct(name, 100.0);

        adminPage.check().hasToast("Товар успешно добавлен");
    }

    // ============================================================
    // 2.4: Редактировать товар через админку
    // ============================================================
    @Test
    @DisplayName("2.4 Редактировать товар через админку")
    void testEditProduct() {
        String name = "Edit_" + UUID.randomUUID().toString().substring(0, 8);
        String newName = "New_" + UUID.randomUUID().toString().substring(0, 8);

        loginPage.openPage(UI_URL)
                .login(ADMIN_USER, ADMIN_PASS);

        adminPage.shouldBeLoaded()
                .addProduct(name, 100.0);

        // Находим ID
        String id = adminPage.findIdByName(name);

        // Меняем и сохраняем
        adminPage.changeName(id, newName)
                .clickUpdate(id);

        // Проверяем
        adminPage.check().nameHasValue(id, newName);
    }
}
