package HotelBookingSystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hotel {
    private String hotelId;
    private String hotelName;
    private String address;
    private String phoneNumber;
    private List<Room> rooms;

    public Hotel(String hotelId, String hotelName, String address, String phoneNumber, List<Room> rooms) {
        this.hotelId = Objects.requireNonNull(hotelId, "Hotel ID cannot be null");
        this.hotelName = Objects.requireNonNull(hotelName, "Hotel name cannot be null");
        this.address = Objects.requireNonNull(address, "Address cannot be null");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "Phone number cannot be null");
        this.rooms = new ArrayList<>(Objects.requireNonNull(rooms, "Rooms list cannot be null"));
    }

    public synchronized String isRoomAvailable(RoomType roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        if (roomType == null) {
            throw new IllegalArgumentException("Room type cannot be null");
        }

        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }

        if (checkInDate.isAfter(checkOutDate) || checkInDate.isEqual(checkOutDate)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }

        for (Room room : rooms) {
            if (room.getRoomType() == roomType) {
                String availableRoomId = room.isAvailable(checkInDate, checkOutDate);
                if (availableRoomId != null) {
                    return availableRoomId;
                }
            }
        }
        return null;
    }

    public synchronized int getNoOfSingleRoomsAvailable(LocalDate checkInDate, LocalDate checkOutDate) {

        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }
        if (checkInDate.isAfter(checkOutDate) || checkInDate.isEqual(checkOutDate)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }

        int count = 0;
        for (Room room : rooms) {
            if (room.getRoomType() == RoomType.SINGLE && room.isAvailable(checkInDate, checkOutDate) != null) {
                count++;
            }
        }
        return count;
    }

    public synchronized int getNoOfDoubleRoomsAvailable(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }
        if (checkInDate.isAfter(checkOutDate) || checkInDate.isEqual(checkOutDate)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }

        int count = 0;
        for (Room room : rooms) {
            if (room.getRoomType() == RoomType.DOUBLE && room.isAvailable(checkInDate, checkOutDate) != null) {
                count++;
            }
        }
        return count;
    }

    public synchronized int getNoOfSuiteRoomsAvailable(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }
        if (checkInDate.isAfter(checkOutDate) || checkInDate.isEqual(checkOutDate)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        int count = 0;
        for (Room room : rooms) {
            if (room.getRoomType() == RoomType.SUITE && room.isAvailable(checkInDate, checkOutDate) != null) {
                count++;
            }
        }
        return count;
    }

    public String getHotelId() {
        return hotelId;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public String getHotelName() {
        return hotelName;
    }
}
