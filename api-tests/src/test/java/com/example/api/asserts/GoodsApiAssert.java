package com.example.api.asserts;

import com.example.common.api.ApiAssert;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static jdk.dynalink.linker.support.Guards.isNotNull;

/**
 * Проверки для API-клиента GoodsApi.
 */
public class GoodsApiAssert extends ApiAssert {

    public GoodsApiAssert(Response actual) {
        super(actual);
    }

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
