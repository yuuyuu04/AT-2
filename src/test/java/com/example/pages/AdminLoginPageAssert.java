package com.example.pages;

import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;

class AdminLoginPageAssert extends AbstractAssert<AdminLoginPageAssert, AdminLoginPage> {

    protected AdminLoginPageAssert(AdminLoginPage actual) {
        super(actual, AdminLoginPageAssert.class);
    }

    public AdminLoginPageAssert isLoaded() {
        isNotNull();
        actual.usernameField().shouldBe(visible);
        actual.passwordField().shouldBe(visible);
        actual.loginButton().shouldBe(visible);
        return this;
    }

    public AdminLoginPageAssert usernameHasValue(String expected) {
        isNotNull();
        actual.usernameField().shouldHave(value(expected));
        return this;
    }

    public AdminLoginPageAssert passwordHasValue(String expected) {
        isNotNull();
        actual.passwordField().shouldHave(value(expected));
        return this;
    }

    public AdminLoginPage page() {
        return actual;
    }
}