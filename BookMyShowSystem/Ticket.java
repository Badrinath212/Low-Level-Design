package BookMyShowSystem;

import java.util.List;

public class Ticket {
    private String ticketID;
    private Show show;
    private List<Seat> seats;

    public Ticket(String ticketID, Show show, List<Seat> seats) {
        this.ticketID = ticketID;
        this.show = show;
        this.seats = seats;
    }
}
