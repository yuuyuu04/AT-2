package com.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ЗАДАЧА 1: 4 автотеста с информативными ассертами
 * Задача 1: написать 4 автотеста, использующих в работе ассерты. Методы, которые будут проверяться автотестами, можно написать самостоятельно или взять из предыдущих заданий.Требования к методам — хотя бы один метод:
 *
 * возвращает булево значение;
 * должен возвращать список;
 * должен вернуть неверное значение (ассерт должен падать).
 * При падении ассерты должны дать информацию, что ожидалось и что было получено в результате падения.
 *
 * Итого:
 * 1. 4 тестовых метода
 * 2. Хотя бы один метод возвращает boolean
 * 3. Хотя бы один метод возвращает список
 * 4. Хотя бы один метод должен упасть (с информативным сообщением)
 */

@DisplayName("Задача 1: Тесты с информативными ассертами")
public class ATestsnum3 {

    @BeforeEach
    void setUp() {
        System.out.println("================================");
        System.out.println("Test method start");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test method end");
        System.out.println("================================");
    }
    // ============================================================
    // 1. ТЕСТ: isEven (возвращает boolean)
    // ============================================================
    @Test
    @DisplayName("1. isEven - проверка на чётность (boolean)")
    void testIsEvenWithAssert() {
        System.out.println("Тест: isEven - проверка на чётность");

        int number = 4;
        boolean result = Tests.isEven(number);
        boolean expected = true;

        System.out.println("Число: " + number);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);

        // Информативный ассерт с сообщением
        assertEquals(expected, result,
                "isEven(" + number + ") Ожидаемый результат " + expected +
                        ", Фактический результат " + result);

        System.out.println(" Тест пройден");
    }

    // ============================================================
    // 2. ТЕСТ: removeSpecificName (возвращает список)
    // ============================================================
    @Test
    @DisplayName("2. removeSpecificName - удаление из списка (List)")
    void testRemoveSpecificNameWithAssert() {
        System.out.println("Тест: removeSpecificName - удаление из списка");

        List<String> input = Arrays.asList("Alice", "Bob", "Charlie", "Bob");
        List<String> expected = Arrays.asList("Alice", "Charlie");
        List<String> result = Tests.removeSpecificName(input, "Bob");

        System.out.println("Входной список: " + input);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);

        //  Информативный ассерт с сообщением
        assertEquals(expected, result,
                "После удаления 'Bob' из " + input +
                        " Ожидаемый результат " + expected +
                        ", Фактический результат " + result);

        System.out.println(" Тест пройден");

    }

    // ============================================================
    // 3. ТЕСТ: getEvenInRange (проверка строки)
    // ============================================================
    @Test
    @DisplayName("3. getEvenInRange - чётные числа в диапазоне")
    void testGetEvenInRangeWithAssert() {
        System.out.println("Тест: getEvenInRange - чётные числа в диапазоне");

        int start = 2;
        int end = 10;
        String result = Tests.getEvenInRange(start, end);
        String expected = "2 4 6 8 10";

        System.out.println("Диапазон: " + start + " - " + end);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);

        //  Информативный ассерт с сообщением
        assertEquals(expected, result,
                "getEvenInRange(" + start + ", " + end + ") Ожидаемый результат '" + expected +
                        "', Фактический результат '" + result + "'");

        System.out.println(" Тест пройден");
    }

    // ============================================================
    // 4. ТЕСТ: ПАДАЮЩИЙ ТЕСТ (должен вернуть неверное значение)
    // ============================================================
    @Test
    @DisplayName("4. ПАДАЮЩИЙ ТЕСТ -  для показа информативного ассерта")
    void testFailingAssert() {
        System.out.println("Тест: ПАДАЮЩИЙ ТЕСТ");

        // Берём метод, который вернёт неверное значение
        int number = 7;
        boolean result = Tests.isEven(number);
        boolean expected = true; // Специально ждём true, но 7 - нечётное!

        System.out.println("Число: " + number);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);
        System.out.println("Этот тест упадёт для показа ассерта");

        //  ИНФОРМАТИВНЫЙ ПАДАЮЩИЙ АССЕРТ
        // Сообщение будет содержать ожидаемое и фактическое значение
        assertEquals(expected, result,
                "Ошибка теста " +
                        "ERROR: isEven(" + number + ") Ожидаемый результат " + expected +
                        ", Фактический результат " + result +
                        ". (Число " + number + " - нечётное.)");

        // Эта строка не выполнится, потому что тест упадёт
        System.out.println(" Тест пройден (не должно случиться)");
    }
}