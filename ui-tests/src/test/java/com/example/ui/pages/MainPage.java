package com.example.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.example.ui.asserts.MainPageAssert;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class MainPage {

    public SelenideElement pageTitle()     { return $("#main-title"); }
    public SelenideElement adminLink()     { return $("a[href='/admin']"); }
    public SelenideElement cartButton()    { return $("#open-cart-btn"); }
    public SelenideElement cartCount()     { return $("#cart-count"); }
    public ElementsCollection productCards()   { return $$(".product-card"); }
    public ElementsCollection productNames()   { return $$(".product-card h4"); }
    public ElementsCollection addToCartButtons() { return $$(".product-card button[data-action='add-to-cart']"); }

    public CartPopup cart = new CartPopup();

    @Step("Открыть главную страницу '{url}'")
    public MainPage openPage(String url) {
        open(url);
        pageTitle().shouldBe(visible, Duration.ofSeconds(15));
        return this;
    }

    @Step("Клик по ссылке 'Админка'")
    public MainPage clickAdminLink() {
        adminLink().click();
        return this;
    }

    @Step("Клик по кнопке 'Корзина'")
    public MainPage clickCartButton() {
        cartButton().click();
        return this;
    }

    @Step("Добавить товар с индексом {index} в корзину")
    public MainPage addToCartByIndex(int index) {
        addToCartButtons().get(index).click();
        return this;
    }

    @Step("Получить имя первого товара")
    public String getFirstProductName() {
        return productNames().first().getText();
    }

    @Step("Получить количество товаров")
    public int getProductCount() {
        return productCards().size();
    }

    @Step("Получить цену товара с индексом {index}")
    public double getProductPriceByIndex(int index) {
        String priceStr = productCards().get(index).getAttribute("data-price");
        return priceStr != null ? Double.parseDouble(priceStr) : 0;
    }

    public MainPageAssert check() {
        return new MainPageAssert(this);
    }

    // ============================================================
    // Вложенный класс — корзина
    // ============================================================
    public class CartPopup {

        public SelenideElement cartItems()    { return $("#cart-items"); }
        public SelenideElement totalPrice()   { return $("#total-price"); }
        public SelenideElement makeOrderBtn() { return $("#makeOrder"); }
        public ElementsCollection removeButtons() { return $$("#cart-items button[data-action='remove']"); }

        @Step("Ждать, что корзина открыта")
        public CartPopup shouldBeOpen() {
            cartItems().shouldBe(visible, Duration.ofSeconds(10));
            return this;
        }

        @Step("Получить итоговую сумму")
        public double getTotal() {
            return Double.parseDouble(totalPrice().getText());
        }

        @Step("Клик 'Оформить заказ'")
        public CartPopup clickMakeOrder() {
            makeOrderBtn().click();
            return this;
        }

        @Step("Удалить первый товар из корзины")
        public CartPopup removeFirst() {
            removeButtons().first().click();
            return this;
        }

        @Step("Проверить, что корзина содержит товар '{name}'")
        public CartPopup shouldContain(String name) {
            cartItems().shouldHave(text(name));
            return this;
        }

        @Step("Проверить, что корзина НЕ содержит товар '{name}'")
        public CartPopup shouldNotContain(String name) {
            cartItems().shouldNotHave(text(name));
            return this;
        }
    }
}
