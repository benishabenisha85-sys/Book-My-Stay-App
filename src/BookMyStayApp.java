import java.util.*;

// Basic Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double baseCost;

    public Reservation(String reservationId, String guestName, String roomType, double baseCost) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.baseCost = baseCost;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getBaseCost() {
        return baseCost;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Cost: ₹" + baseCost;
    }
}

// Booking History (stores confirmed reservations)
class BookingHistory {
    private List<Reservation> confirmedBookings;

    public BookingHistory() {
        confirmedBookings = new ArrayList<>();
    }

    // Add confirmed reservation
    public void addReservation(Reservation reservation) {
        confirmedBookings.add(reservation);
    }

    // Retrieve all reservations (read-only copy)
    public List<Reservation> getAllReservations() {
        return new ArrayList<>(confirmedBookings);
    }
}

// Reporting Service (separate from storage)
class BookingReportService {

    // Print all bookings
    public void printAllBookings(List<Reservation> reservations) {
        System.out.println("\n--- Booking History ---");
        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummary(List<Reservation> reservations) {
        System.out.println("\n--- Booking Summary Report ---");

        int totalBookings = reservations.size();
        double totalRevenue = 0;

        for (Reservation r : reservations) {
            totalRevenue += r.getBaseCost();
        }

        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings
        Reservation r1 = new Reservation("RES101", "Amit", "Deluxe", 3000);
        Reservation r2 = new Reservation("RES102", "Neha", "Suite", 5000);
        Reservation r3 = new Reservation("RES103", "Rahul", "Standard", 2000);

        // Add to history (only after confirmation)
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin retrieves booking history
        List<Reservation> storedBookings = history.getAllReservations();

        // Generate reports
        reportService.printAllBookings(storedBookings);
        reportService.generateSummary(storedBookings);
    }
}