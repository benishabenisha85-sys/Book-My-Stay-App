import java.util.HashMap;

/**
 * Use Case 4 – Room Search & Availability Check
 * Book My Stay App
 * Demonstrates read-only search using inventory
 *
 * @author Benisha
 * @version 4.1
 */

// -------------------- ROOM DOMAIN MODEL --------------------

abstract class Room {

    protected String roomType;
    protected double price;

    public Room(String roomType, double price) {
        this.roomType = roomType;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Price: $" + price);
    }
}

// -------------------- ROOM TYPES --------------------

class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 100);
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 180);
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 300);
    }
}

// -------------------- INVENTORY --------------------

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

// -------------------- SEARCH SERVICE --------------------

class SearchService {

    public static void searchRooms(RoomInventory inventory, Room[] rooms) {

        System.out.println("\nAvailable Rooms:");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            if (available > 0) {

                room.displayDetails();
                System.out.println("Available Rooms: " + available);
                System.out.println("----------------------------");
            }
        }
    }
}

// -------------------- MAIN CLASS --------------------

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Room Search");
        System.out.println("Version 4.1");
        System.out.println("=================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room objects
        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        // Perform search
        SearchService.searchRooms(inventory, rooms);
    }
}