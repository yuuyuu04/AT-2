package com.example.pages;

import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

/**
 * PageAssert для MainPage.
 * Наследуется от AbstractAssert.
 */
class MainPageAssert extends AbstractAssert<MainPageAssert, MainPage> {

    protected MainPageAssert(MainPage actual) {
        super(actual, MainPageAssert.class);
    }

    // ============================================================
    // ПРОВЕРКИ (только одна проверка на метод)
    // ============================================================

    public MainPageAssert isLoaded() {
        isNotNull();
        actual.pageTitle().shouldBe(visible);
        return this;
    }

    public MainPageAssert hasProduct(String name) {
        isNotNull();
        actual.productNames().findBy(text(name)).shouldBe(visible);
        return this;
    }

    public MainPageAssert hasProductsCount(int expected) {
        isNotNull();
        int actualCount = actual.getProductCount();
        if (actualCount != expected) {
            failWithMessage("Ожидалось товаров: %d, но было: %d", expected, actualCount);
        }
        return this;
    }

    public MainPageAssert cartIsVisible() {
        isNotNull();
        actual.cart().cartItems().shouldBe(visible);
        return this;
    }

    public MainPageAssert cartContainsProduct(String name) {
        isNotNull();
        actual.cart().cartItems().shouldHave(text(name));
        return this;
    }

    public MainPageAssert cartNotContainsProduct(String name) {
        isNotNull();
        actual.cart().cartItems().shouldNotHave(text(name));
        return this;
    }

    public MainPageAssert cartTotalIs(double expected) {
        isNotNull();
        double actualTotal = actual.cart().getTotal();
        if (Math.abs(actualTotal - expected) > 0.01) {
            failWithMessage("Ожидалась сумма: %.2f, но была: %.2f", expected, actualTotal);
        }
        return this;
    }

    public MainPageAssert cartCountIs(String expected) {
        isNotNull();
        actual.cartCount().shouldHave(text(expected));
        return this;
    }

    // ✅ Метод page() — возвращает страницу
    public MainPage page() {
        return actual;
    }
}
