public abstract class Bicycle {
    private String brand;
    private double weight;      
    private int wheelSize;      
    private static int objectCount = 0;  

    public Bicycle(String brand, double weight, int wheelSize) {
        this.brand = brand;
        this.weight = weight;
        this.wheelSize = wheelSize;
        objectCount++;
    }

    public Bicycle() {
        this("Unknown", 12.0, 26);
    }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public int getWheelSize() { return wheelSize; }
    public void setWheelSize(int wheelSize) { this.wheelSize = wheelSize; }

    public void setParameters(String brand) {
        this.brand = brand;
    }
    public void setParameters(String brand, double weight) {
        this.brand = brand;
        this.weight = weight;
    }

    public void ride() {
        System.out.println("Едем на велосипеде " + brand);
    }

    public abstract void adjustSeat();

    public void printInfo() {
        System.out.println("Велосипед: " + brand + ", вес: " + weight + " кг, колёса: " + wheelSize + "\"");
    }

    public static int getObjectCount() {
        return objectCount;
    }
}