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
}
