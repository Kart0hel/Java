import java.util.*;
import java.util.concurrent.*;

class Warehouse {
    public int currentWeight = 0;
    public int maxLimit = 150;
    public int itemsLeft = 20;

    public synchronized boolean tryTakeItem(int loaderId) {
        if (itemsLeft <= 0) {
            return false;
        }

        if (currentWeight + 20 > maxLimit) {
            System.out.println("Вес на весах: " + currentWeight + " кг. Близко к лимиту! Разгружаем весы...");
            currentWeight = 0;
            return false;
        }

        currentWeight += 20;
        itemsLeft--;
        System.out.println("Грузчик №" + loaderId + " взял груз 20 кг. (На весах: " + currentWeight + " кг. Осталось: " + itemsLeft + " шт.)");
        return true;
    }
}

public class Lab7Main {
    public static void main(String[] args) throws Exception {
        System.out.println("=== ЗАДАНИЕ 1: СУММА МАССИВА В 2 ПОТОКА ===");
        runTask1();

        System.out.println("\n=== ЗАДАНИЕ 2: МАКСИМУМ В МАТРИЦЕ ===");
        runTask2();

        System.out.println("\n=== ЗАДАНИЕ 3: СИМУЛЯЦИЯ СКЛАДА (Вариант 5) ===");
        runTask3();
    }

    public static void runTask1() throws Exception {
        int[] array = new int[1000];
        Arrays.fill(array, 2); 

        final int[] sum1 = {0};
        final int[] sum2 = {0};

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 500; i++) {
                    sum1[0] += array[i];
                }
                System.out.println("Поток 1 закончил считать свою половину.");
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 500; i < 1000; i++) {
                    sum2[0] += array[i];
                }
                System.out.println("Поток 2 закончил считать свою половину.");
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        int totalSum = sum1[0] + sum2[0];
        System.out.println("Итоговая сумма всего массива: " + totalSum);
    }

    public static void runTask2() throws Exception {
        int[][] matrix = {
            {12, 45, 2},
            {89, 31, 64},
            {7, 18, 95}
        };

        int[] rowMaxes = new int[3];
        Thread[] threads = new Thread[3];

        for (int i = 0; i < 3; i++) {
            final int rowIndex = i;

            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    int maxInRow = matrix[rowIndex][0];
                    for (int j = 1; j < 3; j++) {
                        if (matrix[rowIndex][j] > maxInRow) {
                            maxInRow = matrix[rowIndex][j];
                        }
                    }
                    rowMaxes[rowIndex] = maxInRow;
                    System.out.println("Поток для строки " + rowIndex + " нашел максимум: " + maxInRow);
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < 3; i++) {
            threads[i].join();
        }

        int globalMax = rowMaxes[0];
        for (int i = 1; i < 3; i++) {
            if (rowMaxes[i] > globalMax) {
                globalMax = rowMaxes[i];
            }
        }
        System.out.println("Глобальный максимум в матрице: " + globalMax);
    }

public static void runTask3() throws Exception {
        Warehouse warehouse = new Warehouse();

        CompletableFuture<?>[] loaders = new CompletableFuture[3];

        for (int i = 0; i < 3; i++) {
            final int loaderId = i + 1;

            loaders[i] = CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    while (warehouse.itemsLeft > 0) {
                        boolean success = warehouse.tryTakeItem(loaderId);
                        try {
                            if (success) {
                                Thread.sleep(200); 
                            } else {
                                Thread.sleep(50);  
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Поток грузчика №" + loaderId + " завершил работу. Грузов больше нет!");
                }
            });
        }

        CompletableFuture.allOf(loaders).join();
        
        System.out.println("Все грузы успешно перенесены с помощью CompletableFuture!");
    }
}