public class ArrayAverage {

    public static void main(String[] args) {

        // Тестовые данные
        String[] numbers1 = {"10", "20", "30", "40", "50"};
        String[] numbers2 = {"5", "15", "abc", "25"};   
        String[] numbers3 = {"1", "2", "3"};

        System.out.println("Тест 1:");
        findAverage(numbers1);

        System.out.println("\nТест 2:");
        findAverage(numbers2);

        System.out.println("\nТест 3:");
        findAverage(numbers3);
    }

    public static void findAverage(String[] arr) {
        
        double sum = 0;
        int count = 0;

        try {
            for (int i = 0; i < arr.length; i++) {
                double num = Double.parseDouble(arr[i]);  // может быть ошибка
                sum = sum + num;
                count = count + 1;
            }

            double average = sum / count;
            System.out.println("Среднее арифметическое = " + average);

        } 
        catch (NumberFormatException e) {
            System.out.println("Ошибка! В массиве есть не число: " + e.getMessage());
        } 
        catch (Exception e) {
            System.out.println("Какая-то другая ошибка произошла: " + e.getMessage());
        } 
        finally {
            System.out.println("Блок finally выполнен.");
        }
    }
}