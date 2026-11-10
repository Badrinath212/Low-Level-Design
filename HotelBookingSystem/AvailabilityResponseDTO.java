package HotelBookingSystem;

public class AvailabilityResponseDTO {
    private String hotelId;
    private String hotelName;
    private int noOfSingleAvailableRooms;
    private int noOfDoubleAvailableRooms;
    private int noOfSuiteAvailableRooms;

    public AvailabilityResponseDTO(String hotelId, String hotelName, int noOfSingleAvailableRooms, int noOfDoubleAvailableRooms, int noOfSuiteAvailableRooms) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.noOfSingleAvailableRooms = noOfSingleAvailableRooms;
        this.noOfDoubleAvailableRooms = noOfDoubleAvailableRooms;
        this.noOfSuiteAvailableRooms = noOfSuiteAvailableRooms;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String toString() {
        return "AvailabilityResponseDTO{" +
                "hotelId='" + hotelId + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", noOfSingleAvailableRooms=" + noOfSingleAvailableRooms +
                ", noOfDoubleAvailableRooms=" + noOfDoubleAvailableRooms +
                ", noOfSuiteAvailableRooms=" + noOfSuiteAvailableRooms +
                '}';
    }
}
