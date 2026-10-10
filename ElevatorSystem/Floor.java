package ElevatorSystem;
import java.util.Objects;

public class Floor {
    private String floorId;
    private int floorNumber;

    public Floor(String floorId, int floorNumber) {
        this.floorId = Objects.requireNonNull(floorId, "Floor ID cannot be null");
        this.floorNumber = floorNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
