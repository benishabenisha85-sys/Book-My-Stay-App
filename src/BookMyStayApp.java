import java.util.*;

/**
 * Use Case 6 – Reservation Confirmation & Room Allocation
 * Book My Stay App
 * Demonstrates room allocation while preventing double booking
 *
 * @author Benisha
 * @version 6.0
 */

// -------------------- RESERVATION CLASS --------------------

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// -------------------- INVENTORY SERVICE --------------------

class InventoryService {

    private HashMap<String, Integer> inventory;

    public InventoryService() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

// -------------------- BOOKING SERVICE --------------------

class BookingService {

    private Queue<Reservation> requestQueue;
    private HashMap<String, Set<String>> allocatedRooms;

    public BookingService() {
        requestQueue = new LinkedList<>();
        allocatedRooms = new HashMap<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        requestQueue.add(reservation);
    }

    // Process booking requests
    public void processRequests(InventoryService inventory) {

        while (!requestQueue.isEmpty()) {

            Reservation reservation = requestQueue.poll();
            String roomType = reservation.getRoomType();

            System.out.println("\nProcessing request for: " + reservation.getGuestName());

            if (inventory.getAvailability(roomType) > 0) {

                // Generate unique room ID
                String roomId = roomType.substring(0,2).toUpperCase() + "_" + UUID.randomUUID().toString().substring(0,5);

                allocatedRooms.putIfAbsent(roomType, new HashSet<>());

                Set<String> rooms = allocatedRooms.get(roomType);

                if (!rooms.contains(roomId)) {

                    rooms.add(roomId);

                    // decrease inventory
                    inventory.decreaseRoom(roomType);

                    System.out.println("Reservation Confirmed!");
                    System.out.println("Guest: " + reservation.getGuestName());
                    System.out.println("Room Type: " + roomType);
                    System.out.println("Room ID: " + roomId);
                }

            } else {

                System.out.println("Reservation Failed - No rooms available for " + roomType);
            }
        }
    }
}

// -------------------- MAIN CLASS --------------------

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Room Allocation");
        System.out.println("Version 6.0");
        System.out.println("=================================");

        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService();

        // Booking requests
        bookingService.addRequest(new Reservation("Alice", "Single Room"));
        bookingService.addRequest(new Reservation("Bob", "Double Room"));
        bookingService.addRequest(new Reservation("Charlie", "Single Room"));
        bookingService.addRequest(new Reservation("David", "Suite Room"));

        // Process requests
        bookingService.processRequests(inventory);

        // Show remaining inventory
        inventory.displayInventory();
    }
}