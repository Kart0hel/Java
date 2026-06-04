import java.util.Scanner;

public class PasswordValidator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите пароль:");
        String password = scanner.nextLine();

        try {
            String regex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,16}$";

            if (password.matches(regex)) {
                System.out.println("Пароль корректный.");
            } else {
                System.out.println("Пароль некорректный.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }
}