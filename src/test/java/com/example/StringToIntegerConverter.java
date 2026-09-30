package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringToIntegerConverter {

    public static List<Integer> convertToIntegers(String[] strings) {
        return Arrays.stream(strings)
                .filter(s -> s.matches("-?\\d+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());   //
    }

    public static void main(String[] args) {
        String[] strings = {"1", "abc", "2", "5x", "3", "hello", "-10"};

        List<Integer> integers = convertToIntegers(strings);

        System.out.println(integers); // [1, 2, 3, -10]
    }
}
