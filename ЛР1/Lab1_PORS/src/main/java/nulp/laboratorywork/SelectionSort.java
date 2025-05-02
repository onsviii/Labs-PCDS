package nulp.laboratorywork;

import java.io.*;
import java.util.*;

public class SelectionSort {
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = temp;
        }
    }

    public static void parallelSelectionSort(int[] arr, int[] arr2) throws InterruptedException {
        Thread thread1= new Thread(() -> selectionSort(arr));
        Thread thread2= new Thread(() -> selectionSort(arr2));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    public static int[] generateRandomArray(int size, int bound) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(bound);
        }
        return arr;
    }

    public static void saveArrayToFile(int[] arr, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int num : arr) {
                writer.write(num + " ");
            }
        }
    }

    public static int[] readArrayFromFile(String filename) throws IOException {
        List<Integer> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String[] numbers = reader.readLine().trim().split(" ");
            for (String num : numbers) {
                list.add(Integer.parseInt(num));
            }
        }
        return list.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        String filename = "data.txt";
        int size = 100000;
        int bound = 1000;

        try {
            int[] arr = generateRandomArray(size, bound);
            saveArrayToFile(arr, filename);
            arr = readArrayFromFile(filename);
            int[] arr2 = readArrayFromFile(filename);

            long startTime = System.nanoTime();
            selectionSort(arr);
            selectionSort(arr2);
            long endTime = System.nanoTime();
            saveArrayToFile(arr, "sorted_arr1.txt");
            saveArrayToFile(arr2, "sorted_arr2.txt");
            System.out.println("Послідовне сортування завершено. Час виконання: "
                    + (endTime - startTime) / 1e6 + " мс");

            arr = readArrayFromFile(filename);
            arr2 = readArrayFromFile(filename);
            startTime = System.nanoTime();
            parallelSelectionSort(arr, arr2);
            endTime = System.nanoTime();
            saveArrayToFile(arr, "parallel_sorted_arr1.txt");
            saveArrayToFile(arr2, "parallel_sorted_arr2.txt");
            System.out.println("Паралельне сортування завершено. Час виконання: "
                    + (endTime - startTime) / 1e6 + " мс");
        } catch (IOException | InterruptedException e) {
            System.out.println("Йой, неочікувана помилка...");
        }
    }
}
