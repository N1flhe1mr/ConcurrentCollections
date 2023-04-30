package ru.netology;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    public static final int LETTERS_SIZE = 100;
    public static ArrayBlockingQueue<String> queueA = new ArrayBlockingQueue<>(LETTERS_SIZE);
    public static ArrayBlockingQueue<String> queueB = new ArrayBlockingQueue<>(LETTERS_SIZE);
    public static ArrayBlockingQueue<String> queueC = new ArrayBlockingQueue<>(LETTERS_SIZE);

    public static void main(String[] args) throws InterruptedException {
        Thread textGeneration = new Thread(() -> {
            for (int i = 0; i < LETTERS_SIZE; i++) {
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

        Thread findMaxB = new Thread(() -> {
            var maxB = queueA.stream()
                    .filter(s -> s.chars().allMatch(c -> c == 'b'))
                    .max(Comparator.comparing(String::length));
            maxB.ifPresent(System.out::println);
        });
        findMaxB.start();

        Thread findMaxC = new Thread(() -> {
            var maxC = queueA.stream()
                    .filter(s -> s.chars().allMatch(c -> c == 'c'))
                    .max(Comparator.comparing(String::length));
            maxC.ifPresent(System.out::println);
        });
        findMaxC.start();

        findMaxA.join();
        findMaxB.join();
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