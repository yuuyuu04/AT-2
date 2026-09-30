package com.example;

import java.util.Arrays;
import java.util.Comparator;

public class SudoComparator {

    /**
     * Компаратор: строки с "sudo" — выше остальных.
     * Если обе содержат "sudo" — они равны.
     *
     * Пример: ["hello", "sudo user", "world", "sudo root"]
     * Результат: ["sudo user", "sudo root", "hello", "world"]
     */
    public static Comparator<String> sudoFirstComparator() {
        return (a, b) -> {
            boolean aHasSudo = a.contains("sudo");
            boolean bHasSudo = b.contains("sudo");

            if (aHasSudo && bHasSudo) {
                return 0; // обе содержат sudo → равны
            }
            if (aHasSudo) {
                return -1; // a выше b
            }
            if (bHasSudo) {
                return 1; // b выше a
            }
            return 0; // ни одна не содержит sudo → равны
        };
    }

    // Демонстрация
    public static void main(String[] args) {
        String[] arr = {"hello", "sudo user", "world", "sudo root"};

        Arrays.sort(arr, sudoFirstComparator());

        System.out.println(Arrays.toString(arr));
        // [sudo user, sudo root, hello, world]
    }
}
