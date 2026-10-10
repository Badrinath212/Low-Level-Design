package ElevatorSystem;
import java.util.List;
import java.util.Objects;

public class Elevator {
    private String elevatorId;
    private Floor currentFloor;
    private Direction direction;
    private DoorState doorState;
    private int elevatorNumber;
    private List<Integer> pendingStops;
    private List<Integer> requestedStops;

    public Elevator(String elevatorId, Floor currentFloor, Direction direction, DoorState doorState, int elevatorNumber, List<Integer> pendingStops, List<Integer> requestedStops) {
        this.elevatorId = Objects.requireNonNull(elevatorId, "Elevator ID cannot be null");
        this.currentFloor = Objects.requireNonNull(currentFloor, "Current floor cannot be null");
        this.direction = Objects.requireNonNull(direction, "Direction cannot be null");
        this.doorState = Objects.requireNonNull(doorState, "Door state cannot be null");
        this.elevatorNumber = elevatorNumber;
        this.pendingStops = Objects.requireNonNull(pendingStops, "Pending stops cannot be null");
        this.requestedStops = Objects.requireNonNull(requestedStops, "Requested stops cannot be null");
    }

    public Direction getDirection() {
        return direction;
    }

    public Floor getCurrentFloor() {
        return currentFloor;
    }

    public int getElevatorNumber() {
        return elevatorNumber;
    }

    public void addRequestedStop(int floorNumber) {
        requestedStops.add(floorNumber);
    }


}
