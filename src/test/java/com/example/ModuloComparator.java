package com.example;

import java.util.Arrays;
import java.util.Comparator;

public class ModuloComparator {

    /**
     * Компаратор: сортировка по модулю в порядке убывания.
     *
     * Пример: (-900, 783, 650, -500, -400, 321)
     * Результат: (-900, 783, 650, -500, -400, 321)
     */
    public static Comparator<Integer> moduloDescComparator() {
        return (a, b) -> Integer.compare(Math.abs(b), Math.abs(a));
    }

    // Демонстрация
    public static void main(String[] args) {
        Integer[] arr = {-900, 783, 650, -500, -400, 321};

        Arrays.sort(arr, moduloDescComparator());

        System.out.println(Arrays.toString(arr));
        // [-900, 783, 650, -500, -400, 321]
    }
}
