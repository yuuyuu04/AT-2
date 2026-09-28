package com.example.api;

import com.example.ATestsConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class GoodsApi {

    @Step("API: Создать товар '{name}' с ценой {price}")
    public Response addProduct(String name, double price) {
        return given()
                .filter(new AllureRestAssured())   // ✅ Allure фильтр
                .auth().basic(ATestsConfig.getAdminUsername(), ATestsConfig.getAdminPassword())
                .contentType("application/json")
                .body(Map.of("name", name, "price", price))
                .when()
                .post("/goods/add")
                .then()
                .extract()
                .response();
    }

    @Step("API: Получить список товаров")
    public Response getGoodsList() {
        return given()
                .filter(new AllureRestAssured())
                .auth().basic(ATestsConfig.getAdminUsername(), ATestsConfig.getAdminPassword())
                .when()
                .get("/goods/list")
                .then()
                .extract()
                .response();
    }

    @Step("API: Удалить товар с ID {id}")
    public Response deleteProduct(String id) {
        return given()
                .filter(new AllureRestAssured())
                .auth().basic(ATestsConfig.getAdminUsername(), ATestsConfig.getAdminPassword())
                .pathParam("id", id)
                .when()
                .delete("/goods/{id}")
                .then()
                .extract()
                .response();
    }
}
