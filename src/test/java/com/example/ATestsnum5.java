package com.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * ЗАДАЧА 1: Тесты для эндпоинта /goods/list
 *
 */
@Tag("api")
@DisplayName("Задача 1: Тесты API для товаров")
public class ATestsnum5 {

    // Базовый URL тестового API
    private static final String BASE_URL = "http://localhost:8080";

    // Эндпоинты
    private static final String GOODS_LIST_ENDPOINT = "/goods/list";
    private static final String GOODS_ADD_ENDPOINT = "/goods/add";

    // Basic Auth
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "secret123";

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    // ============================================================
    // 1.1: GET /goods/list с given()/when()/then()
    // ============================================================
    @Test
    @DisplayName("1.1 GET /goods/list - проверка кода 200 ")
    void testGoodsListWithGivenWhenThen() {
        given()
                .auth().basic(USERNAME, PASSWORD)      // ← Basic Auth
                .log().uri()
                .queryParam("page", 0)                  // ← пагинация
                .queryParam("size", 10)
                .when()
                .get(GOODS_LIST_ENDPOINT)
                .then()
                .log().body()
                .statusCode(200)
                .body("goods", notNullValue())
                .body("goods", empty());
    }

    // ============================================================
    // 1.2: GET /goods/list с RequestSpecification
    // ============================================================
    @Test
    @DisplayName("1.2 GET /goods/list - проверка кода 200 (RequestSpecification)")
    void testGoodsListWithRequestSpecification() {
        // Создаём спецификацию запроса
        RequestSpecification spec = given()
                .baseUri(BASE_URL)
                .basePath(GOODS_LIST_ENDPOINT)
                .auth().basic(USERNAME, PASSWORD)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .log().uri();

        // Выполняем запрос
        Response response = spec
                .when()
                .get()
                .then()
                .log().body()
                .extract()
                .response();

        // Проверяем код ответа
        assertThat(response.statusCode())
                .as("Проверка кода ответа GET /goods/list")
                .isEqualTo(200);

        // Проверяем, что в ответе есть поле goods
        assertThat(response.jsonPath().getList("goods"))
                .as("Проверка наличия поля goods")
                .isNotNull();

        assertThat(response.jsonPath().getList("goods")).isEmpty();
    }

    // ============================================================
    // 1.3: POST /goods/add → GET /goods/list (REST Assured проверки)
    // ============================================================
    @Test
    @DisplayName("1.3 POST /goods/add + GET /goods/list - создание и проверка товара (REST Assured)")
    void testCreateAndGetGoodsWithRestAssured() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String productName = "LadyNotBug"+ uniqueId;
        double productPrice = 99.99;

        // Шаг 1: Создаём товар через POST /goods/add
        given()
                .auth().basic(USERNAME, PASSWORD)
                .log().uri()
                .contentType("application/json")
                .body(Map.of(
                        "name", productName,
                        "price", productPrice
                ))
                .when()
                .post(GOODS_ADD_ENDPOINT)
                .then()
                .log().body()
                .statusCode(200);

        // Шаг 2: Получаем список товаров через GET /goods/list
        given()
                .auth().basic(USERNAME, PASSWORD)
                .log().uri()
                .queryParam("page", 0)
                .queryParam("size", 100)
                .when()
                .get(GOODS_LIST_ENDPOINT)
                .then()
                .log().body()
                .statusCode(200)
                // Проверяем, что наш товар есть в списке
                .body("goods.find { it.name == '" + productName + "' }", notNullValue())
                .body("goods.find { it.name == '" + productName + "' }.price", equalTo((float) productPrice));
    }

    // ============================================================
    // 1.4: POST /goods/add → GET /goods/list (AssertJ проверки)
    // ============================================================
    @Test
    @DisplayName("1.4 POST /goods/add + GET /goods/list - создание и проверка товара (AssertJ)")
    void testCreateAndGetGoodsWithAssertJ() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String productName = "ladybug_" + uniqueId;
        double productPrice = 149.99;

        // Создаём товар
        given()
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
                .statusCode(200);

        // Получаем список товаров
        Response listResponse = given()
                .auth().basic(USERNAME, PASSWORD)
                .log().uri()
                .queryParam("page", 0)
                .queryParam("size", 100)
                .when()
                .get("/goods/list")
                .then()
                .log().body()
                .statusCode(200)
                .extract()
                .response();

        List<Map<String, Object>> goods = listResponse.jsonPath().getList("goods");

        assertThat(goods)
                .as("Проверка, что список товаров не пустой")
                .isNotNull()
                .isNotEmpty();

        //  Находим наш товар по имени
        Map<String, Object> createdGood = goods.stream()
                .filter(item -> productName.equals(item.get("name")))
                .findFirst()
                .orElse(null);

        assertThat(createdGood)
                .as("Проверка, что созданный товар найден в списке")
                .isNotNull();

        assertThat(createdGood.get("name"))
                .as("Проверка имени товара")
                .isEqualTo(productName);

        assertThat(Double.valueOf(createdGood.get("price").toString()))
                .as("Проверка цены товара")
                .isEqualTo(productPrice);
    }
}