package com.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Random; //для генерации случайных чисел
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ATests {

    private static final Random random = new Random();

    // ============================================================
    // ЗАДАЧА 1.
    // ============================================================
    @Test
    @DisplayName("Задача 1. Проверка на четность")
    void testIsEven() {
        System.out.println("================================");
        System.out.println("Test method start");

        int number = random.nextInt(100) + 1;
        boolean result = Tests.isEven(number);
        boolean expected = (number % 2 == 0);

        System.out.println("Число: " + number);
        System.out.println("Фактический результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);

        assertEquals(expected, result);

        System.out.println("Test method end");
        System.out.println("================================");
    }

    // ============================================================
    // ЗАДАЧА 2
    // ============================================================
    @RepeatedTest(5)
    @DisplayName("Задача 2. Проверка возраста")
    void testCheckAccess(RepetitionInfo repetitionInfo) {
        System.out.println("================================");
        System.out.println("Test method start");
        System.out.println("Повтор #" + repetitionInfo.getCurrentRepetition());

        int age = random.nextInt(100);
        String result = Tests.checkAccess(age);
        String expected = (age > 18) ? "Allowed" : "Denied";

        System.out.println("Возраст: " + age);
        System.out.println("Фактический результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);

        assertEquals(expected, result);

        System.out.println("Test method end");
        System.out.println("================================");
    }

    // ============================================================
    // ЗАДАЧА 3
    // ============================================================
    @Test
    @DisplayName("Задача 3 проверка положительного числа")
    void testIsPositive() {
        System.out.println("================================");
        System.out.println("Test method start");

        assertTrue(Tests.isPositive(5));
        assertTrue(Tests.isPositive(0));
        assertFalse(Tests.isPositive(-3));

        System.out.println("Все тесты isPositive пройдены ✅");
        System.out.println("Test method end");
        System.out.println("================================");
    }

    // ============================================================
    // ЗАДАЧА 4
    // ============================================================
    @ParameterizedTest
    @MethodSource("gradeTestData")
    @DisplayName("Задача 4 Проверка баллов")
    void testGetGrade(int score, String expected) {
        System.out.println("================================");
        System.out.println("Test method start");

        String result = Tests.getGrade(score);

        System.out.println("Оценка: " + score);
        System.out.println("Фактический результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);

        assertEquals(expected, result);

        System.out.println("Test method end");
        System.out.println("================================");
    }

    static Stream<Arguments> gradeTestData() {
        return Stream.of(
                Arguments.of(5, "E"),
                Arguments.of(20, "E"),
                Arguments.of(25, "D"),
                Arguments.of(40, "D"),
                Arguments.of(50, "C"),
                Arguments.of(60, "C"),
                Arguments.of(70, "B"),
                Arguments.of(80, "B"),
                Arguments.of(90, "A"),
                Arguments.of(100, "A"),
                Arguments.of(-1, "Error"),
                Arguments.of(101, "Error")
        );
    }

    // ============================================================
    // ЗАДАЧА 5
    // ============================================================
    @Test
    @DisplayName("Задача 5 4321 поехали")
    void testBlastOff() {
        System.out.println("================================");
        System.out.println("Test method start");

        assertEquals("5 4 3 2 1 Поехали!", Tests.blastOff(5));
        assertEquals("Поехали!", Tests.blastOff(0));

        System.out.println("Все тесты blastOff пройдены ✅");
        System.out.println("Test method end");
        System.out.println("================================");
    }

    // ============================================================
    // ЗАДАЧА 6
    // ============================================================
    @Test
    @DisplayName("Задача 6 сумма чисел")
    void testSumToN() {
        System.out.println("================================");
        System.out.println("Test method start");

        assertEquals(15, Tests.sumToN(5));
        assertEquals(6, Tests.sumToN(3));
        assertEquals(0, Tests.sumToN(0));
        assertEquals(0, Tests.sumToN(-5));

        System.out.println("Все тесты sumToN пройдены ✅");
        System.out.println("Test method end");
        System.out.println("================================");
    }

    // ============================================================
    // ЗАДАЧА 7
    // ============================================================
    @Test
    @DisplayName("Задача 7 поиск Bug")
    void testHasBug() {
        System.out.println("================================");
        System.out.println("Test method start");

        assertTrue(Tests.hasBug(new String[]{"Hello", "Bug", "World"}));
        assertTrue(Tests.hasBug(new String[]{"Hello", "bug", "World"}));
        assertTrue(Tests.hasBug(new String[]{"Hello", "BUG", "World"}));
        assertFalse(Tests.hasBug(new String[]{"Hello", "World"}));
        assertFalse(Tests.hasBug(new String[]{}));
        assertFalse(Tests.hasBug(null));

        System.out.println("Все тесты hasBug пройдены ✅");
        System.out.println("Test method end");
        System.out.println("================================");
    }

    // ============================================================
    // ЗАДАЧА 8
    // ============================================================
    @Test
    @DisplayName("Задача 8 чётные числа")
    void testGetEvenInRange() {
        System.out.println("================================");
        System.out.println("Test method start");

        assertEquals("2 4", Tests.getEvenInRange(2, 5));
        assertEquals("2 4 6 8 10", Tests.getEvenInRange(1, 10));
        assertEquals("", Tests.getEvenInRange(3, 3));
        assertEquals("4", Tests.getEvenInRange(4, 4));
        assertEquals("", Tests.getEvenInRange(5, 1));

        System.out.println("Все тесты getEvenInRange пройдены ✅");
        System.out.println("Test method end");
        System.out.println("================================");
    }

    // ============================================================
    // ЗАДАЧА 9
    // ============================================================
    @Test
    @DisplayName("Задача 9 поиск максимального числа")
void testFindMax() {
        System.out.println("================================");
        System.out.println("Test method start");

        assertEquals(9, Tests.findMax(new int[]{3, 7, 2, 9, 5}));
        assertEquals(-2, Tests.findMax(new int[]{-5, -2, -10}));
        assertEquals(5, Tests.findMax(new int[]{5}));

        System.out.println("Все тесты findMax пройдены ✅");
        System.out.println("Test method end");
        System.out.println("================================");
    }

    // ============================================================
    // ЗАДАЧА 10
    // ============================================================
    @Test
    @DisplayName("Задача 10 обратный порядок")
    void testReverse() {
        System.out.println("================================");
        System.out.println("Test method start");

        String[] input = {"One", "Two", "Zero"};
        String[] expected = {"Zero", "Two", "One"};

        assertArrayEquals(expected, Tests.reverse(input));
        assertArrayEquals(new String[]{"One", "Two", "Zero"}, input);

        System.out.println("Все тесты reverse пройдены ✅");
        System.out.println("Test method end");
        System.out.println("================================");
    }

    // ============================================================
    // ЗАДАЧА 11
    // ============================================================
    @Test
    @DisplayName("Задача 11 среднее арифметическое")
    void testCalcAverage() {
        System.out.println("================================");
        System.out.println("Test method start");

        assertEquals(5.0, Tests.calcAverage(Arrays.asList(2, 4, 6, 8)), 0.0001);
        assertEquals(3.0, Tests.calcAverage(Arrays.asList(1, 2, 3, 4, 5)), 0.0001);
        assertEquals(0.0, Tests.calcAverage(Arrays.asList()), 0.0001);

        System.out.println("Все тесты calcAverage пройдены ✅");
        System.out.println("Test method end");
        System.out.println("================================");
    }

    // ============================================================
    // ЗАДАЧА 12
    // ============================================================
    @Test
    @DisplayName("Задача 12 исключение из списка")
    void testRemoveSpecificName() {
        System.out.println("================================");
        System.out.println("Test method start");

        List<String> list = Arrays.asList("Lera", "Yana", "Alena", "Nastya");
        List<String> expected = Arrays.asList("Lera", "Yana", "Alena");

        assertEquals(expected, Tests.removeSpecificName(list, "Nastya"));
        assertEquals(Arrays.asList("Lera", "Yana", "Alena", "Nastya"), list);

        System.out.println("Все тесты removeSpecificName пройдены ✅");
        System.out.println("Test method end");
        System.out.println("================================");
    }
}