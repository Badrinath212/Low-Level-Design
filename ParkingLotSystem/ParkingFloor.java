import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ParkingFloor {
    private final String floorID;
    private final List<ParkingSpot> spots;

    /**
     * Creates the default layout: up to three car spots, then up to three bike
     * spots, with any remaining capacity assigned to trucks.
     */
    public ParkingFloor(String floorID, int capacity) {
        this(floorID, defaultSpotCounts(capacity));
    }

    private ParkingFloor(String floorID, int[] spotCounts) {
        this(floorID, spotCounts[0], spotCounts[1], spotCounts[2]);
    }

    /** Creates a floor with an explicit number of spots for each vehicle type. */
    public ParkingFloor(String floorID, int carSpots, int bikeSpots, int truckSpots) {
        if (floorID == null || floorID.isBlank()) {
            throw new IllegalArgumentException("Floor ID cannot be blank");
        }
        if (carSpots < 0 || bikeSpots < 0 || truckSpots < 0) {
            throw new IllegalArgumentException("Spot counts cannot be negative");
        }
        if (carSpots + bikeSpots + truckSpots == 0) {
            throw new IllegalArgumentException("A floor must have at least one spot");
        }

        this.floorID = floorID;
        List<ParkingSpot> configuredSpots = new ArrayList<>();
        addSpots(configuredSpots, carSpots, VehicleType.CAR);
        addSpots(configuredSpots, bikeSpots, VehicleType.BIKE);
        addSpots(configuredSpots, truckSpots, VehicleType.TRUCK);
        this.spots = Collections.unmodifiableList(configuredSpots);
    }

    private static int[] defaultSpotCounts(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }
        int cars = Math.min(capacity, 3);
        int bikes = Math.min(Math.max(capacity - cars, 0), 3);
        int trucks = capacity - cars - bikes;
        return new int[] { cars, bikes, trucks };
    }

    private void addSpots(List<ParkingSpot> target, int count, VehicleType type) {
        for (int i = 0; i < count; i++) {
            String spotID = "S" + (target.size() + 1);
            target.add(new ParkingSpot(spotID, floorID, type));
        }
    }

    public String getFloorID() {
        return floorID;
    }

    public int getCapacity() {
        return spots.size();
    }

    public List<ParkingSpot> getSpots() {
        return spots;
    }

    synchronized Optional<ParkingSpot> reserveSpot(VehicleType vehicleType, String ticketID) {
        Objects.requireNonNull(vehicleType, "Vehicle type cannot be null");
        for (ParkingSpot spot : spots) {
            if (spot.getVehicleType() == vehicleType && spot.reserve(ticketID)) {
                return Optional.of(spot);
            }
        }
        return Optional.empty();
    }
}