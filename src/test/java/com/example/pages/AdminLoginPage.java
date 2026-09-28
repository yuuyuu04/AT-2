package com.example.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

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

    @Step("Открыть страницу логина '{url}/login'")
    public AdminLoginPage openPage(String url) {
        open(url + "/login");
        usernameField().shouldBe(visible, Duration.ofSeconds(15));
        return this;
    }

    @Step("Ввести логин '{username}'")
    public AdminLoginPage setUsername(String username) {
        usernameField().setValue(username);
        return this;
    }

    @Step("Ввести пароль")
    public AdminLoginPage setPassword(String password) {
        passwordField().setValue(password);
        return this;
    }

    @Step("Клик по кнопке 'Войти'")
    public AdminLoginPage clickLoginButton() {
        loginButton().click();
        return this;
    }

    @Step("Полный вход: логин + пароль + кнопка")
    public AdminLoginPage login(String username, String password) {
        setUsername(username);
        setPassword(password);
        clickLoginButton();
        return this;
    }

    public AdminLoginPageAssert check() {
        return new AdminLoginPageAssert(this);
    }
}
