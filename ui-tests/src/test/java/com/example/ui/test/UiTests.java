package com.example.ui.test;

import com.example.common.config.ConfigReader;
import com.example.common.utils.RandomUtils;
import com.example.ui.pages.MainPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("UI Tests")
@Feature("Интернет-магазин")
@DisplayName("UI тесты")
public class UiTests extends BaseUiTest {

    private static final String UI_URL = ConfigReader.getUiUrl();

// ============================================================
// 2.1: Добавить 3 единицы товара и оплатить (≤ 300)
// ============================================================
@Test
@Tag("smoke")
@Story("Корзина")
@Severity(SeverityLevel.CRITICAL)
@DisplayName("2.1 Добавить 3 единицы товара и оплатить")
void testOrderThreeItems() {
    mainPage.openPage(UI_URL);

    int index = -1;
    for (int i = 0; i < mainPage.getProductCount(); i++) {
        if (mainPage.getProductPriceByIndex(i) * 3 <= 300) {
            index = i;
            break;
        }
    }
    assertThat(index).as("Не найден товар ≤ 100 руб.").isGreaterThanOrEqualTo(0);

    mainPage.addToCartByIndex(index)
            .addToCartByIndex(index)
            .addToCartByIndex(index)
            .clickCartButton();

    mainPage.cart.shouldBeOpen();

    mainPage.check().cartTotalIs(mainPage.getProductPriceByIndex(index) * 3);

    mainPage.cart.clickMakeOrder();
}

    // ============================================================
    // 2.2: Разные товары, проверка суммы
    // ============================================================
    @Test
    @Story("Корзина")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("2.2 Разные товары, проверка суммы")
    void testSumOfDifferentProducts() {
        mainPage.openPage(UI_URL);

        double expected = mainPage.getProductPriceByIndex(0)
                + mainPage.getProductPriceByIndex(1)
                + mainPage.getProductPriceByIndex(2);

        mainPage.addToCartByIndex(0)
                .addToCartByIndex(1)
                .addToCartByIndex(2)
                .clickCartButton();

        mainPage.cart.shouldBeOpen();

        mainPage.check().cartTotalIs(expected);
    }

    // ============================================================
    // 2.3: Добавить товар через админку
    // ============================================================
    @Test
    @Tag("smoke")
    @Story("Админка")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("2.3 Добавить товар через админку")
    void testAddProductViaAdmin() {
        String name = RandomUtils.randomName("PO");

        authSteps.loginAsAdmin(UI_URL);
        adminPage.shouldBeLoaded()
                .addProduct(name, 100.0);

        adminPage.check().hasToast("Товар успешно добавлен");
    }

    // ============================================================
    // 2.4: Редактировать товар через админку
    // ============================================================
    @Test
    @Story("Админка")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("2.4 Редактировать товар через админку")
    void testEditProduct() {
        String name = RandomUtils.randomName("Edit");
        String newName = RandomUtils.randomName("New");

        authSteps.loginAsAdmin(UI_URL);
        adminPage.shouldBeLoaded()
                .addProduct(name, 100.0);

        String id = adminPage.findIdByName(name);

        adminPage.changeName(id, newName)
                .clickUpdate(id);

        adminPage.check().nameHasValue(id, newName);
    }
}