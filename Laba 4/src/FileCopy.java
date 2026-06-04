import java.io.*;

public class FileCopy {

    public static void main(String[] args) {

        copyFile("input.txt", "output.txt");
    }

    public static void copyFile(String fromFile, String toFile) {

        try {
            FileReader fr = new FileReader(fromFile);
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw = new FileWriter(toFile);
            BufferedWriter bw = new BufferedWriter(fw);

            String line;
            int count = 0;

            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
                count++;
            }

            System.out.println("Файл успешно скопирован!");
            System.out.println("Скопировано строк: " + count);

            br.close();
            bw.close();

        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: файл не найден! " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка при чтении или записи файла: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Неизвестная ошибка: " + e.getMessage());
        }
    }
}