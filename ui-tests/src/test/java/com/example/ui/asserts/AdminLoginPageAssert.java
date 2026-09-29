package com.example.ui.asserts;

import com.example.ui.pages.AdminLoginPage;
import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;

public class AdminLoginPageAssert extends AbstractAssert<AdminLoginPageAssert, AdminLoginPage> {

    public AdminLoginPageAssert(AdminLoginPage actual) {
        super(actual, AdminLoginPageAssert.class);
    }

    @Step("Проверить, что все элементы логина видны")
    public AdminLoginPageAssert isLoaded() {
        isNotNull();
        actual.usernameField().shouldBe(visible);
        actual.passwordField().shouldBe(visible);
        actual.loginButton().shouldBe(visible);
        return this;
    }

    @Step("Проверить, что поле логина содержит '{expected}'")
    public AdminLoginPageAssert usernameHasValue(String expected) {
        isNotNull();
        actual.usernameField().shouldHave(value(expected));
        return this;
    }

    public AdminLoginPage page() {
        return actual;
    }
}