package HotelBookingSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class User {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private List<Booking> bookings;

    public User(String userId, String name, String email, String phoneNumber, List<Booking> bookings) {
        this.userId = Objects.requireNonNull(userId, "User ID cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.email = Objects.requireNonNull(email, "Email cannot be null");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "Phone number cannot be null");
        this.bookings = new ArrayList<>(Objects.requireNonNull(bookings, "Bookings list cannot be null"));
    }

    public String getUserId() {
        return userId;
    }

    public List<Booking> getBookings() {
        return Collections.unmodifiableList(bookings);
    }

    public void addBooking(Booking booking) {
        bookings.add(Objects.requireNonNull(booking, "Booking cannot be null"));
    }
}
