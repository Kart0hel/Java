public class BMX extends MountainBicycle {
    private double frameHeight;  
    private boolean hasPegs;     
    private String trickLevel;   

    public BMX(String brand, double weight, int wheelSize, int numGears,
               boolean hasSuspension, String terrainType,
               double frameHeight, boolean hasPegs, String trickLevel) {
        super(brand, weight, wheelSize, numGears, hasSuspension, terrainType);
        this.frameHeight = frameHeight;
        this.hasPegs = hasPegs;
        this.trickLevel = trickLevel;
    }

    public BMX() {
        super();
        this.frameHeight = 20.5;
        this.hasPegs = true;
        this.trickLevel = "Advanced";
    }

    public double getFrameHeight() { return frameHeight; }
    public void setFrameHeight(double frameHeight) { this.frameHeight = frameHeight; }
    public boolean isHasPegs() { return hasPegs; }
    public void setHasPegs(boolean hasPegs) { this.hasPegs = hasPegs; }
    public String getTrickLevel() { return trickLevel; }
    public void setTrickLevel(String trickLevel) { this.trickLevel = trickLevel; }

    @Override
    public void ride() {
        System.out.println("Выполняем трюки на BMX!");
    }

    public void performTrick() {
        System.out.println("Делаем " + trickLevel + " трюк на BMX");
    }
}