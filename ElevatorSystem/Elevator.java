package ElevatorSystem;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Elevator {
    private String elevatorId;
    private Floor currentFloor;
    private Direction direction;
    private DoorState doorState;
    private int elevatorNumber;
    private List<Integer> pendingStops;
    private List<Integer> requestedStops;
    private final Map<Integer, List<Integer>> destinationsByPickup = new HashMap<>();

    public Elevator(String elevatorId, Floor currentFloor, Direction direction, DoorState doorState, int elevatorNumber, List<Integer> pendingStops, List<Integer> requestedStops) {
        this.elevatorId = Objects.requireNonNull(elevatorId, "Elevator ID cannot be null");
        this.currentFloor = Objects.requireNonNull(currentFloor, "Current floor cannot be null");
        this.direction = Objects.requireNonNull(direction, "Direction cannot be null");
        this.doorState = Objects.requireNonNull(doorState, "Door state cannot be null");
        this.elevatorNumber = elevatorNumber;
        this.pendingStops = copyStops(pendingStops, "Pending stops");
        this.requestedStops = copyStops(requestedStops, "Requested stops");
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

    public DoorState getDoorState() {
        return doorState;
    }

    public List<Integer> getStops() {
        List<Integer> stops = new ArrayList<>(pendingStops);
        for (Integer stop : requestedStops) {
            if (!stops.contains(stop)) {
                stops.add(stop);
            }
        }
        for (List<Integer> destinations : destinationsByPickup.values()) {
            for (Integer destination : destinations) {
                if (!stops.contains(destination)) {
                    stops.add(destination);
                }
            }
        }
        return Collections.unmodifiableList(stops);
    }

    public void assignRequest(int pickupFloor, int destinationFloor) {
        if (pickupFloor < 0 || destinationFloor < 0) {
            throw new IllegalArgumentException("Floor numbers cannot be negative");
        }
        if (pickupFloor == destinationFloor) {
            throw new IllegalArgumentException("Pickup and destination floors cannot be the same");
        }

        if (!pendingStops.contains(pickupFloor)) {
            pendingStops.add(pickupFloor);
        }
        List<Integer> destinations = destinationsByPickup.computeIfAbsent(pickupFloor, key -> new ArrayList<>());
        if (!destinations.contains(destinationFloor)) {
            destinations.add(destinationFloor);
        }
        if (direction == Direction.IDLE) {
            int currentFloorNumber = currentFloor.getFloorNumber();
            direction = currentFloorNumber < pickupFloor ? Direction.UP
                : currentFloorNumber > pickupFloor ? Direction.DOWN
                : destinationFloor > pickupFloor ? Direction.UP : Direction.DOWN;
        }
    }

    public void addPendingStop(int floorNumber) {
        if (floorNumber < 0) {
            throw new IllegalArgumentException("Floor number cannot be negative");
        }
        if (!pendingStops.contains(floorNumber)) {
            pendingStops.add(floorNumber);
        }
    }

    public void addRequestedStop(int floorNumber) {
        if (floorNumber < 0) {
            throw new IllegalArgumentException("Floor number cannot be negative");
        }
        if (!requestedStops.contains(floorNumber)) {
            requestedStops.add(floorNumber);
        }
    }

    public void removePendingStop(int floorNumber) {
        pendingStops.remove(floorNumber);
        destinationsByPickup.remove(floorNumber);
    }

    public void openDoor() {
        doorState = DoorState.OPENED;
    }

    public void closeDoor() {
        doorState = DoorState.CLOSED;
    }

    public boolean hasStops() {
        return !pendingStops.isEmpty() || !requestedStops.isEmpty();
    }

    public boolean advanceOneFloor(List<Floor> floors) {
        Objects.requireNonNull(floors, "Floors cannot be null");
        if (doorState != DoorState.CLOSED) {
            throw new IllegalStateException("Elevator cannot move while its doors are open");
        }

        int currentFloorNumber = currentFloor.getFloorNumber();
        if (removeStopAt(currentFloorNumber)) {
            openDoor();
            updateDirectionWhenEmpty();
            return false;
        }

        if (!hasStops()) {
            direction = Direction.IDLE;
            return false;
        }

        updateDirectionForRemainingStops(currentFloorNumber);
        int nextFloorNumber = currentFloorNumber + (direction == Direction.UP ? 1 : -1);
        if (nextFloorNumber < 0 || nextFloorNumber >= floors.size()) {
            throw new IllegalStateException("Elevator cannot move beyond the building floors");
        }
        currentFloor = floors.get(nextFloorNumber);

        if (removeStopAt(nextFloorNumber)) {
            openDoor();
            updateDirectionWhenEmpty();
        }
        return true;
    }

    private boolean removeStopAt(int floorNumber) {
        boolean removedPending = pendingStops.remove(Integer.valueOf(floorNumber));
        List<Integer> destinations = destinationsByPickup.remove(floorNumber);
        if (destinations != null) {
            for (Integer destination : destinations) {
                addRequestedStop(destination);
            }
        }
        boolean removedRequested = requestedStops.remove(Integer.valueOf(floorNumber));
        return removedPending || removedRequested;
    }

    private void updateDirectionForRemainingStops(int currentFloorNumber) {
        boolean hasStopAbove = pendingStops.stream().anyMatch(floor -> floor > currentFloorNumber)
            || requestedStops.stream().anyMatch(floor -> floor > currentFloorNumber);
        boolean hasStopBelow = pendingStops.stream().anyMatch(floor -> floor < currentFloorNumber)
            || requestedStops.stream().anyMatch(floor -> floor < currentFloorNumber);

        if (direction == Direction.UP && !hasStopAbove) {
            direction = hasStopBelow ? Direction.DOWN : Direction.IDLE;
        } else if (direction == Direction.DOWN && !hasStopBelow) {
            direction = hasStopAbove ? Direction.UP : Direction.IDLE;
        } else if (direction == Direction.IDLE) {
            direction = hasStopAbove ? Direction.UP : Direction.DOWN;
        }
    }

    private void updateDirectionWhenEmpty() {
        if (!hasStops()) {
            direction = Direction.IDLE;
        }
    }

    private static List<Integer> copyStops(List<Integer> stops, String name) {
        Objects.requireNonNull(stops, name + " cannot be null");
        List<Integer> copy = new ArrayList<>();
        for (Integer stop : stops) {
            if (stop == null || stop < 0) {
                throw new IllegalArgumentException(name + " must contain non-negative floor numbers");
            }
            if (!copy.contains(stop)) {
                copy.add(stop);
            }
        }
        return copy;
    }

}
