import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface DataProcessor {
    String description() default "";
}

class MyProcessors {

    @DataProcessor(description = "Фильтрация: оставляем только четные числа")
    public List<Integer> filterEvens(List<Integer> data) {
        return data.stream().filter(n -> n % 2 == 0).collect(Collectors.toList());
    }

    @DataProcessor(description = "Трансформация: умножаем каждое число на 10")
    public List<Integer> multiplyByTen(List<Integer> data) {
        return data.stream().map(n -> n * 10).collect(Collectors.toList());
    }

    @DataProcessor(description = "Агрегация: считаем сумму всех чисел")
    public List<Integer> sumAll(List<Integer> data) {
        int sum = data.stream().mapToInt(Integer::intValue).sum();
        return Arrays.asList(sum);
    }
}

class DataManager {
    private Object processorObject;
    private List<Integer> sharedData = new ArrayList<>();

    public void registerDataProcessor(Object processor) {
        this.processorObject = processor;
        System.out.println("Зарегистрирован обработчик: " + processor.getClass().getSimpleName());
    }

    public void loadData(String source) {
        sharedData = Arrays.asList(5, 12, 3, 8, 20, 7);
        System.out.println("Загружены исходные данные: " + sharedData);
    }

    public void processData() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        Method[] methods = processorObject.getClass().getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(DataProcessor.class)) {
                DataProcessor annotation = method.getAnnotation(DataProcessor.class);

                pool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Object result = method.invoke(processorObject, sharedData);
                            System.out.println("[" + annotation.description() + "] -> Результат: " + result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);
    }

    public void saveData(String destination) {
        System.out.println("Результаты обработки успешно сохранены в источник: " + destination);
    }
}

public class Lab8Main {
    public static void main(String[] args) throws Exception {
        System.out.println("=== ЛАБОРАТОРНАЯ РАБОТА №8 ===");
        
        DataManager manager = new DataManager();
        
        manager.registerDataProcessor(new MyProcessors());
        manager.loadData("input_source");
        manager.processData();
        manager.saveData("output_destination");
    }
}