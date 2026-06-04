import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberFinder {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите текст:");
        String text = scanner.nextLine();

        try {
            Pattern pattern = Pattern.compile("-?\\d+(?:[.,]\\d+)?");
            Matcher matcher = pattern.matcher(text);

            boolean found = false;
            while (matcher.find()) {
                System.out.println(matcher.group());
                found = true;
            }

            if (!found) {
                System.out.println("Числа не найдены.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }
}