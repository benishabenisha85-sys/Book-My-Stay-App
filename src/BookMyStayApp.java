import java.util.*;

// Custom Exception for Invalid Booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType;
    }
}

// Inventory Manager
class InventoryManager {
    private Map<String, Integer> roomInventory;

    public InventoryManager() {
        roomInventory = new HashMap<>();
        roomInventory.put("Standard", 2);
        roomInventory.put("Deluxe", 1);
        roomInventory.put("Suite", 1);
    }

    public boolean isRoomTypeValid(String roomType) {
        return roomInventory.containsKey(roomType);
    }

    public int getAvailableRooms(String roomType) {
        return roomInventory.getOrDefault(roomType, 0);
    }

    public void bookRoom(String roomType) throws InvalidBookingException {
        int available = getAvailableRooms(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }

        roomInventory.put(roomType, available - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory: " + roomInventory);
    }
}

// Validator Class (Fail-Fast)
class BookingValidator {

    public static void validate(String reservationId, String guestName, String roomType,
                                InventoryManager inventory) throws InvalidBookingException {

        if (reservationId == null || reservationId.isEmpty()) {
            throw new InvalidBookingException("Reservation ID cannot be empty.");
        }

        if (guestName == null || guestName.isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (!inventory.isRoomTypeValid(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (inventory.getAvailableRooms(roomType) <= 0) {
            throw new InvalidBookingException("Room not available for type: " + roomType);
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        InventoryManager inventory = new InventoryManager();

        // Test cases (valid + invalid)
        String[][] testBookings = {
                {"RES201", "Amit", "Deluxe"},     // valid
                {"", "Neha", "Suite"},            // invalid ID
                {"RES203", "", "Standard"},       // invalid name
                {"RES204", "Rahul", "Premium"},   // invalid room type
                {"RES205", "Sara", "Suite"},      // valid
                {"RES206", "John", "Suite"}       // no availability
        };

        for (String[] booking : testBookings) {
            try {
                String reservationId = booking[0];
                String guestName = booking[1];
                String roomType = booking[2];

                System.out.println("\nProcessing Booking...");

                // Step 1: Validate (Fail Fast)
                BookingValidator.validate(reservationId, guestName, roomType, inventory);

                // Step 2: Allocate Room
                inventory.bookRoom(roomType);

                // Step 3: Create Reservation
                Reservation reservation = new Reservation(reservationId, guestName, roomType);

                System.out.println("Booking Successful: " + reservation);

            } catch (InvalidBookingException e) {
                // Graceful failure
                System.out.println("Booking Failed: " + e.getMessage());
            }
        }

        // Final Inventory State
        inventory.displayInventory();
    }
}