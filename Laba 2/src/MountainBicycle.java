public class MountainBicycle extends Bicycle {
    private int numGears;           
    private boolean hasSuspension;  
    private String terrainType;     

    public MountainBicycle(String brand, double weight, int wheelSize,
                           int numGears, boolean hasSuspension, String terrainType) {
        super(brand, weight, wheelSize);
        this.numGears = numGears;
        this.hasSuspension = hasSuspension;
        this.terrainType = terrainType;
    }

    public MountainBicycle() {
        super();
        this.numGears = 21;
        this.hasSuspension = true;
        this.terrainType = "Off-road";
    }

    public int getNumGears() { return numGears; }
    public void setNumGears(int numGears) { this.numGears = numGears; }
    public boolean isHasSuspension() { return hasSuspension; }
    public void setHasSuspension(boolean hasSuspension) { this.hasSuspension = hasSuspension; }
    public String getTerrainType() { return terrainType; }
    public void setTerrainType(String terrainType) { this.terrainType = terrainType; }

    @Override
    public void adjustSeat() {
        System.out.println("Настраиваем седло для горного велосипеда");
    }

    @Override
    public void ride() {
        System.out.println("Едем по пересечённой местности на горном велосипеде!");
    }

    public void climbHill() {  
        System.out.println("Подъём в гору на " + getNumGears() + " передаче");
    }
}