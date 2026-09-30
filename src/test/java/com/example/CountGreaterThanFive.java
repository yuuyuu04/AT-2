package com.example;

import java.util.List;

public class CountGreaterThanFive {

    /**
     * С помощью Stream API находит количество элементов > 5.
     *
     * Пример: [1, 3, 6, 8, 2, 10, 4] → 3 (6, 8, 10)
     */
    public static long countGreaterThanFive(List<Integer> list) {
        return list.stream()
                .filter(n -> n > 5)
                .count();
    }

    // Демонстрация
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 3, 6, 8, 2, 10, 4);

        long count = countGreaterThanFive(numbers);

        System.out.println("Количество > 5: " + count); // 3
    }
}
