import java.util.*;

// Custom Exception
class CancellationException extends Exception {
    public CancellationException(String message) {
        super(message);
    }
}

// Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private boolean isCancelled;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.isCancelled = false;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void cancel() {
        this.isCancelled = true;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room Type: " + roomType +
                ", Status: " + (isCancelled ? "Cancelled" : "Confirmed");
    }
}

// Inventory Manager
class InventoryManager {
    private Map<String, Integer> inventory;

    public InventoryManager() {
        inventory = new HashMap<>();
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }

    public void bookRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void releaseRoom(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public void displayInventory() {
        System.out.println("Inventory: " + inventory);
    }
}

// Booking History
class BookingHistory {
    private Map<String, Reservation> reservations = new HashMap<>();

    public void addReservation(Reservation r) {
        reservations.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return reservations.get(id);
    }

    public Collection<Reservation> getAll() {
        return reservations.values();
    }
}

// Cancellation Service (Core Logic)
class CancellationService {
    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingHistory history,
                              InventoryManager inventory)
            throws CancellationException {

        Reservation r = history.getReservation(reservationId);

        // Validation
        if (r == null) {
            throw new CancellationException("Reservation does not exist.");
        }

        if (r.isCancelled()) {
            throw new CancellationException("Reservation already cancelled.");
        }

        // Step 1: Record rollback (roomType used as identifier)
        rollbackStack.push(r.getRoomType());

        // Step 2: Restore inventory
        inventory.releaseRoom(r.getRoomType());

        // Step 3: Mark reservation cancelled
        r.cancel();

        System.out.println("Cancellation successful for ID: " + reservationId);
    }

    public void showRollbackStack() {
        System.out.println("Rollback Stack: " + rollbackStack);
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        InventoryManager inventory = new InventoryManager();
        BookingHistory history = new BookingHistory();
        CancellationService cancelService = new CancellationService();

        // Create bookings
        Reservation r1 = new Reservation("RES301", "Amit", "Deluxe");
        Reservation r2 = new Reservation("RES302", "Neha", "Suite");

        // Simulate booking (reduce inventory)
        inventory.bookRoom("Deluxe");
        inventory.bookRoom("Suite");

        history.addReservation(r1);
        history.addReservation(r2);

        inventory.displayInventory();

        // Test cancellations
        String[] cancelRequests = {"RES301", "RES999", "RES301"};

        for (String id : cancelRequests) {
            try {
                System.out.println("\nProcessing cancellation for: " + id);
                cancelService.cancelBooking(id, history, inventory);
            } catch (CancellationException e) {
                System.out.println("Cancellation Failed: " + e.getMessage());
            }
        }

        // Final State
        System.out.println("\nFinal Reservations:");
        for (Reservation r : history.getAll()) {
            System.out.println(r);
        }

        System.out.println();
        inventory.displayInventory();
        cancelService.showRollbackStack();
    }
}