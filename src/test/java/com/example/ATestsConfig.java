package com.example;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс для чтения конфигурации из config.properties
 */
public class ATestsConfig {

    private static final Properties properties = new Properties();

    // загружаем конфиг
    static {
        try (InputStream input = ATestsConfig.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "Файл config.properties не найден в classpath! " +
                                "Проверьте, что он лежит в src/test/resources/"
                );
            }

            properties.load(input);
            System.out.println("[CONFIG] Конфигурация загружена успешно");

        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки config.properties", e);
        }
    }

    // ============================================================
    // Публичные методы для получения значений
    // ============================================================

    public static String getUiUrl() {
        return properties.getProperty("ui.url");
    }

    public static String getApiUrl() {
        return properties.getProperty("api.url");
    }

    public static int getTimeout() {
        return Integer.parseInt(properties.getProperty("timeout"));
    }

    public static boolean isLoggingEnabled() {
        return Boolean.parseBoolean(properties.getProperty("logging.enabled"));
    }

    public static String getAdminUsername() {
        return properties.getProperty("admin.username");
    }

    public static String getAdminPassword() {
        return properties.getProperty("admin.password");
    }

    public static String getTestProductName() {
        return properties.getProperty("test.product.name");
    }

    public static double getTestProductPrice() {
        return Double.parseDouble(properties.getProperty("test.product.price"));
    }

    // ============================================================
    // Метод для вывода всех параметров (кроме credentials)
    // ============================================================
    public static void printConfig() {
        System.out.println("============================================================");
        System.out.println("              КОНФИГУРАЦИЯ АВТОТЕСТОВ");
        System.out.println("============================================================");
        System.out.println("  UI URL:            " + getUiUrl());
        System.out.println("  API URL:           " + getApiUrl());
        System.out.println("  Timeout:           " + getTimeout() + " ms");
        System.out.println("  Логирование:       " + (isLoggingEnabled() ? "включено" : "выключено"));
        System.out.println("  Товар:             " + getTestProductName());
        System.out.println("  Цена:              " + getTestProductPrice() + " руб.");
        System.out.println("============================================================");
        System.out.println("  Credentials:       []");
        System.out.println("============================================================");
    }
}
