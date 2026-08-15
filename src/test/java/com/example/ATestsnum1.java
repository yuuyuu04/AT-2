
//Задача 1: создать проект на основе Gradle. В файле build.gradle описать две задачи:
//1. Задача запускает все тесты в проекте.
//2. После прогона тестов пишет в консоль Test run is over и запускается после завершения первой.
//Требования к тестовому классу Класс запускает три метода из задания к теме «Базовая Java»:
//
//boolean isEven(int n) — запустить метод один раз со случайным числом от 1 до 100;
//String checkAccess(int age) — запустить метод 20 раз со случайными числами от 0 до 99;
//String getGrade(int score) — запустить метод в параметризованных тестах с массивом случайных чисел от 0 до 100.
//Перед запуском каждого метода должна быть выведена строка:
//
//========================Test method start
//
//После работы каждого метода должна быть выведена строка:
//
//Test method end
//
//========================

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
public class ATestsnum1 {
    private static final Random random = new Random();

    // ============================================================
    // ЗАДАЧА testIsEven
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
    // ЗАДАЧА testCheckAccess
    // ============================================================
    @RepeatedTest(20)
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
    // ЗАДАЧА testGetGrade
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
}
