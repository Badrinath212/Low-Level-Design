package BookMyShowSystem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Show {
    private String showId;
    private Movie movie;
    private LocalDate date;
    private Booking booking;
    private LocalTime time;
    private List<List<Seat>> seats;

    public Movie getMovie() {
        return movie;
    }

    public synchronized List<List<Seat>> getSeats() {
        return Collections.unmodifiableList(new ArrayList<>(seats));
    }

}
