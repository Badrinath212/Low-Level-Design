package BookMyShowSystem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Show {
    private String showID;
    private Movie movie;
    private LocalDate date;
    private Booking booking;
    private LocalTime time;
    private List<List<Seat>> seats;
    private List<Seat> lockedSeats;


    public Movie getMovie() {
        return movie;
    }

    public synchronized List<List<Seat>> getSeats() {
        return Collections.unmodifiableList(new ArrayList<>(seats));
    }

    public String getShowID() {
        return showID;
    }

    public synchronized void addToLockedSeats(Seat seat) {
        lockedSeats.add(seat);
    }

    public synchronized void addBooking(Ticket ticket) {
        booking.addBooking(ticket);
    }

    public synchronized void removeLockForSeat(Seat seat) {
        lockedSeats.remove(seat);
    }

}
