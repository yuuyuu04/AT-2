package com.example.ui.steps;

import com.example.common.config.ConfigReader;
import com.example.ui.pages.AdminLoginPage;
import io.qameta.allure.Step;

public class AuthSteps {

    private final AdminLoginPage loginPage;

    public AuthSteps(AdminLoginPage loginPage) {
        this.loginPage = loginPage;
    }

    @Step("Авторизоваться в админке под '{ConfigReader.getAdminUsername()}'")
    public void loginAsAdmin(String url) {
        loginPage.openPage(url)
                .login(ConfigReader.getAdminUsername(), ConfigReader.getAdminPassword());
    }
}