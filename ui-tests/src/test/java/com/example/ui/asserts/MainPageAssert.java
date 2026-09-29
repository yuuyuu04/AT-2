package com.example.ui.asserts;

import com.example.ui.pages.MainPage;
import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class MainPageAssert extends AbstractAssert<MainPageAssert, MainPage> {

    public MainPageAssert(MainPage actual) {
        super(actual, MainPageAssert.class);
    }

    @Step("Проверить, что главная страница загружена")
    public MainPageAssert isLoaded() {
        isNotNull();
        actual.pageTitle().shouldBe(visible);
        return this;
    }

    @Step("Проверить, что корзина содержит товар '{name}'")
    public MainPageAssert cartContainsProduct(String name) {
        isNotNull();
        actual.cart.cartItems().shouldHave(text(name));
        return this;
    }

    @Step("Проверить, что корзина НЕ содержит товар '{name}'")
    public MainPageAssert cartNotContainsProduct(String name) {
        isNotNull();
        actual.cart.cartItems().shouldNotHave(text(name));
        return this;
    }

    @Step("Проверить, что итоговая сумма = {expected}")
    public MainPageAssert cartTotalIs(double expected) {
        isNotNull();
        double actualTotal = actual.cart.getTotal();
        if (Math.abs(actualTotal - expected) > 0.01) {
            failWithMessage("Ожидалась сумма %.2f, но была %.2f", expected, actualTotal);
        }
        return this;
    }

    public MainPage page() {
        return actual;
    }
}
