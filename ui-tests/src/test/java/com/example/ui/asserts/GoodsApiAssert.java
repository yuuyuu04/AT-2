package com.example.ui.asserts;

import com.example.common.api.ApiAssert;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class GoodsApiAssert extends ApiAssert {

    public GoodsApiAssert(Response actual) {
        super(actual);
    }

    // ============================================================
    // ПЕРЕОПРЕДЕЛЕНИЯ — чтобы цепочка возвращала GoodsApiAssert
    // ============================================================

    @Override
    @Step("API: Проверить код ответа = {expectedCode}")
    public GoodsApiAssert statusCodeIs(int expectedCode) {
        super.statusCodeIs(expectedCode);
        return this;
    }

    @Override
    @Step("API: Проверить поле '{field}' = '{expected}'")
    public GoodsApiAssert fieldIs(String field, Object expected) {
        super.fieldIs(field, expected);
        return this;
    }

    @Override
    @Step("API: Проверить, что тело не пустое")
    public GoodsApiAssert bodyIsNotEmpty() {
        super.bodyIsNotEmpty();
        return this;
    }

    // ============================================================
    // СВОИ МЕТОДЫ
    // ============================================================

    @Step("API: Проверить, что товар '{name}' есть в списке")
    public GoodsApiAssert containsProduct(String name) {
        isNotNull();
        String body = actual.body().asString();
        if (!body.contains(name)) {
            failWithMessage("Товар '%s' не найден в списке", name);
        }
        return this;
    }

    @Step("API: Проверить, что ID товара > 0")
    public GoodsApiAssert idIsPositive() {
        isNotNull();
        int id = actual.jsonPath().getInt("data.id");
        if (id <= 0) {
            failWithMessage("ID товара должен быть > 0, но был %d", id);
        }
        return this;
    }
}