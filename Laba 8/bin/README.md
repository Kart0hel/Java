# Лабораторная работа №8: Аннотации, Stream API и многопоточная обработка данных

## Описание работы

В лабораторной работе изучаются аннотации (Annotations), Stream API и многопоточная обработка данных с использованием `java.util.concurrent`. Разработано приложение, которое считывает данные из файла, применяет к ним операции с помощью Stream API, использует многопоточность для обработки и сохраняет результаты в новый файл.

## Аннотации (Annotations)

Аннотации — это метаданные, которые можно присоединять к классам, методам, полям и другим элементам программы. Они используются для:
- Пометки кода для анализа инструментами
- Управления поведением компилятора
- Получения информации во время выполнения (RetentionPolicy.RUNTIME)

```java
// Создание собственной аннотации
public @interface DataProcessor {
    String description() default "";
    int priority() default 0;
}

Stream API
Stream API предоставляет набор операций для обработки данных в функциональном стиле:

java
List<Person> olderThan30 = people.stream()
    .filter(person -> person.getAge() > 30)
    .sorted(Comparator.comparing(Person::getAge))
    .collect(Collectors.toList());
Основные операции Stream API
Операция	Описание
filter()	Фильтрация элементов по условию
map()	Преобразование каждого элемента
sorted()	Сортировка элементов
collect()	Сбор результатов в коллекцию
forEach()	Выполнение действия для каждого элемента
reduce()	Агрегация элементов в одно значение
count()	Подсчёт количества элементов
Полная реализация (Лабораторная работа №8)
Шаг 1: Создание аннотации @DataProcessor
java
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataProcessor {
    String description() default "";
    int priority() default 0;
}
Шаг 2: Класс DataManager (многопоточная обработка данных)
java
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class DataManager {
    private List<String> data;
    private List<Object> processors;
    private ExecutorService executorService;
    
    public DataManager() {
        this.data = new ArrayList<>();
        this.processors = new ArrayList<>();
        this.executorService = Executors.newFixedThreadPool(4);
    }
    
    // Регистрация обработчика данных
    public void registerDataProcessor(Object processor) {
        processors.add(processor);
        System.out.println("Зарегистрирован обработчик: " + processor.getClass().getSimpleName());
    }
    
    // Загрузка данных из файла
    public void loadData(String source) throws IOException {
        data.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        }
        System.out.println("Загружено " + data.size() + " строк из " + source);
    }
    
    // Многопоточная обработка данных
    public void processData() {
        if (data.isEmpty()) {
            System.out.println("Нет данных для обработки");
            return;
        }
        
        List<CompletableFuture<List<String>>> futures = new ArrayList<>();
        
        for (Object processor : processors) {
            CompletableFuture<List<String>> future = CompletableFuture.supplyAsync(() -> {
                List<String> result = new ArrayList<>(data);
                Method[] methods = processor.getClass().getDeclaredMethods();
                
                for (Method method : methods) {
                    if (method.isAnnotationPresent(DataProcessor.class)) {
                        DataProcessor annotation = method.getAnnotation(DataProcessor.class);
                        System.out.println(Thread.currentThread().getName() + " выполняет: " + 
                                         method.getName() + " (" + annotation.description() + ")");
                        try {
                            @SuppressWarnings("unchecked")
                            List<String> processed = (List<String>) method.invoke(processor, result);
                            result = processed;
                        } catch (Exception e) {
                            System.err.println("Ошибка при вызове " + method.getName() + ": " + e.getMessage());
                        }
                    }
                }
                return result;
            }, executorService);
            futures.add(future);
        }
        
        // Сбор результатов
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        
        // Объединение результатов
        List<String> finalData = new ArrayList<>();
        for (CompletableFuture<List<String>> future : futures) {
            try {
                finalData.addAll(future.get());
            } catch (Exception e) {
                System.err.println("Ошибка получения результата: " + e.getMessage());
            }
        }
        
        data = finalData.stream().distinct().collect(Collectors.toList());
        System.out.println("Обработка завершена. Получено " + data.size() + " записей");
    }
    
    // Сохранение данных в файл
    public void saveData(String destination) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destination))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        }
        System.out.println("Сохранено " + data.size() + " строк в " + destination);
    }
    
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
Шаг 3: Обработчики данных с аннотацией @DataProcessor
java
import java.util.*;
import java.util.stream.*;

// Обработчик 1: Фильтрация строк
public class FilterProcessor {
    
    @DataProcessor(description = "Фильтрация строк длиной более 5 символов", priority = 1)
    public List<String> filterLongStrings(List<String> input) {
        return input.stream()
            .filter(s -> s.length() > 5)
            .collect(Collectors.toList());
    }
    
    @DataProcessor(description = "Фильтрация строк, содержащих букву 'а'", priority = 2)
    public List<String> filterContainingA(List<String> input) {
        return input.stream()
            .filter(s -> s.toLowerCase().contains("а"))
            .collect(Collectors.toList());
    }
}

// Обработчик 2: Трансформация данных
public class TransformProcessor {
    
    @DataProcessor(description = "Преобразование в верхний регистр", priority = 1)
    public List<String> toUpperCase(List<String> input) {
        return input.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());
    }
    
    @DataProcessor(description = "Добавление префикса", priority = 2)
    public List<String> addPrefix(List<String> input) {
        return input.stream()
            .map(s -> "[DATA] " + s)
            .collect(Collectors.toList());
    }
    
    @DataProcessor(description = "Удаление пробелов в начале и конце", priority = 3)
    public List<String> trimStrings(List<String> input) {
        return input.stream()
            .map(String::trim)
            .collect(Collectors.toList());
    }
}

// Обработчик 3: Агрегация и сортировка
public class AggregateProcessor {
    
    @DataProcessor(description = "Сортировка в алфавитном порядке", priority = 1)
    public List<String> sortStrings(List<String> input) {
        return input.stream()
            .sorted()
            .collect(Collectors.toList());
    }
    
    @DataProcessor(description = "Удаление дубликатов", priority = 2)
    public List<String> removeDuplicates(List<String> input) {
        return input.stream()
            .distinct()
            .collect(Collectors.toList());
    }
    
    @DataProcessor(description = "Обратная сортировка", priority = 3)
    public List<String> reverseSort(List<String> input) {
        return input.stream()
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());
    }
}

// Обработчик 4: Статистика и подсчёт
public class StatisticsProcessor {
    
    @DataProcessor(description = "Подсчёт длины каждой строки", priority = 1)
    public List<String> countLength(List<String> input) {
        return input.stream()
            .map(s -> s + " (длина: " + s.length() + ")")
            .collect(Collectors.toList());
    }
    
    @DataProcessor(description = "Фильтрация пустых строк", priority = 2)
    public List<String> removeEmptyStrings(List<String> input) {
        return input.stream()
            .filter(s -> !s.trim().isEmpty())
            .collect(Collectors.toList());
    }
}
Шаг 4: Главный класс для тестирования
java
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Лабораторная работа №8: Аннотации, Stream API и многопоточность ===\n");
        
        DataManager manager = new DataManager();
        
        // Регистрация обработчиков
        manager.registerDataProcessor(new FilterProcessor());
        manager.registerDataProcessor(new TransformProcessor());
        manager.registerDataProcessor(new AggregateProcessor());
        manager.registerDataProcessor(new StatisticsProcessor());
        
        // Создание тестового файла с данными
        createTestFile("input.txt");
        
        try {
            // Загрузка данных
            manager.loadData("input.txt");
            
            // Обработка данных
            long startTime = System.currentTimeMillis();
            manager.processData();
            long endTime = System.currentTimeMillis();
            System.out.println("Время обработки: " + (endTime - startTime) + "