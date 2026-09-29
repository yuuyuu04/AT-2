package com.example.api.client;

import com.example.common.api.BasicApi;
import com.example.common.constants.ApiPaths;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

/**
 * API-клиент для работы с эндпоинтом /goods.
 */
public class GoodsApi extends BasicApi {

    @Step("API: Получить список товаров (page={page}, size={size})")
    public Response getGoodsList(int page, int size) {
        return get(ApiPaths.GOODS_LIST, Map.of("page", page, "size", size));
    }

    @Step("API: Создать товар '{name}' с ценой {price}")
    public Response addProduct(String name, double price) {
        return post(ApiPaths.GOODS_ADD, Map.of("name", name, "price", price));
    }

    @Step("API: Получить товар с ID {id}")
    public Response getGoodsById(int id) {
        return get("/goods/" + id);
    }

    @Step("API: Обновить товар с ID {id}")
    public Response updateProduct(int id, Map<String, Object> body) {
        return patch("/goods/" + id, body);
    }

    @Step("API: Удалить товар с ID {id}")
    public Response deleteProduct(int id) {
        return delete("/goods/" + id);
    }
}
