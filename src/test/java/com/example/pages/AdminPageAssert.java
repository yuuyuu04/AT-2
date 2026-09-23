package com.example.pages;

import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;

class AdminPageAssert extends AbstractAssert<AdminPageAssert, AdminPage> {

    protected AdminPageAssert(AdminPage actual) {
        super(actual, AdminPageAssert.class);
    }

    public AdminPageAssert isLoaded() {
        isNotNull();
        actual.nameField().shouldBe(visible);
        return this;
    }

    public AdminPageAssert hasToast(String expectedText) {
        isNotNull();
        actual.toast().shouldBe(visible);
        actual.toast().shouldHave(text(expectedText));
        return this;
    }

    public AdminPageAssert nameHasValue(String id, String expected) {
        isNotNull();
        actual.nameInputById(id).shouldHave(value(expected));
        return this;
    }

    public AdminPage page() {
        return actual;
    }
}