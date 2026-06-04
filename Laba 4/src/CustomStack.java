public class CustomStack {

    private String[] data;   
    private int top;

    public CustomStack() {
        data = new String[5];   
        top = -1;
    }

    public void push(String value) {
        if (top == data.length - 1) {
            System.out.println("Стек переполнен!");
            return;
        }
        top = top + 1;
        data[top] = value;
    }

    public String pop() throws CustomEmptyStackException {
        if (top == -1) {
            throw new CustomEmptyStackException("Стек пустой! Нельзя взять элемент.");
        }
        String value = data[top];
        top = top - 1;
        return value;
    }

    public String peek() throws CustomEmptyStackException {
        if (top == -1) {
            throw new CustomEmptyStackException("Стек пустой! Нельзя посмотреть верхний элемент.");
        }
        return data[top];
    }
}