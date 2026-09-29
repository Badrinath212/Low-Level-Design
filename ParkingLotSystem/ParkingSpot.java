import java.util.Objects;

public final class ParkingSpot {
    private final String spotID;
    private final String floorID;
    private final VehicleType vehicleType;
    private String activeTicketID;

    public ParkingSpot(String spotID, String floorID, VehicleType vehicleType) {
        if (spotID == null || spotID.isBlank()) {
            throw new IllegalArgumentException("Spot ID cannot be blank");
        }
        if (floorID == null || floorID.isBlank()) {
            throw new IllegalArgumentException("Floor ID cannot be blank");
        }
        this.spotID = spotID;
        this.floorID = floorID;
        this.vehicleType = Objects.requireNonNull(vehicleType, "Vehicle type cannot be null");
    }

    public String getSpotID() {
        return spotID;
    }

    public String getFloorID() {
        return floorID;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public synchronized boolean isAvailable() {
        return activeTicketID == null;
    }

    synchronized boolean reserve(String ticketID) {
        if (ticketID == null || ticketID.isBlank()) {
            throw new IllegalArgumentException("Ticket ID cannot be blank");
        }
        if (activeTicketID != null) {
            return false;
        }
        activeTicketID = ticketID;
        return true;
    }

    synchronized void release(String ticketID) {
        if (!Objects.equals(activeTicketID, ticketID)) {
            throw new IllegalStateException("Ticket does not own this parking spot");
        }
        activeTicketID = null;
    }

    @Override
    public String toString() {
        return "Floor: " + floorID + ", Spot: " + spotID + ", Type: " + vehicleType
                + ", Status: " + (isAvailable() ? "AVAILABLE" : "OCCUPIED");
    }
}