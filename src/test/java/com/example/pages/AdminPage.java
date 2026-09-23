package com.example.pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

class AdminPage {

    public SelenideElement nameField() {
        return $("#n-name");
    }

    public SelenideElement priceField() {
        return $("#n-price");
    }

    public SelenideElement createButton() {
        return $("#add-btn");
    }

    public SelenideElement toast() {
        return $(".toast");
    }

    public SelenideElement nameInputById(String id) {
        return $("#nm-" + id);
    }

    public SelenideElement updateButtonById(String id) {
        return $("button[data-action='update'][data-id='" + id + "']");
    }

    public SelenideElement nameInputByValue(String value) {
        return $("input[value='" + value + "']");
    }

    // ============================================================
    // МЕТОДЫ
    // ============================================================

    public AdminPage shouldBeLoaded() {
        nameField().shouldBe(visible, Duration.ofSeconds(15));
        return this;
    }

    public AdminPage addProduct(String name, double price) {
        nameField().setValue(name);
        priceField().setValue(String.valueOf(price));
        createButton().click();
        return this;
    }

    public AdminPage changeName(String id, String newName) {
        nameInputById(id).setValue(newName);
        return this;
    }

    public AdminPage clickUpdate(String id) {
        updateButtonById(id).click();
        return this;
    }

    public String findIdByName(String name) {
        String id = nameInputByValue(name).getAttribute("id");
        return id != null ? id.replace("nm-", "") : null;
    }

    public AdminPageAssert check() {
        return new AdminPageAssert(this);
    }
}