package com.example.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class GoodsApiAssert {

    @Step("API: Проверить, что код ответа {expectedCode}")
    public GoodsApiAssert statusCodeIs(Response response, int expectedCode) {
        assertThat(response.statusCode())
                .as("Код ответа")
                .isEqualTo(expectedCode);
        return this;
    }

    @Step("API: Проверить, что сообщение '{expectedMessage}'")
    public GoodsApiAssert messageIs(Response response, String expectedMessage) {
        assertThat(response.jsonPath().getString("message"))
                .as("Сообщение")
                .isEqualTo(expectedMessage);
        return this;
    }

    @Step("API: Проверить, что список товаров не пустой")
    public GoodsApiAssert listIsNotEmpty(Response response) {
        assertThat(response.jsonPath().getList("goods"))
                .as("Список товаров")
                .isNotEmpty();
        return this;
    }

    @Step("API: Проверить, что ID товара не пустой")
    public GoodsApiAssert idIsNotNull(Response response) {
        assertThat(response.jsonPath().getInt("data.id"))
                .as("ID товара")
                .isGreaterThan(0);
        return this;
    }
}
