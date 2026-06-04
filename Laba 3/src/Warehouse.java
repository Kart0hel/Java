import java.util.HashMap;

class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return name + " | Цена: " + price + " | Остаток: " + quantity;
    }
}

public class Warehouse {
    public static void main(String[] args) {
        HashMap<String, Product> warehouse = new HashMap<>();

        warehouse.put("123456789", new Product("Ноутбук ASUS", 75000, 10));
        warehouse.put("987654321", new Product("Мышка Logitech", 1500, 50));
        warehouse.put("555555555", new Product("Клавиатура", 2500, 30));

        System.out.println("Поиск по штрихкоду 123456789:");
        System.out.println(warehouse.get("123456789"));

        warehouse.remove("987654321");
        System.out.println("\nПосле удаления мышки:");

        warehouse.forEach((barcode, product) ->
            System.out.println("Штрихкод: " + barcode + " → " + product)
        );

        System.out.println("\nВсего товаров на складе: " + warehouse.size());
    }
}