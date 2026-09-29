package com.example.ui.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.example.common.config.ConfigReader;
import com.example.ui.pages.AdminLoginPage;
import com.example.ui.pages.AdminPage;
import com.example.ui.pages.MainPage;
import com.example.ui.steps.AuthSteps;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BaseUiTest {

    protected MainPage mainPage;
    protected AdminLoginPage loginPage;
    protected AdminPage adminPage;
    protected AuthSteps authSteps;

    @BeforeAll
    static void setUpAll() {
        ConfigReader.printConfig();

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide().screenshots(true).savePageSource(true));

        Configuration.baseUrl = ConfigReader.getUiUrl();
        Configuration.timeout = ConfigReader.getTimeout();
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void setUp() {
        mainPage = new MainPage();
        loginPage = new AdminLoginPage();
        adminPage = new AdminPage();
        authSteps = new AuthSteps(loginPage);
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }
}