package ru.netology;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    public static int lettersSize = 100;
    public static ArrayBlockingQueue<String> maxA = new ArrayBlockingQueue<>(lettersSize);
    public static ArrayBlockingQueue<String> maxB = new ArrayBlockingQueue<>(lettersSize);
    public static ArrayBlockingQueue<String> maxC = new ArrayBlockingQueue<>(lettersSize);


    public static void main(String[] args) throws InterruptedException {
        Thread textGeneration = new Thread(() -> {
            for (int i = 0; i < lettersSize; i++) {
                Random random = new Random();
                maxA.add(generateText("abc", 3 + random.nextInt(3)));
                maxB.add(generateText("abc", 3 + random.nextInt(3)));
                maxC.add(generateText("abc", 3 + random.nextInt(3)));
            }
        });

        Thread filterA = new Thread(() -> {
            for (int i = 0; i < lettersSize; i++) {
                for (String text : maxA) {
                    if (!allSameA(text)) {
                        maxA.remove(text);
                    }
                }
            }
        });

        Thread filterB = new Thread(() -> {
            for (int i = 0; i < lettersSize; i++) {
                for (String text : maxB) {
                    if (!allSameB(text)) {
                        maxB.remove(text);
                    }
                }
            }
        });

        Thread filterC = new Thread(() -> {
            for (int i = 0; i < lettersSize; i++) {
                for (String text : maxC) {
                    if (!allSameC(text)) {
                        maxC.remove(text);
                    }
                }
            }
        });

        textGeneration.start();
        textGeneration.join();
        filterA.start();
        filterB.start();
        filterC.start();
        filterA.join();
        filterB.join();
        filterC.join();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean allSameA(String str) {
        return str != null && !str.isEmpty() && str.chars().allMatch(ch -> 'a' == ch);
    }

    public static boolean allSameB(String str) {
        return str != null && !str.isEmpty() && str.chars().allMatch(ch -> 'b' == ch);
    }

    public static boolean allSameC(String str) {
        return str != null && !str.isEmpty() && str.chars().allMatch(ch -> 'c' == ch);
    }
}