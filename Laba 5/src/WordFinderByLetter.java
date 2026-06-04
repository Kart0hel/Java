import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordFinderByLetter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите текст:");
        String text = scanner.nextLine();

        System.out.println("Введите букву:");
        String letter = scanner.nextLine();

        try {
            Pattern pattern = Pattern.compile("\\b\\p{L}+\\b");
            Matcher matcher = pattern.matcher(text);

            String targetLetter = letter.toLowerCase();
            boolean found = false;

            while (matcher.find()) {
                String word = matcher.group();
                if (word.toLowerCase().startsWith(targetLetter)) {
                    System.out.println(word);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Слова не найдены.");
            }

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }
}