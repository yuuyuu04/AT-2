package com.example.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * PageObject главной страницы с товарами.
 * Реализует паттерн Builder: все методы возвращают this.
 */
class MainPage {

    // ============================================================
    // ЭЛЕМЕНТЫ (5+)
    // ============================================================

    public SelenideElement pageTitle() {
        return $("#main-title");
    }

    public SelenideElement adminLink() {
        return $("a[href='/admin']");
    }

    public SelenideElement cartButton() {
        return $("#open-cart-btn");
    }

    public SelenideElement cartCount() {
        return $("#cart-count");
    }

    public ElementsCollection productCards() {
        return $$(".product-card");
    }

    public ElementsCollection productNames() {
        return $$(".product-card h4");
    }

    public ElementsCollection addToCartButtons() {
        return $$(".product-card button[data-action='add-to-cart']");
    }

    // Вложенный класс корзины
    public final CartPopup cartPopup = new CartPopup();

    public CartPopup cart() {
        return cartPopup;
    }

    // ============================================================
    // МЕТОДЫ (Builder — возвращают this)
    // ============================================================

    public MainPage openPage(String url) {
        open(url);
        pageTitle().shouldBe(visible, Duration.ofSeconds(15));
        return this;
    }

    public MainPage clickAdminLink() {
        adminLink().click();
        return this;
    }

    public MainPage clickCartButton() {
        cartButton().click();
        return this;
    }

    public MainPage addToCartByIndex(int index) {
        addToCartButtons().get(index).click();
        return this;
    }

    public MainPage addToCartByName(String name) {
        productCards().findBy(text(name))
                .$("button[data-action='add-to-cart']").click();
        return this;
    }

    public String getFirstProductName() {
        return productNames().first().getText();
    }

    public int getProductCount() {
        return productCards().size();
    }

    public double getProductPriceByIndex(int index) {
        String priceStr = productCards().get(index).getAttribute("data-price");
        return priceStr != null ? Double.parseDouble(priceStr) : 0;
    }

    // ✅ Метод check() — возвращает PageAssert
    public MainPageAssert check() {
        return new MainPageAssert(this);
    }

    // ============================================================
    // ВЛОЖЕННЫЙ КЛАСС — Корзина
    // ============================================================
    class CartPopup {

        public SelenideElement cartItems() {
            return $("#cart-items");
        }

        public SelenideElement totalPrice() {
            return $("#total-price");
        }

        public SelenideElement makeOrderButton() {
            return $("#makeOrder");
        }

        public ElementsCollection removeButtons() {
            return $$("#cart-items button[data-action='remove']");
        }

        public CartPopup shouldBeOpen() {
            cartItems().shouldBe(visible, Duration.ofSeconds(10));
            return this;
        }

        public double getTotal() {
            return Double.parseDouble(totalPrice().getText());
        }

        public CartPopup clickMakeOrder() {
            makeOrderButton().click();
            return this;
        }

        public CartPopup removeFirst() {
            removeButtons().first().click();
            return this;
        }
    }
}
