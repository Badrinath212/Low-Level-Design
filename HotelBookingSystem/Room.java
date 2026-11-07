package HotelBookingSystem;

import java.time.LocalDate;
import java.util.List;

public class Room {
    private String roomId;
    private RoomType roomType;
    private String hotelId;
    private int roomNumber;
    private int floorNumber;
    private List<Booking> upcomingBookings;


    public RoomType getRoomType() {
        return roomType;
    }

    public synchronized String isAvailable(LocalDate checkInDate, LocalDate checkOutDate) {
        for (Booking booking : upcomingBookings) {
            if (booking.getCheckInDate().isBefore(checkOutDate) && booking.getCheckOutDate().isAfter(checkInDate)) {
                return null;
            } 
        }
        return roomId;
    }
}
