public class GenericStack<T> {
    private Object[] array; 
    private int size = 0;   

    public GenericStack(int capacity) {
        array = new Object[capacity];
    }

    public void push(T element) {
        if (size < array.length) {
            array[size] = element;
            size++; 
            System.out.println("Положили в стек: " + element);
        } else {
            System.out.println("Стек переполнен!");
        }
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (size > 0) {
            size--; 
            T element = (T) array[size]; 
            array[size] = null;          
            System.out.println("Забрали из стека: " + element);
            return element;
        } else {
            System.out.println("Стек пуст!");
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (size > 0) {
            return (T) array[size - 1]; 
        } else {
            System.out.println("Стек пуст, смотреть нечего!");
            return null;
        }
    }
}