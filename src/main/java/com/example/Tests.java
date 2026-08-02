package com.example;
import java.util.*;
public class Tests {
    //Задача 1
    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    //Задача 2
    public static String checkAccess(int age) {
        if (age > 18) {
            return "Allowed";
        } else {
            return "Denied";
        }
    }

    //Задача 3
    public static boolean isPositive(int n) {
        return n >= 0 ? true : false;
    }

    //Задача 4
    public static String getGrade(int score) {
        if (score < 0 || score > 100) return "Error"; //проверим что число в диапазоне 0-100
        if (score <= 20) return "E";
        if (score <= 40) return "D";
        if (score <= 60) return "C";
        if (score <= 80) return "B";
        return "A";
    }

    //Задача 5
    public static String blastOff(int start) {
        if (start < 1) return "Поехали!";
        StringBuilder s = new StringBuilder(); //тут берем StringBuilder для построения-создания строки
        for (int i = start; i >= 1; i--) {
            s.append(i);
            if (i > 1) s.append(" ");
        }
        s.append(" Поехали!");
        return s.toString();
    }

    //Задача 6
    public static int sumToN(int n) {
        if (n <= 0) {
            return 0;
        }
        return n * (n + 1) / 2; //тут берем формулу арифметической прогрессии an = a₁ + (n – 1) · d
    }

    //Задача 7
    public static boolean hasBug(String[] messages) {
        if (messages == null || messages.length == 0) {
            return false;
        }
        for (String message : messages) {
            if (message.equalsIgnoreCase("Bug")) {
                return true;
            }
        }
        return false;
    }

    //Задача 8
    public static String getEvenInRange(int start, int end) {
        if (start > end) return "";
        StringBuilder s = new StringBuilder();
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                if (s.length() > 0) s.append(" ");
                s.append(i);
            }
        }
        return s.toString();
    }

    //Задача 9
    public static int findMax(int[] arr) { //убрала лишний public из условия
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) max = arr[i];
        }
        return max;
    }

    //Задача 10
    public static String[] reverse(String[] arr) {
        if (arr == null) return null;
        String[] result = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[arr.length - 1 - i];
        }
        return result;
    }

    //Задача 11
    public static double calcAverage(List<Integer> list) {
        if (list == null || list.isEmpty()) return 0.0;
        int sum = 0;
        for (int num : list) sum += num;
        return (double) sum / list.size();
    }

    //Задача 12
    public static List<String> removeSpecificName(List<String> list, String nameToRemove) {
        if (list == null) return null;
        List<String> result = new ArrayList<>();
        for (String name : list) {
            if (!Objects.equals(name, nameToRemove)) {
                result.add(name);
            }
        }
        return result;
    }
}
