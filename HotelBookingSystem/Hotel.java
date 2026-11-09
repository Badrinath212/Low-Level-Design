package HotelBookingSystem;

import java.time.LocalDate;
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
        this.rooms = Objects.requireNonNull(rooms, "Rooms list cannot be null");
    }

    public synchronized String isRoomAvailable(RoomType roomType, LocalDate checkInDate, LocalDate checkOutDate) {
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

    public String getHotelId() {
        return hotelId;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
