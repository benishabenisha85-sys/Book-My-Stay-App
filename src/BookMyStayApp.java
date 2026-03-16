import java.util.LinkedList;
import java.util.Queue;

/**
 * Use Case 5 – Booking Request Queue
 * Book My Stay App
 * Demonstrates First-Come-First-Served booking request handling
 *
 * @author Benisha
 * @version 5.0
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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

// -------------------- BOOKING REQUEST QUEUE --------------------

class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // Display all requests in queue
    public void displayRequests() {

        System.out.println("\nCurrent Booking Request Queue:");

        for (Reservation r : queue) {
            r.displayReservation();
        }
    }
}

// -------------------- MAIN APPLICATION --------------------

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Book My Stay App - Booking Queue");
        System.out.println("Version 5.0");
        System.out.println("=================================");

        // Create booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guest booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Add requests to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queue
        bookingQueue.displayRequests();
    }
}