package com.example.api.test;

import com.example.common.config.ConfigReader;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

/**
 * Базовый класс для всех API-тестов.
 */
public class BaseApiTest {

    @BeforeAll
    static void setUpAll() {
        ConfigReader.printConfig();
        RestAssured.baseURI = ConfigReader.getApiUrl();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
