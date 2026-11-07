package HotelBookingSystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HotelBookingSystem {
    private List<Hotel> hotels;
    private List<User> users;
    private Map<String, List<Booking>> bookingsByUser;

    public void addHotel(Hotel hotel) {
        hotels.add(hotel);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public synchronized boolean checkAvailability(String hotelId, RoomType roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        for (Hotel hotel : hotels) {
            if (hotel.getHotelId().equals(hotelId)) {
                if (hotel.isRoomAvailable(roomType, checkInDate, checkOutDate) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValidUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }
    
    public synchronized boolean bookRoom(String userId, String hotelId, RoomType roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        if (!isValidUser(userId)) {
            return false;
        }

        for (Hotel hotel : hotels) {
            if (hotel.getHotelId().equals(hotelId)) {
                String availableRoomId = hotel.isRoomAvailable(roomType, checkInDate, checkOutDate);
                if (availableRoomId != null) {
                    String bookingId = UUID.randomUUID().toString();
                    Booking booking = new Booking(bookingId, userId, hotelId, availableRoomId, checkInDate, checkOutDate);
                    bookingsByUser.putIfAbsent(hotelId, new ArrayList<>()).add(booking);
                    return true;
                }
            }
        }
        return false;
    }

}
