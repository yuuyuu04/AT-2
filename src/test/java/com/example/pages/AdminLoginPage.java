package com.example.pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * PageObject страницы логина в админку.
 * 3 элемента: логин, пароль, кнопка.
 */
class AdminLoginPage {

    public SelenideElement usernameField() {
        return $("#username");
    }

    public SelenideElement passwordField() {
        return $("#password");
    }

    public SelenideElement loginButton() {
        return $("button.primary");
    }
    // ============================================================
    // МЕТОДЫ (Builder)
    // ============================================================

    public AdminLoginPage openPage(String url) {
        open(url + "/login");
        usernameField().shouldBe(visible, Duration.ofSeconds(15));
        return this;
    }

    public AdminLoginPage setUsername(String username) {
        usernameField().setValue(username);
        return this;
    }

    public AdminLoginPage setPassword(String password) {
        passwordField().setValue(password);
        return this;
    }

    public AdminLoginPage clickLoginButton() {
        loginButton().click();
        return this;
    }

    public AdminLoginPage login(String username, String password) {
        setUsername(username);
        setPassword(password);
        clickLoginButton();
        return this;
    }

    // ✅ Метод check()
    public AdminLoginPageAssert check() {
        return new AdminLoginPageAssert(this);
    }
}