package com.example.ui.pages;

import com.codeborne.selenide.SelenideElement;
import com.example.ui.asserts.AdminPageAssert;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class AdminPage {

    public SelenideElement nameField()   { return $("#n-name"); }
    public SelenideElement priceField()  { return $("#n-price"); }
    public SelenideElement createButton() { return $("#add-btn"); }
    public SelenideElement toast()       { return $(".toast"); }

    public SelenideElement nameInputById(String id) {
        return $("#nm-" + id);
    }

    public SelenideElement updateButtonById(String id) {
        return $("button[data-action='update'][data-id='" + id + "']");
    }

    public SelenideElement nameInputByValue(String value) {
        return $("input[value='" + value + "']");
    }
    @Step("Дождаться загрузки админки")
    public AdminPage shouldBeLoaded() {
        nameField().shouldBe(visible, Duration.ofSeconds(15));
        return this;
    }

    @Step("Создать товар '{name}' с ценой {price}")
    public AdminPage addProduct(String name, double price) {
        nameField().setValue(name);
        priceField().setValue(String.valueOf(price));
        createButton().click();
        return this;
    }

    @Step("Изменить имя товара с ID {id} на '{newName}'")
    public AdminPage changeName(String id, String newName) {
        nameInputById(id).setValue(newName);
        return this;
    }

    @Step("Клик 'Сохранить' для товара с ID {id}")
    public AdminPage clickUpdate(String id) {
        updateButtonById(id).click();
        return this;
    }

    @Step("Найти ID товара по имени '{name}'")
    public String findIdByName(String name) {
        String id = nameInputByValue(name).getAttribute("id");
        return id != null ? id.replace("nm-", "") : null;
    }

    public AdminPageAssert check() {
        return new AdminPageAssert(this);
    }
}