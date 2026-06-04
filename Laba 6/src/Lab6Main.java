import java.io.File;
import java.util.*;

public class Lab6Main {
    public static void main(String[] args) throws Exception {
        System.out.println("=== ЗАДАНИЕ 1: ТОП-10 СЛОВ ===");
        runTask1();

        System.out.println("\n=== ЗАДАНИЕ 2: СТЕК ===");
        runTask2();

        System.out.println("\n=== ЗАДАНИЕ 3: УЧЕТ ПРОДАЖ (Вариант 5) ===");
        runTask3();
    }

    public static void runTask1() throws Exception {
        File file = new File("text.txt");
        if (!file.exists()) {
            java.io.PrintWriter pw = new java.io.PrintWriter(file);
            pw.println("java привет код привет java мир код java привет java");
            pw.close();
        }

        HashMap<String, Integer> map = new HashMap<>();
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String word = scanner.next().toLowerCase().replaceAll("[^a-zA-Zа-яА-Я]", "");
            
            if (!word.isEmpty()) {
                if (map.containsKey(word)) {
                    map.put(word, map.get(word) + 1);
                } else {
                    map.put(word, 1);
                }
            }
        }
        scanner.close();

        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());

        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });

        for (int i = 0; i < Math.min(10, list.size()); i++) {
            System.out.println(list.get(i).getKey() + " встретилось " + list.get(i).getValue() + " раз(а)");
        }
    }

    public static void runTask2() {
        GenericStack<String> myStack = new GenericStack<>(3);
        myStack.push("Книга 1");
        myStack.push("Книга 2");
        myStack.push("Книга 3");
        
        myStack.pop();
        myStack.pop();
    }

    public static void runTask3() {
        HashMap<Product, Integer> sales = new HashMap<>();

        Product phone = new Product("Телефон", 20000);
        Product Case = new Product("Чехол", 500);

        sales.put(phone, 3); // Продали 3 телефона
        sales.put(Case, 10); // Продали 10 чехлов

        double totalMoney = 0; // Переменная для подсчета общей выручки

        System.out.println("Отчет по продажам:");
        for (Map.Entry<Product, Integer> entry : sales.entrySet()) {
            Product p = entry.getKey();     // Получаем сам объект товара
            int count = entry.getValue();   // Получаем сколько штук продано
            
            double revenue = p.price * count; // Считаем деньги за этот товар
            totalMoney += revenue;            // Плюсуем в общую кассу

            System.out.println(p.name + " | Цена: " + p.price + " руб. | Продано: " + count + " шт. | Выручка: " + revenue + " руб.");
        }

        System.out.println("Общая выручка магазина: " + totalMoney + " руб.");
    }
}