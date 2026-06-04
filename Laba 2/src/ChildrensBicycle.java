public class ChildrensBicycle extends Bicycle {
    private int ageRange;      
    private String color;
    private boolean hasBell;

    public ChildrensBicycle(String brand, double weight, int wheelSize,
                            int ageRange, String color, boolean hasBell) {
        super(brand, weight, wheelSize);
        this.ageRange = ageRange;
        this.color = color;
        this.hasBell = hasBell;
    }

    public ChildrensBicycle() {
        super();
        this.ageRange = 3;
        this.color = "Red";
        this.hasBell = true;
    }

    public int getAgeRange() { return ageRange; }
    public void setAgeRange(int ageRange) { this.ageRange = ageRange; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public boolean isHasBell() { return hasBell; }
    public void setHasBell(boolean hasBell) { this.hasBell = hasBell; }

    @Override
    public void adjustSeat() {
        System.out.println("Настраиваем седло для детского велосипеда");
    }

    public void ringBell() {
        System.out.println("Звенит звонок!");
    }
}