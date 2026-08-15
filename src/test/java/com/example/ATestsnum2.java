package com.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ATestsnum2 {

    // Счетчики для статистики
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    // ============================================================
    // выводы сделать через @BeforeEach | @AfterEach
    // ============================================================

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
    // ТЕСТ 1: isEven
    // ============================================================
    @Test
    @DisplayName("1. isEven - проверка на чётность")
    void testIsEven() {
        int number = 4;
        boolean result = Tests.isEven(number);
        boolean expected = true;

        System.out.println("Число: " + number);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаем: " + expected);

        if (result == expected) {
            System.out.println("TEST PASSED");
            testsPassed++;
        } else {
            System.out.println("TEST FAILED");
            testsFailed++;
        }

        assertEquals(expected, result);
    }

    // ============================================================
    // ТЕСТ 2: checkAccess
    // ============================================================
    @Test
    @DisplayName("2. checkAccess - проверка доступа")
    void testCheckAccess() {
        int age = 19;
        String result = Tests.checkAccess(age);
        String expected = "Allowed";

        System.out.println("Возраст: " + age);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаем: " + expected);

        if (result.equals(expected)) {
            System.out.println("TEST PASSED");
            testsPassed++;
        } else {
            System.out.println("TEST FAILED");
            testsFailed++;
        }

        assertEquals(expected, result);
    }

    // ============================================================
    // ТЕСТ 3: isPositive
    // ============================================================
    @Test
    @DisplayName("3. isPositive - проверка положительного числа")
    void testIsPositive() {
        int number = 5;
        boolean result = Tests.isPositive(number);
        boolean expected = true;

        System.out.println("Число: " + number);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаем: " + expected);

        if (result == expected) {
            System.out.println("TEST PASSED");
            testsPassed++;
        } else {
            System.out.println("TEST FAILED");
            testsFailed++;
        }

        assertTrue(result);
    }

    // ============================================================
    // ТЕСТ 4: getGrade
    // ============================================================
    @Test
    @DisplayName("4. getGrade - определение оценки")
    void testGetGrade() {
        int score = 85;
        String result = Tests.getGrade(score);
        String expected = "A";

        System.out.println("Оценка: " + score);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаем: " + expected);

        if (result.equals(expected)) {
            System.out.println("TEST PASSED");
            testsPassed++;
        } else {
            System.out.println("TEST FAILED");
            testsFailed++;
        }

        assertEquals(expected, result);
    }

    // ============================================================
    // ТЕСТ 5: blastOff
    // ============================================================
    @RepeatedTest(2)
    @DisplayName("5. blastOff - обратный отсчёт")
    void testBlastOff() {
        int start = 5;
        String result = Tests.blastOff(start);
        String expected = "5 4 3 2 1 Поехали!";

        System.out.println("Старт: " + start);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаем: " + expected);

        if (result.equals(expected)) {
            System.out.println("TEST PASSED");
            testsPassed++;
        } else {
            System.out.println("TEST FAILED");
            testsFailed++;
        }

        assertEquals(expected, result);
    }

    // ============================================================
    // ТЕСТ 6: sumToN
    // ============================================================
    @RepeatedTest(3)
    @DisplayName("6. sumToN - сумма чисел")
    void testSumToN() {
        int n = 5;
        int result = Tests.sumToN(n);
        int expected = 15;

        System.out.println("n = " + n);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаем: " + expected);

        if (result == expected) {
            System.out.println("TEST PASSED");
            testsPassed++;
        } else {
            System.out.println("TEST FAILED");
            testsFailed++;
        }

        assertEquals(expected, result);
    }

    // ============================================================
    // ТЕСТ 7: hasBug
    // ============================================================
    @RepeatedTest(4)
    @DisplayName("7. hasBug - поиск Bug в массиве")
    void testHasBug() {
        String[] messages = {"Hello", "Bug", "World"};
        boolean result = Tests.hasBug(messages);
        boolean expected = true;

        System.out.println("Массив: " + Arrays.toString(messages));
        System.out.println("Результат: " + result);
        System.out.println("Ожидаем: " + expected);

        if (result == expected) {
            System.out.println("TEST PASSED");
            testsPassed++;
        } else {
            System.out.println("TEST FAILED");
            testsFailed++;
        }

        assertTrue(result);
    }

    // ============================================================
    // ТЕСТ 8: getEvenInRange
    // ============================================================
    @Test
    @DisplayName("8. getEvenInRange - чётные числа в диапазоне")
    void testGetEvenInRange() {
        int start = 2;
        int end = 10;
        String result = Tests.getEvenInRange(start, end);
        String expected = "2 4 6 8 10";

        System.out.println("Диапазон: " + start + " - " + end);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаем: " + expected);

        if (result.equals(expected)) {
            System.out.println("TEST PASSED");
            testsPassed++;
        } else {
            System.out.println("TEST FAILED");
            testsFailed++;
        }

        assertEquals(expected, result);
    }

    // ============================================================
    // ТЕСТ 9: findMax
    // ============================================================
    @Test
    @DisplayName("9. findMax - максимум в массиве")
    void testFindMax() {
        int[] arr = {3, 7, 2, 9, 5};
        int result = Tests.findMax(arr);
        int expected = 9;

        System.out.println("Массив: " + Arrays.toString(arr));
        System.out.println("Результат: " + result);
        System.out.println("Ожидаем: " + expected);

        if (result == expected) {
            System.out.println("TEST PASSED");
            testsPassed++;
        } else {
            System.out.println("TEST FAILED");
            testsFailed++;
        }

        assertEquals(expected, result);
    }
    // ============================================================
    // ТЕСТ 10: reverse
    // ============================================================
    @Test
    @DisplayName("10. reverse - обратный порядок массива")
    void testReverse() {
        String[] input = {"One", "Two", "Three", "Zero"};
        String[] expected = {"Zero", "Three", "Two", "One"};
        String[] result = Tests.reverse(input);

        System.out.println("Входной массив: " + Arrays.toString(input));
        System.out.println("Результат: " + Arrays.toString(result));
        System.out.println("Ожидаем: " + Arrays.toString(expected));

        boolean passed = Arrays.equals(result, expected);
        if (passed) {
            System.out.println("TEST PASSED");
            testsPassed++;
        } else {
            System.out.println("TEST FAILED");
            testsFailed++;
        }

        assertArrayEquals(expected, result);
    }

    // ============================================================
    // ТЕСТ 11: calcAverage
    // ============================================================
    @Test
    @DisplayName("11. calcAverage - среднее арифметическое")
    void testCalcAverage() {
        List<Integer> list = Arrays.asList(2, 4, 6, 8);
        double result = Tests.calcAverage(list);
        double expected = 5.0;

        System.out.println("Список: " + list);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаем: " + expected);

        if (Math.abs(result - expected) < 0.0001) {
            System.out.println("TEST PASSED");
            testsPassed++;
        } else {
            System.out.println("TEST FAILED");
            testsFailed++;
        }

        assertEquals(expected, result, 0.0001);
    }

    // ============================================================
    // ТЕСТ 12: removeSpecificName
    // ============================================================
    @Test
    @DisplayName("12. removeSpecificName - удаление имени из списка")
    void testRemoveSpecificName() {
        List<String> list = Arrays.asList("Alice", "Bob", "Charlie", "Bob");
        List<String> expected = Arrays.asList("Alice", "Charlie");
        List<String> result = Tests.removeSpecificName(list, "Bob");

        System.out.println("Список: " + list);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаем: " + expected);

        if (result.equals(expected)) {
            System.out.println("TEST PASSED");
            testsPassed++;
        } else {
            System.out.println("TEST FAILED");
            testsFailed++;
        }

        assertEquals(expected, result);
    }

    // ============================================================
    // ТЕСТЫ с данными из файла
    // ============================================================
    @ParameterizedTest
    @CsvFileSource(resources = "/test-data.csv", numLinesToSkip = 1)
    @DisplayName("isEven из файла")
    void testIsEvenFromCsv(int number, int expectedInt) {
        System.out.println("================================");
        System.out.println("Test method start");

        boolean result = Tests.isEven(number);
        boolean expected = expectedInt == 1;

        System.out.println("Число: " + number);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаем: " + expected);

        if (result == expected) {
            System.out.println("TEST PASSED");
            testsPassed++;
        } else {
            System.out.println("TEST FAILED");
            testsFailed++;
        }

        assertEquals(expected, result);

        System.out.println("Test method end");
        System.out.println("================================");
    }

    // ============================================================
    // ИТОГИ тестов
    // ============================================================
    @AfterAll
    static void printSummary() {
        System.out.println("\n========================================");
        System.out.println("Итого");
        System.out.println("Пройдено тестов: " + testsPassed);
        System.out.println("Провалено тестов: " + testsFailed);
        System.out.println("========================================");
    }
}