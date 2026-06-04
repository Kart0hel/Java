import java.util.Scanner;

public class IPAddressValidator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите IP-адрес:");
        String ip = scanner.nextLine();

        try {
            if (!ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
                System.out.println("IP-адрес некорректный.");
            } else {
                String[] parts = ip.split("\\.");
                boolean correct = true;

                for (String part : parts) {
                    if (part.length() > 1 && part.startsWith("0")) {
                        correct = false;
                        break;
                    }
                    int number = Integer.parseInt(part);
                    if (number < 0 || number > 255) {
                        correct = false;
                        break;
                    }
                }

                if (correct) {
                    System.out.println("IP-адрес корректный.");
                } else {
                    System.out.println("IP-адрес некорректный.");
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }
}