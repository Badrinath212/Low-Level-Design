package HotelBookingSystem;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Room {
    private String roomId;
    private RoomType roomType;
    private String hotelId;
    private int roomNumber;
    private int floorNumber;
    private List<Booking> upcomingBookings;

    public Room(String roomId, RoomType roomType, String hotelId, int roomNumber, int floorNumber, List<Booking> upcomingBookings) {
        this.roomId = Objects.requireNonNull(roomId, "Room ID cannot be null");
        this.roomType = Objects.requireNonNull(roomType, "Room type cannot be null");
        this.hotelId = Objects.requireNonNull(hotelId, "Hotel ID cannot be null");
        this.roomNumber = roomNumber;
        this.floorNumber = floorNumber;
        this.upcomingBookings = Objects.requireNonNull(upcomingBookings, "Upcoming bookings list cannot be null");
    }


    public RoomType getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public void addBooking(Booking booking) {
        upcomingBookings.add(booking);
    }

    public void removeBooking(Booking booking) {
        upcomingBookings.remove(booking);
    }

    public synchronized String isAvailable(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }
        if (checkInDate.isAfter(checkOutDate) || checkInDate.isEqual(checkOutDate)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        for (Booking booking : upcomingBookings) {
            if (booking.getCheckInDate().isBefore(checkOutDate) && booking.getCheckOutDate().isAfter(checkInDate)) {
                return null;
            } 
        }
        return roomId;
    }
}
