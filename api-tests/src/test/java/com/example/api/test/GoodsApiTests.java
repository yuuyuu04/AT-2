package com.example.api.test;

import com.example.api.asserts.GoodsApiAssert;
import com.example.api.client.GoodsApi;
import com.example.common.utils.RandomUtils;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.util.Map;

@Epic("API Tests")
@Feature("Goods API")
@DisplayName("API тесты для /goods")
public class GoodsApiTests extends BaseApiTest {

    private GoodsApi goodsApi;

    @BeforeEach
    void setUp() {
        goodsApi = new GoodsApi();
    }

    // ============================================================
    // GET /goods/list
    // ============================================================
    @Test
    @Tag("smoke")
    @Story("Список товаров")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("GET /goods/list — 200")
    void testGetGoodsList() {
        var response = goodsApi.getGoodsList(0, 10);

        new GoodsApiAssert(response)
                .statusCodeIs(200)
                .bodyIsNotEmpty();
    }

    // ============================================================
    // POST /goods/add — 200
    // ============================================================
    @Test
    @Tag("smoke")
    @Story("Создание товара")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("POST /goods/add — 200")
    void testAddProductSuccess() {
        String name = RandomUtils.randomName("API");
        double price = RandomUtils.randomPrice(10, 100);

        var response = goodsApi.addProduct(name, price);

        GoodsApiAssert assertObj = new GoodsApiAssert(response);
        assertObj.statusCodeIs(200);
        assertObj.fieldIs("message", "success");
        assertObj.idIsPositive();
    }

    // ============================================================
    // POST /goods/add — 400 (без имени)
    // ============================================================
    @Test
    @Story("Создание товара")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("POST /goods/add — 400 (без имени)")
    void testAddProductWithoutName() {
        var response = goodsApi.addProduct(null, 100.0);

        new GoodsApiAssert(response).statusCodeIs(400);
    }

    // ============================================================
    // DELETE /goods/{id} — 200
    // ============================================================
    @Test
    @Story("Удаление товара")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("DELETE /goods/{id} — 200")
    void testDeleteProduct() {
        String name = RandomUtils.randomName("Del");

        var createResp = goodsApi.addProduct(name, 50.0);
        int id = createResp.jsonPath().getInt("data.id");

        var deleteResp = goodsApi.deleteProduct(id);

        new GoodsApiAssert(deleteResp).statusCodeIs(200);
    }

    // ============================================================
    // PATCH /goods/{id} — 200
    // ============================================================
    @Test
    @Story("Обновление товара")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("PATCH /goods/{id} — 200")
    void testUpdateProduct() {
        String name = RandomUtils.randomName("Upd");

        var createResp = goodsApi.addProduct(name, 50.0);
        int id = createResp.jsonPath().getInt("data.id");

        var updateResp = goodsApi.updateProduct(id, Map.of("price", 99.0));

        new GoodsApiAssert(updateResp).statusCodeIs(200);
    }
}
