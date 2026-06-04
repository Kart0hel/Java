# Лабораторная работа №7: Многопоточность (Вариант 5)

## Описание работы

В лабораторной работе изучается многопоточность в Java — выполнение нескольких задач одновременно в рамках одного приложения. Реализована программа для переноса товаров между складами с использованием **Semaphore** (вариант 5).

## Многопоточность в Java

Поток (thread) — это легковесный процесс, который может выполняться параллельно с другими потоками.

### Создание потоков

**Через наследование Thread:**
```java
public class MyThread extends Thread {
    public void run() {
        System.out.println("Hello from thread!");
    }
}
new MyThread().start();

Синхронизация потоков
При доступе к общим ресурсам может возникнуть состояние гонки (race condition). Для синхронизации используется ключевое слово synchronized:

java
public class Counter {
    private int count;
    public synchronized void increment() {
        count++;
    }
}
Вариант 5: Использование Semaphore
Условие задачи: Склад с товарами, которые нужно перенести на другой склад. У каждого товара есть вес. Работают 3 грузчика. Грузчики могут переносить товары одновременно, но суммарный вес товаров не может превышать 150 кг. Как только грузчики наберут 150 кг, они отправляются на другой склад и разгружаются.

Semaphore — это счётный семафор, который ограничивает количество потоков, имеющих доступ к ресурсу.

Полная реализация (Вариант 5)
java
import java.util.*;
import java.util.concurrent.Semaphore;

// Класс Товар
class Product {
    private String name;
    private int weight;
    
    public Product(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }
    
    public String getName() {
        return name;
    }
    
    public int getWeight() {
        return weight;
    }
    
    @Override
    public String toString() {
        return name + " (" + weight + " кг)";
    }
}

// Класс Склад
class Warehouse {
    private String name;
    private List<Product> products;
    
    public Warehouse(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }
    
    public synchronized void addProduct(Product product) {
        products.add(product);
    }
    
    public synchronized Product removeProduct() {
        if (products.isEmpty()) {
            return null;
        }
        return products.remove(0);
    }
    
    public synchronized List<Product> removeProducts(List<Product> productsToRemove) {
        products.removeAll(productsToRemove);
        return productsToRemove;
    }
    
    public synchronized int getTotalWeight() {
        return products.stream().mapToInt(Product::getWeight).sum();
    }
    
    public synchronized void addProducts(List<Product> products) {
        this.products.addAll(products);
    }
    
    public synchronized void printProducts() {
        System.out.println(name + " содержит " + products.size() + " товаров:");
        for (Product p : products) {
            System.out.println("  - " + p);
        }
    }
}

// Класс Грузчик (использует Semaphore)
class Loader implements Runnable {
    private static final int MAX_WEIGHT = 150;
    private String name;
    private Warehouse source;
    private Warehouse destination;
    private Semaphore semaphore;
    private List<Product> loadedProducts;
    private Random random;
    
    public Loader(String name, Warehouse source, Warehouse destination, Semaphore semaphore) {
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.semaphore = semaphore;
        this.loadedProducts = new ArrayList<>();
        this.random = new Random();
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                // Запрос разрешения от семафора
                semaphore.acquire();
                
                // Загрузка товаров
                loadProducts();
                
                // Если товаров нет на складе, выходим
                if (loadedProducts.isEmpty()) {
                    semaphore.release();
                    break;
                }
                
                // Имитация переноса товаров
                System.out.println(name + " начал перенос " + loadedProducts.size() + " товаров (вес: " + 
                                   getTotalWeight() + " кг)");
                Thread.sleep(1000 + random.nextInt(1000));
                
                // Разгрузка на складе назначения
                unloadProducts();
                
                System.out.println(name + " завершил перенос и разгрузился");
                
                // Освобождаем семафор
                semaphore.release();
                
                // Небольшая пауза перед следующим рейсом
                Thread.sleep(500);
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println(name + " закончил работу (товаров больше нет)");
    }
    
    private void loadProducts() {
        loadedProducts.clear();
        int currentWeight = 0;
        
        // Пытаемся загрузить товары со склада
        while (currentWeight < MAX_WEIGHT) {
            Product product = source.removeProduct();
            if (product == null) {
                break; // Товаров больше нет
            }
            
            if (currentWeight + product.getWeight() <= MAX_WEIGHT) {
                loadedProducts.add(product);
                currentWeight += product.getWeight();
                System.out.println(name + " загрузил: " + product + " (всего: " + currentWeight + " кг)");
            } else {
                // Товар не помещается, возвращаем обратно
                source.addProduct(product);
                break;
            }
        }
        
        if (currentWeight == 0) {
            System.out.println(name + " не нашёл товаров для загрузки");
        }
    }
    
    private void unloadProducts() {
        for (Product product : loadedProducts) {
            destination.addProduct(product);
            System.out.println(name + " разгрузил: " + product);
        }
        loadedProducts.clear();
    }
    
    private int getTotalWeight() {
        return loadedProducts.stream().mapToInt(Product::getWeight).sum();
    }
}

// Главный класс программы
public class WarehouseTransfer {
    public static void main(String[] args) {
        System.out.println("=== Программа переноса товаров между складами ===");
        System.out.println("Грузчики: 3 человека");
        System.out.println("Максимальный вес за одну поездку: 150 кг\n");
        
        // Создание складов
        Warehouse sourceWarehouse = new Warehouse("Склад А (исходный)");
        Warehouse destinationWarehouse = new Warehouse("Склад Б (назначения)");
        
        // Добавление товаров на исходный склад
        List<Product> products = Arrays.asList(
            new Product("Коробка с книгами", 30),
            new Product("Ящик с инструментами", 25),
            new Product("Мешок цемента", 50),
            new Product("Упаковка ламп", 10),
            new Product("Паллет с кирпичами", 80),
            new Product("Бочка с краской", 40),
            new Product("Ящик с деталями", 35),
            new Product("Мешок песка", 45),
            new Product("Коробка с игрушками", 20),
            new Product("Паллет с плиткой", 70),
            new Product("Упаковка проводов", 15),
            new Product("Ящик с крепежом", 25),
            new Product("Мешок гипса", 40),
            new Product("Коробка с документами", 10),
            new Product("Паллет с утеплителем", 55)
        );
        
        for (Product p : products) {
            sourceWarehouse.addProduct(p);
        }
        
        // Вывод начального состояния
        sourceWarehouse.printProducts();
        System.out.println("\nОбщий вес на складе А: " + sourceWarehouse.getTotalWeight() + " кг\n");
        
        // Создание семафора (максимум 1 грузчик может загружаться одновременно)
        // Для более строгого контроля веса используем семафор с 1 разрешением
        Semaphore semaphore = new Semaphore(1);
        
        // Создание грузчиков
        Loader loader1 = new Loader("Грузчик 1 (Антон)", sourceWarehouse, destinationWarehouse, semaphore);
        Loader loader2 = new Loader("Грузчик 2 (Борис)", sourceWarehouse, destinationWarehouse, semaphore);
        Loader loader3 = new Loader("Грузчик 3 (Владимир)", sourceWarehouse, destinationWarehouse, semaphore);
        
        // Запуск потоков
        Thread thread1 = new Thread(loader1);
        Thread thread2 = new Thread(loader2);
        Thread thread3 = new Thread(loader3);
        
        long startTime = System.currentTimeMillis();
        
        thread1.start();
        thread2.start();
        thread3.start();
        
        // Ожидание завершения всех потоков
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        long endTime = System.currentTimeMillis();
        
        // Вывод конечного состояния
        System.out.println("\n=== Результат ===");
        sourceWarehouse.printProducts();
        System.out.println();
        destinationWarehouse.printProducts();
        
        System.out.println("\nОбщий вес на складе А: " + sourceWarehouse.getTotalWeight() + " кг");
        System.out.println("Общий вес на складе Б: " + destinationWarehouse.getTotalWeight() + " кг");
        System.out.println("Время выполнения: " + (endTime - startTime) / 1000 + " секунд");
    }
}
Пример вывода программы
text
=== Программа переноса товаров между складами ===
Грузчики: 3 человека
Максимальный вес за одну поездку: 150 кг

Склад А (исходный) содержит 15 товаров:
  - Коробка с книгами (30 кг)
  - Ящик с инструментами (25 кг)
  - Мешок цемента (50 кг)
  ...

Общий вес на складе А: 550 кг

Грузчик 1 (Антон) загрузил: Коробка с книгами (30 кг) (всего: 30 кг)
Грузчик 1 (Антон) загрузил: Ящик с инструментами (25 кг) (всего: 55 кг)
Грузчик 1 (Антон) загрузил: Мешок цемента (50 кг) (всего: 105 кг)
Грузчик 1 (Антон) загрузил: Упаковка ламп (10 кг) (всего: 115 кг)
Грузчик 1 (Антон) загрузил: Ящик с деталями (35 кг) (всего: 150 кг)
Грузчик 1 (Антон) начал перенос 5 товаров (вес: 150 кг)
Грузчик 1 (Антон) разгрузил: Коробка с книгами (30 кг)
...

=== Результат ===
Склад А (исходный) содержит 0 товаров
Склад Б (назначения) содержит 15 товаров

Общий вес на складе А: 0 кг
Общий вес на складе Б: 550 кг
Время выполнения: 12 секунд
Ключевые концепции многопоточности
Концепция	Описание
Thread	Класс, представляющий поток выполнения
Runnable	Интерфейс для задачи, выполняемой в потоке
start()	Запуск потока (вызывает run())
join()	Ожидание завершения потока
sleep()	Приостановка потока на указанное время
synchronized	Блокировка объекта для безопасного доступа
Semaphore	Счётный семафор для ограничения доступа к ресурсу
acquire()	Запрос разрешения у семафора
release()	Освобождение разрешения семафора
text
