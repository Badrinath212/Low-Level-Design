package HotelBookingSystem;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HotelBookingSystemTest {
    private List<Hotel> hotels;
    private HotelBookingSystem bookingSystem;
    private List<User> users;

    @BeforeEach
    void setUp() {
        hotels = List.of(
            new Hotel("1", "Hotel A", "123 Main St", "123-456-7890", List.of(
                new Room("1", RoomType.SINGLE, "Hotel A", 101, 1, List.of()),
                new Room("2", RoomType.DOUBLE, "Hotel A", 102, 1, List.of())
            )),
            new Hotel("2", "Hotel B", "456 Elm St", "987-654-3210", List.of(
                new Room("3", RoomType.SINGLE, "Hotel B", 201, 2, List.of()),
                new Room("4", RoomType.DOUBLE, "Hotel B", 202, 2, List.of())
            ))
        );
        users = List.of(
            new User(UUID.randomUUID().toString(), "John Doe", "john.doe@example.com", "555-1234", new ArrayList<>()),
            new User(UUID.randomUUID().toString(), "Jane Smith", "jane.smith@example.com", "555-5678", new ArrayList<>())
        );
        bookingSystem = new HotelBookingSystem(hotels, users);
    }

    @Test
    void validateUserCheck() {
        boolean bookStatus = bookingSystem.bookRoom(
            "Badri",
            hotels.get(0).getHotelId(),
            RoomType.SINGLE,
            LocalDate.of(2026, 11, 10),
            LocalDate.of(2026, 11, 12)
        );
        assertFalse(bookStatus, "Booking should fail for non-existent user");
    }

    @Test
    void simulateBooking() {
        boolean bookStatus = bookingSystem.bookRoom(
            users.get(0).getUserId(),
            hotels.get(0).getHotelId(),
            RoomType.SINGLE,
            LocalDate.of(2026, 11, 10),
            LocalDate.of(2026, 11, 12)
        );
        assertTrue(bookStatus, "Booking should succeed for existing user");
    }
}
