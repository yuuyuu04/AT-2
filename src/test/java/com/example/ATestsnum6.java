package com.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * ЗАДАЧА 2: Покрытие всех эндпоинтов из Swagger
 * <p>
 * Эндпоинты:
 * 1. GET /goods/list - список товаров с пагинацией
 * 2. POST /goods/add - добавить товар
 * 3. GET /goods/{id} - получить товар по ID
 * 4. DELETE /goods/{id} - удалить товар
 * 5. PATCH /goods/{id} - частичное обновление товара
 */
@Tag("api")
@DisplayName("Задача 2: Полное покрытие Swagger API")
public class ATestsnum6 {

    // Базовый URL тестового API
    private static final String BASE_URL = "http://localhost:8080";
    // Basic Auth
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "secret123";


    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    // ============================================================
    // 1. GET /goods/list - список товаров с пагинацией
    // ============================================================
    @Test
    @DisplayName("GET /goods/list - с пагинацией (page=0, size=5)")
    void testGoodsListPagination() {
        Response response = given()
                .auth().basic(USERNAME, PASSWORD)
                .log().uri()
                .queryParam("page", 1)
                .queryParam("size", 5)
                .when()
                .get("/goods/list")
                .then()
                .log().body()
                .extract()
                .response();

        assertThat(response.statusCode())
                .as("Проверка кода ответа GET /goods/list с пагинацией")
                .isEqualTo(200);

        List<Map<String, Object>> goods = response.jsonPath().getList("goods");
        assertThat(goods)
                .as("Проверка, что список товаров не null")
                .isNotNull();
        //тут тест падает потому что метод всегда возвращает 500
    }

    // ============================================================
    // 2. POST /goods/add - добавить товар
    // ============================================================
    @Test
    @DisplayName("POST /goods/add - создание товара (200)")
    void testAddGoodsSuccess() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String productName = "superkot_" + uniqueId;
        double productPrice = 199.99;

        Response response = given()
                .auth().basic(USERNAME, PASSWORD)
                .log().uri()
                .contentType("application/json")
                .body(Map.of(
                        "name", productName,
                        "price", productPrice
                ))
                .when()
                .post("/goods/add")
                .then()
                .log().body()
                .extract()
                .response();

        assertThat(response.statusCode())
                .as("Проверка кода ответа POST /goods/add")
                .isEqualTo(200);

        assertThat(response.jsonPath().getString("message"))
                .as("Проверка сообщения об успехе")
                .isEqualTo("success");
    }

    @Test
    @DisplayName("POST /goods/add - без авторизации (401)")
    void testAddGoodsUnauthorized() {
        Response response = given()
                .log().uri()
                .contentType("application/json")
                .body(Map.of(
                        "name", "Товар без авторизации",
                        "price", 120.0
                ))
                .when()
                .post("/goods/add")
                .then()
                .log().body()
                .extract()
                .response();

        assertThat(response.statusCode())
                .as("Проверка кода ответа POST /goods/add без авторизации")
                .isEqualTo(401);
    }

    @Test
    @DisplayName("POST /goods/add - без имени (400)")
    void testAddGoodsMissingName() {
        Response response = given()
                .auth().basic(USERNAME, PASSWORD)
                .log().uri()
                .contentType("application/json")
                .body(Map.of(
                        "price", 19.99
                ))
                .when()
                .post("/goods/add")
                .then()
                .log().body()
                .extract()
                .response();

        assertThat(response.statusCode())
                .as("Проверка кода ответа POST /goods/add без имени")
                .isEqualTo(400);
    }

    @Test
    @DisplayName("POST /goods/add - отрицательная цена (400)")
    void testAddGoodsNegativePrice() {
        Response response = given()
                .auth().basic(USERNAME, PASSWORD)
                .log().uri()
                .contentType("application/json")
                .body(Map.of(
                        "name", "Товар с отрицательной ценой",
                        "price", -10.0
                ))
                .when()
                .post("/goods/add")
                .then()
                .log().body()
                .extract()
                .response();

        assertThat(response.statusCode())
                .as("Проверка кода ответа POST /goods/add с отрицательной ценой")
                .isEqualTo(400);
    }

    // ============================================================
    // 3. GET /goods/{id} - получить товар по ID
    // ============================================================
    @Test
    @DisplayName("GET /goods/{id} - получение существующего товара (200)")
    void testGetGoodsByIdSuccess() {
        // Создаём товар
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String productName = "miracle_" + uniqueId;
        double productPrice = 79.99;

        Response createResponse = given()
                .auth().basic(USERNAME, PASSWORD)
                .contentType("application/json")
                .body(Map.of("name", productName, "price", productPrice))
                .when()
                .post("/goods/add")
                .then()
                .extract()
                .response();

        int createdId = createResponse.jsonPath().getInt("data.id");

        // Получаем товар по ID
        Response response = given()
                .auth().basic(USERNAME, PASSWORD)
                .log().uri()
                .pathParam("id", createdId)
                .when()
                .get("/goods/{id}")
                .then()
                .log().body()
                .extract()
                .response();

        assertThat(response.statusCode())
                .as("Проверка кода ответа GET /goods/{id}")
                .isEqualTo(200);

        assertThat(response.jsonPath().getString("name"))
                .as("Проверка имени товара")
                .isEqualTo(productName);
    }

    @Test
    @DisplayName("GET /goods/{id} - несуществующий ID (404)")
    void testGetGoodsByIdNotFound() {
        int invalidId = 919;

        Response response = given()
                .auth().basic(USERNAME, PASSWORD)
                .log().uri()
                .pathParam("id", invalidId)
                .when()
                .get("/goods/{id}")
                .then()
                .log().body()
                .extract()
                .response();

        assertThat(response.statusCode())
                .as("Проверка кода ответа GET /goods/{id} с несуществующим ID")
                .isEqualTo(404);

        // тоже падает 500, хотя правильно возвращать 404
    }
// 4. DELETE /goods/{id} - удалить товар
    // ============================================================
    @Test
    @DisplayName("DELETE /goods/{id} - удаление существующего товара (200)")
    void testDeleteGoodsByIdSuccess() {
        // Создаём товар
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String productName = "tovar_" + uniqueId;
        double productPrice = 50.0;

        Response createResponse = given()
                .auth().basic(USERNAME, PASSWORD)
                .contentType("application/json")
                .body(Map.of("name", productName, "price", productPrice))
                .when()
                .post("/goods/add")
                .then()
                .extract()
                .response();

        int createdId = createResponse.jsonPath().getInt("data.id");

        // Удаляем товар
        Response response = given()
                .auth().basic(USERNAME, PASSWORD)
                .log().uri()
                .pathParam("id", createdId)
                .when()
                .delete("/goods/{id}")
                .then()
                .log().body()
                .extract()
                .response();

        assertThat(response.statusCode())
                .as("Проверка кода ответа DELETE /goods/{id}")
                .isEqualTo(200);

        // Проверяем, что товар действительно удалён
        Response getResponse = given()
                .auth().basic(USERNAME, PASSWORD)
                .pathParam("id", createdId)
                .when()
                .get("/goods/{id}")
                .then()
                .extract()
                .response();

        assertThat(getResponse.statusCode())
                .as("Проверка, что товар удалён")
                .isEqualTo(404);
    }

    @Test
    @DisplayName("DELETE /goods/{id} - несуществующий ID (404)")
    void testDeleteGoodsByIdNotFound() {
        int invalidId = 999999;

        Response response = given()
                .auth().basic(USERNAME, PASSWORD)
                .log().uri()
                .pathParam("id", invalidId)
                .when()
                .delete("/goods/{id}")
                .then()
                .log().body()
                .extract()
                .response();

        assertThat(response.statusCode())
                .as("Проверка кода ответа DELETE /goods/{id} с несуществующим ID")
                .isEqualTo(404);
    }

    @Test
    @DisplayName("DELETE /goods/{id} - без авторизации (401)")
    void testDeleteGoodsByIdUnauthorized() {
        int existingId = 1;

        Response response = given()
                .log().uri()
                .pathParam("id", existingId)
                .when()
                .delete("/goods/{id}")
                .then()
                .log().body()
                .extract()
                .response();

        assertThat(response.statusCode())
                .as("Проверка кода ответа DELETE /goods/{id} без авторизации")
                .isEqualTo(401);
    }

    // ============================================================
    // 5. PATCH /goods/{id} - частичное обновление товара
    // ============================================================
    @Test
    @DisplayName("PATCH /goods/{id} - обновление цены (200)")
    void testPatchGoodsUpdatePrice() {
        // Создаём товар
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String originalName = "cherry_" + uniqueId;
        double originalPrice = 10.0;

        Response createResponse = given()
                .auth().basic(USERNAME, PASSWORD)
                .contentType("application/json")
                .body(Map.of("name", originalName, "price", originalPrice))
                .when()
                .post("/goods/add")
                .then()
                .extract()
                .response();

        int createdId = createResponse.jsonPath().getInt("data.id");
        double newPrice = 25.0;

        // Обновляем только цену
        Response response = given()
                .auth().basic(USERNAME, PASSWORD)
                .log().uri()
                .pathParam("id", createdId)
                .contentType("application/json")
                .body(Map.of(
                        "name", originalName,
                        "price", newPrice
                ))
                .when()
                .patch("/goods/{id}")
                .then()
                .log().body()
                .extract()
                .response();

        assertThat(response.statusCode())
                .as("Проверка кода ответа PATCH /goods/{id}")
                .isEqualTo(200);

        // Проверяем, что цена обновилась
        Response getResponse = given()
                .auth().basic(USERNAME, PASSWORD)
                .pathParam("id", createdId)
                .when()
                .get("/goods/{id}")
                .then()
                .extract()
                .response();

        assertThat(getResponse.jsonPath().getDouble("price"))
                .as("Проверка обновлённой цены")
                .isEqualTo(newPrice);

        assertThat(getResponse.jsonPath().getString("name"))
                .as("Проверка, что имя не изменилось")
                .isEqualTo(originalName);
    }

    @Test
    @DisplayName("PATCH /goods/{id} - обновление имени (200)")
    void testPatchGoodsUpdateName() {
        // Создаём товар
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String originalName = "apple_" + uniqueId;
        double originalPrice = 10.0;

        Response createResponse = given()
                .auth().basic(USERNAME, PASSWORD)
                .contentType("application/json")
                .body(Map.of("name", originalName, "price", originalPrice))
                .when()
                .post("/goods/add")
                .then()
                .extract()
                .response();

        int createdId = createResponse.jsonPath().getInt("data.id");
        String newName = "orange_" + uniqueId;

        // Обновляем только имя
        Response response = given()
                .auth().basic(USERNAME, PASSWORD)
                .log().uri()
                .pathParam("id", createdId)
                .contentType("application/json")
                .body(Map.of(
                        "name", newName
                ))
                .when()
                .patch("/goods/{id}")
                .then()
                .log().body()
                .extract()
                .response();

        assertThat(response.statusCode())
                .as("Проверка кода ответа PATCH /goods/{id}")
                .isEqualTo(200);

        // Проверяем, что имя обновилось
        Response getResponse = given()
                .auth().basic(USERNAME, PASSWORD)
                .pathParam("id", createdId)
                .when()
                .get("/goods/{id}")
                .then()
                .extract()
                .response();

        assertThat(getResponse.jsonPath().getString("name"))
                .as("Проверка обновлённого имени")
                .isEqualTo(newName);
    }

    @Test
    @DisplayName("PATCH /goods/{id} - несуществующий ID (404)")
    void testPatchGoodsByIdNotFound() {
        int invalidId = 999999;

        Response response = given()
                .auth().basic(USERNAME, PASSWORD)
                .log().uri()
                .pathParam("id", invalidId)
                .contentType("application/json")
                .body(Map.of(
                        "price", 100.0
                ))
                .when()
                .patch("/goods/{id}")
                .then()
                .log().body()
                .extract()
                .response();

        assertThat(response.statusCode())
                .as("Проверка кода ответа PATCH /goods/{id} с несуществующим ID")
                .isEqualTo(400);
    }

    @Test
    @DisplayName("PATCH /goods/{id} - без авторизации (401)")
    void testPatchGoodsByIdUnauthorized() {
        int existingId = 1;

        Response response = given()
                .log().uri()
                .pathParam("id", existingId)
                .contentType("application/json")
                .body(Map.of(
                        "price", 100.0
                ))
                .when()
                .patch("/goods/{id}")
                .then()
                .log().body()
                .extract()
                .response();

        assertThat(response.statusCode())
                .as("Проверка кода ответа PATCH /goods/{id} без авторизации")
                .isEqualTo(401);
    }
}