package BookMyShowSystem;

import java.util.List;

public class Booking {
    private String bookingID;
    private List<Ticket> bookings;

    public Booking(String bookingID, List<Ticket> bookings) {
        this.bookingID = bookingID;
        this.bookings = bookings;
    }

    public synchronized void addBooking(Ticket ticket) {
        bookings.add(ticket);
    }
}
