package ru.netology;

import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    public static int lettersSize = 100;
    public static ArrayBlockingQueue<String> queueA = new ArrayBlockingQueue<>(lettersSize);
    public static ArrayBlockingQueue<String> queueB = new ArrayBlockingQueue<>(lettersSize);
    public static ArrayBlockingQueue<String> queueC = new ArrayBlockingQueue<>(lettersSize);

    public static void main(String[] args) throws InterruptedException {
        Thread textGeneration = new Thread(() -> {
            for (int i = 0; i < lettersSize; i++) {
                Random random = new Random();
                queueA.add(generateText("abc", 3 + random.nextInt(3)));
                queueB.add(generateText("abc", 3 + random.nextInt(3)));
                queueC.add(generateText("abc", 3 + random.nextInt(3)));
            }
        });
        textGeneration.start();
        textGeneration.join();

        Thread findMaxA = new Thread(() -> {
            var maxA = queueA.stream()
                    .filter(s -> s.chars().allMatch(c -> c == 'a'))
                    .max(Comparator.comparing(String::length));
            maxA.ifPresent(System.out::println);
        });
        findMaxA.start();
        findMaxA.join();

        Thread findMaxB = new Thread(() -> {
            var maxB = queueA.stream()
                    .filter(s -> s.chars().allMatch(c -> c == 'b'))
                    .max(Comparator.comparing(String::length));
            maxB.ifPresent(System.out::println);
        });
        findMaxB.start();
        findMaxB.join();

        Thread findMaxC = new Thread(() -> {
            var maxC = queueA.stream()
                    .filter(s -> s.chars().allMatch(c -> c == 'c'))
                    .max(Comparator.comparing(String::length));
            maxC.ifPresent(System.out::println);
        });
        findMaxC.start();
        findMaxC.join();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}