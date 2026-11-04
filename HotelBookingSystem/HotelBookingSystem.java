package HotelBookingSystem;

import java.util.List;
import java.util.Map;

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

    

}
