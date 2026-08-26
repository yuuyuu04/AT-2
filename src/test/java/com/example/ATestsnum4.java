//Задача 2: Task2AssertionTest.java

//Задача 2: добавить в автотесты разработанные
// в задаче 2 темы «Gradle и JUnit» информативные ассерты
// (заменить проверки через if на ассерты).
// При падении ассерты должны дать информацию,
// что ожидаем и что было получено в результате падения.
// Дополнить задачу запуска автотестов фильтрацией,
// или по аннотации @Tag, или по пакету.
// Запустить каждый тестовый метод не менее 10 раз.

package com.example;

import org.junit.jupiter.api.*;
        import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ЗАДАЧА 2: Замена if на информативные ассерты
 *
 * Требования:
 * 1. Все тесты используют ассерты вместо if
 * 2. Информативные сообщения при падении
 * 3. Запуск каждого теста 10 раз
 * 4. Фильтрация по @Tag
 */
@Tag("task2")  // ← для фильтрации!
@DisplayName("Задача 2: Тесты с ассертами (замена if)")
public class ATestsnum4 {

    private static int testsPassed = 0;
    private static int testsFailed = 0;

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
    // 1. isEven (повтор 10 раз)
    // ============================================================
    @RepeatedTest(10)
    @Tag("task2")
    @DisplayName("1. isEven - проверка на чётность (10 раз)")
    void testIsEvenWithAssert(RepetitionInfo repetitionInfo) {
        System.out.println("Повтор #" + repetitionInfo.getCurrentRepetition());

        int number = 4;
        boolean result = Tests.isEven(number);
        boolean expected = true;

        System.out.println("Число: " + number);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);

        //   Заменили if на ассерт с информативным сообщением
        try {
            assertEquals(expected, result,
                    "isEven(" + number + ")   Ожидаемый результат " + expected +
                            ",   Фактический результат " + result);
            System.out.println("  TEST PASSED");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("   TEST FAILED: " + e.getMessage());
            testsFailed++;
            throw e;
        }
    }

    // ============================================================
    // 2. checkAccess (повтор 10 раз)
    // ============================================================
    @RepeatedTest(10)
    @Tag("task2")
    @DisplayName("2. checkAccess - проверка доступа (10 раз)")
    void testCheckAccessWithAssert(RepetitionInfo repetitionInfo) {
        System.out.println("Повтор #" + repetitionInfo.getCurrentRepetition());

        int age = 19;
        String result = Tests.checkAccess(age);
        String expected = "Allowed";

        System.out.println("Возраст: " + age);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);

        try {
            assertEquals(expected, result,
                    "checkAccess(" + age + ")   Ожидаемый результат '" + expected +
                            "', Фактический результат '" + result + "'");
            System.out.println("  TEST PASSED");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("   TEST FAILED: " + e.getMessage());
            testsFailed++;
            throw e;
        }
    }
// ============================================================
// 3. isPositive (повтор 10 раз)
// ============================================================
    @RepeatedTest(10)
    @Tag("task2")
    @DisplayName("3. isPositive - проверка положительного числа (10 раз)")
    void testIsPositiveWithAssert(RepetitionInfo repetitionInfo) {
        System.out.println("Повтор #" + repetitionInfo.getCurrentRepetition());

        int number = 5;
        boolean result = Tests.isPositive(number);
        boolean expected = true;

        System.out.println("Число: " + number);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);

        try {
            assertEquals(expected, result,
                    "isPositive(" + number + ") Ожидаемый результат " + expected +
                            ", Фактический результат " + result);
            System.out.println(" TEST PASSED");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println(" TEST FAILED: " + e.getMessage());
            testsFailed++;
            throw e;
        }
    }
    // ============================================================
    // 4. getGrade (повтор 10 раз)
    // ============================================================
    @RepeatedTest(10)
    @Tag("task2")
    @DisplayName("4. getGrade - определение оценки (10 раз)")
    void testGetGradeWithAssert(RepetitionInfo repetitionInfo) {
        System.out.println("Повтор #" + repetitionInfo.getCurrentRepetition());

        int score = 85;
        String result = Tests.getGrade(score);
        String expected = "A";

        System.out.println("Оценка: " + score);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);

        try {
            assertEquals(expected, result,
                    "getGrade(" + score + ")   Ожидаемый результат '" + expected +
                            "', Фактический результат '" + result + "'");
            System.out.println("  TEST PASSED");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("   TEST FAILED: " + e.getMessage());
            testsFailed++;
            throw e;
        }
    }

    // ============================================================
    // 5. blastOff (повтор 10 раз)
    // ============================================================
    @RepeatedTest(10)
    @Tag("task2")
    @DisplayName("5. blastOff - обратный отсчёт (10 раз)")
    void testBlastOffWithAssert(RepetitionInfo repetitionInfo) {
        System.out.println("Повтор #" + repetitionInfo.getCurrentRepetition());

        int start = 5;
        String result = Tests.blastOff(start);
        String expected = "5 4 3 2 1 Поехали!";

        System.out.println("Старт: " + start);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);

        try {
            assertEquals(expected, result,
                    "blastOff(" + start + ")   Ожидаемый результат '" + expected +
                            "', Фактический результат '" + result + "'");
            System.out.println("  TEST PASSED");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("   TEST FAILED: " + e.getMessage());
            testsFailed++;
            throw e;
        }
    }

    // ============================================================
    // 6. sumToN (повтор 10 раз)
    // ============================================================
    @RepeatedTest(10)
    @Tag("task2")
    @DisplayName("6. sumToN - сумма чисел (10 раз)")
    void testSumToNWithAssert(RepetitionInfo repetitionInfo) {
        System.out.println("Повтор #" + repetitionInfo.getCurrentRepetition());

        int n = 5;
        int result = Tests.sumToN(n);
        int expected = 15;

        System.out.println("n = " + n);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);

        try {
            assertEquals(expected, result,
                    "sumToN(" + n + ")   Ожидаемый результат " + expected +
                            ",   Фактический результат " + result);
            System.out.println("  TEST PASSED");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("   TEST FAILED: " + e.getMessage());
            testsFailed++;
            throw e;
        }
    }

    // ============================================================
    // 7. hasBug (повтор 10 раз)
    // ============================================================
    @RepeatedTest(10)
    @Tag("task2")
    @DisplayName("7. hasBug - поиск Bug в массиве (10 раз)")
    void testHasBugWithAssert(RepetitionInfo repetitionInfo) {
        System.out.println("Повтор #" + repetitionInfo.getCurrentRepetition());

        String[] messages = {"Hello", "Bug", "World"};
        boolean result = Tests.hasBug(messages);
        boolean expected = true;

        System.out.println("Массив: " + Arrays.toString(messages));
        System.out.println("Результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);

        try {
            assertTrue(result,
                    "hasBug(" + Arrays.toString(messages) + ")   Ожидаемый результат " + expected +
                            ",   Фактический результат " + result);
            System.out.println("  TEST PASSED");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("   TEST FAILED: " + e.getMessage());
            testsFailed++;
            throw e;
        }
    }

// ============================================================
// 8. getEvenInRange (повтор 10 раз)
// ============================================================
@RepeatedTest(10)
@Tag("task2")
@DisplayName("8. getEvenInRange - чётные числа в диапазоне (10 раз)")
void testGetEvenInRangeWithAssert(RepetitionInfo repetitionInfo) {
    System.out.println("Повтор #" + repetitionInfo.getCurrentRepetition());

    int start = 2;
    int end = 10;
    String result = Tests.getEvenInRange(start, end);
    String expected = "2 4 6 8 10";

    System.out.println("Диапазон: " + start + " - " + end);
    System.out.println("Результат: " + result);
    System.out.println("Ожидаемый результат: " + expected);

    try {
        assertEquals(expected, result,
                "getEvenInRange(" + start + ", " + end + ")   Ожидаемый результат '" + expected +
                        "', Фактический результат '" + result + "'");
        System.out.println("  TEST PASSED");
        testsPassed++;
    } catch (AssertionError e) {
        System.out.println("   TEST FAILED: " + e.getMessage());
        testsFailed++;
        throw e;
    }
}

    // ============================================================
    // 9. reverse (повтор 10 раз)
    // ============================================================
    @RepeatedTest(10)
    @Tag("task2")
    @DisplayName("9. reverse - обратный порядок массива (10 раз)")
    void testReverseWithAssert(RepetitionInfo repetitionInfo) {
        System.out.println("Повтор #" + repetitionInfo.getCurrentRepetition());

        String[] input = {"One", "Two", "Three", "Zero"};
        String[] expected = {"Zero", "Three", "Two", "One"};
        String[] result = Tests.reverse(input);

        System.out.println("Входной массив: " + Arrays.toString(input));
        System.out.println("Результат: " + Arrays.toString(result));
        System.out.println("Ожидаемый результат: " + Arrays.toString(expected));

        try {
            assertArrayEquals(expected, result,
                    "reverse(" + Arrays.toString(input) + ")   Ожидаемый результат " + Arrays.toString(expected) +
                            ", Фактический результат " + Arrays.toString(result));
            System.out.println("  TEST PASSED");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("   TEST FAILED: " + e.getMessage());
            testsFailed++;
            throw e;
        }
    }

    // ============================================================
    // 10. calcAverage (повтор 10 раз)
    // ============================================================
    @RepeatedTest(10)
    @Tag("task2")
    @DisplayName("10. calcAverage - среднее арифметическое (10 раз)")
    void testCalcAverageWithAssert(RepetitionInfo repetitionInfo) {
        System.out.println("Повтор #" + repetitionInfo.getCurrentRepetition());

        List<Integer> list = Arrays.asList(2, 4, 6, 8);
        double result = Tests.calcAverage(list);
        double expected = 5.0;

        System.out.println("Список: " + list);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);

        try {
            assertEquals(expected, result, 0.0001,
                    "calcAverage(" + list + ")   Ожидаемый результат " + expected +
                            ", Фактический результат " + result);
            System.out.println("  TEST PASSED");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("   TEST FAILED: " + e.getMessage());
            testsFailed++;
            throw e;
        }
    }

    // ============================================================
    // 11. removeSpecificName (повтор 10 раз)
    // ============================================================
    @RepeatedTest(10)
    @Tag("task2")
    @DisplayName("11. removeSpecificName - удаление имени (10 раз)")
    void testRemoveSpecificNameWithAssert(RepetitionInfo repetitionInfo) {
        System.out.println("Повтор #" + repetitionInfo.getCurrentRepetition());

        List<String> list = Arrays.asList("Alice", "Bob", "Charlie", "Bob");
        List<String> expected = Arrays.asList("Alice", "Charlie");
        List<String> result = Tests.removeSpecificName(list, "Bob");

        System.out.println("Список: " + list);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);

        try {
            assertEquals(expected, result,
                    "removeSpecificName(" + list + ", 'Bob')   Ожидаемый результат " + expected +
                            ", Фактический результат " + result);
            System.out.println("  TEST PASSED");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("   TEST FAILED: " + e.getMessage());
            testsFailed++;
            throw e;
        }
    }

    // ============================================================
    // 12. findMax (повтор 10 раз)
    // ============================================================
    @RepeatedTest(10)
    @Tag("task2")
    @DisplayName("12. findMax - максимум в массиве (10 раз)")
    void testFindMaxWithAssert(RepetitionInfo repetitionInfo) {
        System.out.println("Повтор #" + repetitionInfo.getCurrentRepetition());

        int[] arr = {3, 7, 2, 9, 5};
        int result = Tests.findMax(arr);
        int expected = 9;

        System.out.println("Массив: " + Arrays.toString(arr));
        System.out.println("Результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);

        try {
            assertEquals(expected, result,
                    "findMax(" + Arrays.toString(arr) + ")   Ожидаемый результат " + expected +
                            ", Фактический результат " + result);
            System.out.println("  TEST PASSED");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("   TEST FAILED: " + e.getMessage());
            testsFailed++;
            throw e;
        }
    }

    // ============================================================
    // 13. ПАДАЮЩИЙ ТЕСТ (демонстрация информативного ассерта)
    // ============================================================
    @RepeatedTest(10)
    @Tag("failing")
    @DisplayName("13. ПАДАЮЩИЙ ТЕСТ (10 раз)")
    void testFailingWithAssert(RepetitionInfo repetitionInfo) {
        System.out.println("Повтор #" + repetitionInfo.getCurrentRepetition());
        System.out.println("⚠️ Этот тест специально упадёт!");

        int number = 7;
        boolean result = Tests.isEven(number);
        boolean expected = true; // Специально ждём true!

        System.out.println("Число: " + number);
        System.out.println("Результат: " + result);
        System.out.println("Ожидаемый результат: " + expected);

        //   ИНФОРМАТИВНЫЙ ПАДАЮЩИЙ АССЕРТ
        assertEquals(expected, result,
                "   ОШИБКА: isEven(" + number + ")   Ожидаемый результат " + expected +
                        ", Фактический результат " + result +
                        ". (Число " + number + " - нечётное)");
    }

    // ============================================================
    // ИТОГОВАЯ СТАТИСТИКА
    // ============================================================
    @AfterAll
    static void printSummary() {
        System.out.println("\n========================================");
        System.out.println(" ИТОГОВАЯ СТАТИСТИКА");
        System.out.println("  Пройдено тестов: " + testsPassed);
        System.out.println("   Провалено тестов: " + testsFailed);
        System.out.println("========================================");
    }
}