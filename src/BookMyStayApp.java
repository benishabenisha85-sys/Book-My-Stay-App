import java.io.*;
import java.util.*;

// Reservation (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

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
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// Wrapper class to persist full system state
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    // Save state
    public static void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("System state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    // Load state
    public static SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state. Starting with safe defaults.");
        }

        // Default state if file missing/corrupt
        Map<String, Integer> defaultInventory = new HashMap<>();
        defaultInventory.put("Standard", 2);
        defaultInventory.put("Deluxe", 1);
        defaultInventory.put("Suite", 1);

        return new SystemState(defaultInventory, new ArrayList<>());
    }
}

// Main Class
public class BookMyStayApp{

    public static void main(String[] args) {

        // STEP 1: Load previous state (Recovery)
        SystemState state = PersistenceService.load();

        Map<String, Integer> inventory = state.inventory;
        List<Reservation> bookings = state.bookings;

        // Display recovered state
        System.out.println("\nRecovered Inventory: " + inventory);
        System.out.println("Recovered Bookings: " + bookings);

        // STEP 2: Simulate new booking
        System.out.println("\nAdding new booking...");

        if (inventory.get("Deluxe") > 0) {
            Reservation r = new Reservation("RES401", "Amit", "Deluxe");
            bookings.add(r);
            inventory.put("Deluxe", inventory.get("Deluxe") - 1);

            System.out.println("Booking added: " + r);
        } else {
            System.out.println("No Deluxe rooms available.");
        }

        // STEP 3: Save state before shutdown
        PersistenceService.save(new SystemState(inventory, bookings));

        // Final display
        System.out.println("\nFinal Inventory: " + inventory);
        System.out.println("Final Bookings: " + bookings);
    }
}