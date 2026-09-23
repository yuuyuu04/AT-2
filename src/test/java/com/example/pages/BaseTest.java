package com.example.pages;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.example.ATestsConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    protected MainPage mainPage;
    protected AdminLoginPage loginPage;
    protected AdminPage adminPage;

    @BeforeAll
    static void setUpAll() {
        ATestsConfig.printConfig();

        Configuration.baseUrl = ATestsConfig.getUiUrl();
        Configuration.timeout = ATestsConfig.getTimeout();
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";

        RestAssured.baseURI = ATestsConfig.getApiUrl();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    void setUp() {
        mainPage = new MainPage();
        loginPage = new AdminLoginPage();
        adminPage = new AdminPage();
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }
}