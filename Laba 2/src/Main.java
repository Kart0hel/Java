public class Main {
    public static void main(String[] args) {
        Bicycle mountain = new MountainBicycle("Trek", 13.5, 27, 24, true, "Mountain");
        Bicycle child = new ChildrensBicycle("Puky", 8.0, 16, 5, "Blue", true);
        Bicycle bmx = new BMX("GT", 11.0, 20, 1, false, "Street", 21.0, true, "Pro");

        Bicycle[] bikes = {mountain, child, bmx};
        for (Bicycle b : bikes) {
            b.printInfo();
            b.ride();           
            b.adjustSeat();
            System.out.println("-------------------");
        }

        System.out.println("Всего создано велосипедов: " + Bicycle.getObjectCount());

        mountain.setParameters("Specialized", 14.2);
        mountain.printInfo();
    }
}