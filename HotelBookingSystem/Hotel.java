package HotelBookingSystem;

import java.time.LocalDate;
import java.util.List;

public class Hotel {
    private String hotelId;
    private String hotelName;
    private String address;
    private String phoneNumber;
    private List<Room> rooms;

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
}
