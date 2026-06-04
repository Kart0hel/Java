public class MainDemo {

    public static void main(String[] args) {

        CustomStack stack = new CustomStack();

        stack.push("Java");
        stack.push("Python");
        stack.push("C++");

        try {
            System.out.println("Верхний элемент: " + stack.peek());
            System.out.println("Извлекли: " + stack.pop());
            System.out.println("Извлекли: " + stack.pop());
            System.out.println("Извлекли: " + stack.pop());
            System.out.println("Извлекли: " + stack.pop());  
        } 
        catch (CustomEmptyStackException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }
    }
}