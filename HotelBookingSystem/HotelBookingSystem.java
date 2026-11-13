package HotelBookingSystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class HotelBookingSystem {
    private List<Hotel> hotels;
    private List<User> users;

    public HotelBookingSystem(List<Hotel> hotels, List<User> users) {
        Objects.requireNonNull(hotels, "Hotels cannot be null");
        if (hotels.isEmpty()) {
            throw new IllegalArgumentException("Hotels list cannot be empty");
        }
        Objects.requireNonNull(users, "Users cannot be null");
        if (users.isEmpty()) {
            throw new IllegalArgumentException("Users list cannot be empty");
        }
        this.hotels = new ArrayList<>(hotels);
        this.users = new ArrayList<>(users);
    }

    public static void main(String[] args) {
        List<Hotel> hotels = List.of(
            new Hotel(UUID.randomUUID().toString(), "Hotel A", "123 Main St", "123-456-7890", List.of(
                new Room(UUID.randomUUID().toString(), RoomType.SINGLE, "Hotel A", 101, 1, new ArrayList<>()),
                new Room(UUID.randomUUID().toString(), RoomType.DOUBLE, "Hotel A", 102, 1, new ArrayList<>())
            )),
            new Hotel(UUID.randomUUID().toString(), "Hotel B", "456 Elm St", "987-654-3210", List.of(
                new Room(UUID.randomUUID().toString(), RoomType.SINGLE, "Hotel B", 201, 2, new ArrayList<>()),
                new Room(UUID.randomUUID().toString(), RoomType.DOUBLE, "Hotel B", 202, 2, new ArrayList<>())
            ))
        );

        List<User> users = List.of(
            new User(UUID.randomUUID().toString(), "John Doe", "john.doe@example.com", "555-1234", new ArrayList<>()),
            new User(UUID.randomUUID().toString(), "Jane Smith", "jane.smith@example.com", "555-5678", new ArrayList<>())
        );

        HotelBookingSystem bookingSystem = new HotelBookingSystem(hotels, users);

        List<AvailabilityResponseDTO> availability = bookingSystem.checkAvailability(LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 12));
        for (AvailabilityResponseDTO response : availability) { 
            System.out.println(response.toString());
        }

        boolean isAvailable = bookingSystem.checkAvailability(hotels.get(0).getHotelId(), RoomType.SINGLE, LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 12));
        System.out.println("Is room available: " + isAvailable);

        boolean bookingSuccess = bookingSystem.bookRoom(users.get(0).getUserId(), hotels.get(0).getHotelId(), RoomType.SINGLE, LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 12));
        System.out.println("Booking successful: " + bookingSuccess);
    }

    public void addHotel(Hotel hotel) {
        hotels.add(hotel);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public synchronized List<AvailabilityResponseDTO> checkAvailability(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }
        if (checkInDate.isAfter(checkOutDate) || checkInDate.isEqual(checkOutDate)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        List<AvailabilityResponseDTO> availabilityList = new ArrayList<>();
        for (Hotel hotel : hotels) {
            int noOfSingleRoomsAvailable = hotel.getNoOfSingleRoomsAvailable(checkInDate, checkOutDate);
            int noOfDoubleRoomsAvailable = hotel.getNoOfDoubleRoomsAvailable(checkInDate, checkOutDate);
            int noOfSuiteRoomsAvailable = hotel.getNoOfSuiteRoomsAvailable(checkInDate, checkOutDate);

            availabilityList.add(new AvailabilityResponseDTO(hotel.getHotelId(), hotel.getHotelName(), noOfSingleRoomsAvailable, noOfDoubleRoomsAvailable, noOfSuiteRoomsAvailable));
        }

        return availabilityList;
    }

    public synchronized boolean checkAvailability(String hotelId, RoomType roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        System.out.println("check availability called" + " Hotel ID: " + hotelId);
        for (Hotel hotel : hotels) {
            System.out.println("Hotel ID: " + hotel.getHotelId());
            if (hotel.getHotelId().equals(hotelId)) {
                System.out.println("reached here");
                if (hotel.isRoomAvailable(roomType, checkInDate, checkOutDate) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized boolean isValidUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }
    
    public synchronized boolean bookRoom(String userId, String hotelId, RoomType roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        User user = findUser(userId);
        if (user == null) {
            return false;
        }

        for (Hotel hotel : hotels) {
            if (hotel.getHotelId().equals(hotelId)) {
                String availableRoomId = hotel.isRoomAvailable(roomType, checkInDate, checkOutDate);
                System.out.println("Available Room ID: " + availableRoomId);
                if (availableRoomId != null) {
                    String bookingId = UUID.randomUUID().toString();
                    Booking booking = new Booking(bookingId, userId, hotelId, availableRoomId, checkInDate, checkOutDate);
                    user.addBooking(booking);
                    for (Room room : hotel.getRooms()) {
                        if (room.getRoomId().equals(availableRoomId)) {
                            room.addBooking(booking);
                            break;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized boolean cancelBooking(String userId, String bookingId) {
        User user = findUser(userId);
        if (user == null) {
            return false;
        }

        for (Booking booking : user.getBookings()) {
            if (booking.getBookingId().equals(bookingId) && booking.cancel()) {
                return true;
            }
        }
        return false;
    }

    private User findUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

}
