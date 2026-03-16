abstract class Room {

    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Beds : " + beds);
        System.out.println("Size : " + size + " sq ft");
        System.out.println("Price : $" + price);
    }
}

class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 100);
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 180);
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 500, 300);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("Single Room");
        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable);

        System.out.println("\nDouble Room");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable);

        System.out.println("\nSuite Room");
        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}