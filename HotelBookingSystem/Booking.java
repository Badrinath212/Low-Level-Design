package HotelBookingSystem;

import java.time.LocalDate;
import java.util.Objects;

public class Booking {
    private String bookingId;
    private String userId;
    private String hotelId;
    private String roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BookingStatus bookingStatus;

    public Booking(String bookingId, String userId, String hotelId, String roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        this.bookingId = Objects.requireNonNull(bookingId, "Booking ID cannot be null");
        this.userId = Objects.requireNonNull(userId, "User ID cannot be null");
        this.hotelId = Objects.requireNonNull(hotelId, "Hotel ID cannot be null");
        this.roomId = Objects.requireNonNull(roomId, "Room ID cannot be null");
        this.checkInDate = Objects.requireNonNull(checkInDate, "Check-in date cannot be null");
        this.checkOutDate = Objects.requireNonNull(checkOutDate, "Check-out date cannot be null");
        if (checkInDate.isAfter(checkOutDate) || checkInDate.isEqual(checkOutDate)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        this.bookingStatus = BookingStatus.CONFIRMED;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getRoomId() {
        return roomId;
    }
}
