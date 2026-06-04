# Лабораторная работа №2: Наследование и ООП (Велосипеды)

## Описание работы

Создана иерархия классов на примере велосипедов. Реализованы все принципы ООП: абстракция, инкапсуляция, наследование, полиморфизм.

## Иерархия классов

- **Абстрактный класс:** `Bicycle` (велосипед)
- **Дочерние классы (1-й уровень):** `MountainBike` (горный), `KidsBike` (детский), `BMX`

## Что реализовано

| Требование | Реализация |
|------------|------------|
| Абстрактный класс | `Bicycle` с абстрактным методом `ride()` |
| 3 поля в классе | `brand`, `wheelSize`, `weight` |
| 2 метода | `ride()` (абстрактный), `getInfo()` |
| Инкапсуляция | `private` поля, геттеры и сеттеры |
| Перегрузка методов | Несколько конструкторов |
| Переопределение | `ride()` в каждом дочернем классе |
| Конструкторы | По умолчанию и с параметрами |
| Статическая переменная | `totalBicycles` — счётчик созданных объектов |
| Ввод/вывод | Вывод информации о велосипедах |

## Код классов

### Bicycle.java (абстрактный)

```java
public abstract class Bicycle {
    private String brand;
    private double wheelSize;
    private double weight;
    private static int totalBicycles = 0;

    public Bicycle() {
        totalBicycles++;
    }

    public Bicycle(String brand, double wheelSize, double weight) {
        this.brand = brand;
        this.wheelSize = wheelSize;
        this.weight = weight;
        totalBicycles++;
    }

    // Геттеры и сеттеры
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public double getWheelSize() { return wheelSize; }
    public void setWheelSize(double wheelSize) { this.wheelSize = wheelSize; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public static int getTotalBicycles() { return totalBicycles; }

    public abstract void ride();
}