package BookMyShowSystem;

import java.time.Duration;
import java.time.LocalDateTime;

public class Seat {
    private String seatId;
    private int seatNo;
    private SeatType seatType;
    private Character rowCharacter;
    private BookingStatus bookingStatus;
    private long ttlNanos;

    public Seat(String seatId, int seatNo, SeatType seatType, Character rowCharacter, BookingStatus bookingStatus) {
        this.seatId = seatId;
        this.seatNo = seatNo;
        this.seatType = seatType;
        this.rowCharacter = rowCharacter;
        this.bookingStatus = bookingStatus;
    }

    public String getSeatID() {
        return seatId;
    }

    public void setTtl() {
       ttlNanos = Duration.ofSeconds(600).toNanos();
    }


    public SeatType getSeatType() {
        return seatType;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public Character getRowCharacter() {
        return rowCharacter;
    }

    public synchronized BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public synchronized void setBookingStatus(BookingStatus status) {
        bookingStatus = status;
    }

    public boolean isExpired() {
        return (System.nanoTime() - ttlNanos) > 0;
    }
}
