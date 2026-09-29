package com.example.ui.asserts;

import com.example.ui.pages.AdminPage;
import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;

public class AdminPageAssert extends AbstractAssert<AdminPageAssert, AdminPage> {

    public AdminPageAssert(AdminPage actual) {
        super(actual, AdminPageAssert.class);
    }

    @Step("Проверить, что админка загружена")
    public AdminPageAssert isLoaded() {
        isNotNull();
        actual.nameField().shouldBe(visible);
        return this;
    }

    @Step("Проверить, что показан тост '{expectedText}'")
    public AdminPageAssert hasToast(String expectedText) {
        isNotNull();
        actual.toast().shouldBe(visible);
        actual.toast().shouldHave(text(expectedText));
        return this;
    }

    @Step("Проверить, что имя товара с ID {id} = '{expected}'")
    public AdminPageAssert nameHasValue(String id, String expected) {
        isNotNull();
        actual.nameInputById(id).shouldHave(value(expected));
        return this;
    }

    public AdminPage page() {
        return actual;
    }
}