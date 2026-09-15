package com.example;


import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ЗАДАЧА 1: Selenium-тесты
 */
@DisplayName("Задача 1: UI-тесты на Selenium")
public class ATestsnum7 {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String BASE_URL = "http://localhost:8080";
    private static final String LOGIN_URL = BASE_URL + "/login";
    private static final String ADMIN_URL = BASE_URL + "/admin";

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "secret123";

    // ============================================================
    // 1.5: @BeforeEach / @AfterEach — запуск и закрытие браузера
    // ============================================================
    @BeforeEach
    void setUp() {


        // Открываем браузер
        driver = new ChromeDriver();

        // Неявное ожидание (для всех элементов)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Явное ожидание (для конкретных условий)
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Разворачиваем на весь экран
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ============================================================
    // 1.1: Добавить товар через админку → проверить на витрине
    // ============================================================
    @Test
    @DisplayName("1.1 Добавить товар через админку и проверить на витрине")
    void testAddProductAndCheckOnShop() {
        // Генерируем уникальное имя товара
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String productName = "TestProduct_" + uniqueId;
        String productPrice = "150";

        // 1. Логинимся в админку
        loginToAdmin();

        // 2. Добавляем товар
        driver.findElement(By.id("n-name")).sendKeys(productName);
        driver.findElement(By.id("n-price")).sendKeys(productPrice);
        driver.findElement(By.id("add-btn")).click();

        // 3. Проверяем, что уведомление появилось
        WebElement toast = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".toast"))
        );
        assertThat(toast.getText())
                .as("Проверка уведомления о добавлении товара")
                .contains("Товар успешно добавлен");

        // 4. Переходим на витрину
        driver.get(BASE_URL);

        // 5. Проверяем, что товар есть на витрине
        List<WebElement> productTitles = driver.findElements(By.cssSelector(".product-card h4"));
        boolean productFound = productTitles.stream()
                .anyMatch(title -> title.getText().equals(productName));

        assertThat(productFound)
                .as("Товар '" + productName + "' должен быть на витрине")
                .isTrue();
    }

    // ============================================================
    // 1.2: Добавить товар в корзину → проверить
    // ============================================================
    @Test
    @DisplayName("1.2 Добавить товар в корзину и проверить")
    void testAddProductToCart() {
        // 1. Открываем витрину
        driver.get(BASE_URL);

        // 2. Берём имя первого товара на странице
        WebElement firstProduct = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-card h4"))
        );
        String productName = firstProduct.getText();

        // 3. Добавляем его в корзину
        WebElement addButton = driver.findElement(
                By.cssSelector(".product-card button[data-action='add-to-cart']")
        );
        addButton.click();

        // 4. Открываем корзину
        WebElement cartButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("open-cart-btn"))
        );
        cartButton.click();

        // 5. Проверяем, что товар есть в корзине
        WebElement cartItems = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("cart-items"))
        );
        String cartText = cartItems.getText();

        assertThat(cartText)
                .as("Товар '" + productName + "' должен быть в корзине")
                .contains(productName);
    }

    // ============================================================
    // 1.3: Попытка входа в админку с неверным логином/паролем
    // ============================================================
    @Test
    @DisplayName("1.3 Попытка входа в админку с неверным логином и паролем")
    void testLoginWithWrongCredentials() {
        // 1. Открываем страницу логина
        driver.get(LOGIN_URL);

        // 2. Вводим неверные данные
        driver.findElement(By.id("username")).sendKeys("wrong_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password");

        // 3. Нажимаем "Sign in"
        driver.findElement(By.cssSelector("button.primary")).click();

        // 4. Проверяем, что остались на странице логина
        // (или появилось сообщение об ошибке)
        String currentUrl = driver.getCurrentUrl();

        // Если URL всё ещё содержит "/login" — вход не выполнен
        boolean stayedOnLogin = currentUrl.contains("/login");
        // Или проверяем наличие сообщения об ошибке
        boolean hasErrorMessage = !driver.findElements(
                By.cssSelector(".alert-danger, .error, .alert")
        ).isEmpty();

        assertThat(stayedOnLogin || hasErrorMessage)
                .as("Вход с неверными данными не должен выполняться")
                .isTrue();
    }

    // ============================================================
// 1.4: Проверить сохранение товаров в корзине после обновления
// ============================================================
    @Test
    @DisplayName("1.4 Проверить сохранение товаров в корзине после обновления")
    void testCartSavedAfterRefresh() {
        // 1. Открываем витрину
        driver.get(BASE_URL);

        // 2. Берём имя первого товара
        WebElement firstProduct = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-card h4"))
        );
        String productName = firstProduct.getText();

        // 3. Добавляем в корзину
        WebElement addButton = driver.findElement(
                By.cssSelector(".product-card button[data-action='add-to-cart']")
        );
        addButton.click();

        // 4. Открываем корзину
        WebElement cartButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("open-cart-btn"))
        );
        cartButton.click();

        // 5. Ждём появления товара в корзине
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart-items")));

        // 6. Обновляем страницу
        driver.navigate().refresh();

        // 7. ✅ ДОБАВЛЕНО: ждём, пока кнопка корзины снова станет доступной
        WebElement cartButtonAfterRefresh = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("open-cart-btn"))
        );
        cartButtonAfterRefresh.click();

        // 8. ✅ ДОБАВЛЕНО: ждём, пока корзина снова откроется
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart-items")));

        // 9. Проверяем, что товар всё ещё в корзине
        WebElement cartItems = driver.findElement(By.id("cart-items"));
        String cartText = cartItems.getText();

        assertThat(cartText)
                .as("Товар '" + productName + "' должен сохраниться после обновления")
                .contains(productName);
    }

    // ============================================================
    // ВСПОМОГАТЕЛЬНЫЙ МЕТОД: логин в админку
    // ============================================================
    private void loginToAdmin() {
        driver.get(LOGIN_URL);
        driver.findElement(By.id("username")).sendKeys(ADMIN_USERNAME);
        driver.findElement(By.id("password")).sendKeys(ADMIN_PASSWORD);
        driver.findElement(By.cssSelector("button.primary")).click();

        // Ждём загрузки админки
        wait.until(ExpectedConditions.urlContains("/admin"));
    }
}