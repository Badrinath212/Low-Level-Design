package HotelBookingSystem;

import java.time.LocalDate;

public class Booking {
    private String bookingId;
    private String userId;
    private String hotelId;
    private String roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BookingStatus bookingStatus;

    public Booking(String bookingId, String userId, String hotelId, String roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.hotelId = hotelId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingStatus = BookingStatus.CONFIRMED;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
}
