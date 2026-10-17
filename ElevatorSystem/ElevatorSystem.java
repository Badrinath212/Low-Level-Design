package ElevatorSystem;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ElevatorSystem {
    private final List<Elevator> elevators;
    private final List<Floor> floors;
    private final int totalFloors;
    private final Map<Integer, Elevator> elevatorMap = new HashMap<>();

    public ElevatorSystem(List<Elevator> elevators, int totalFloors, List<Floor> floors) {
        if (elevators == null || elevators.isEmpty()) {
            throw new IllegalArgumentException("Elevators list cannot be null or empty");
        }
        if (totalFloors <= 0) {
            throw new IllegalArgumentException("Total floors must be greater than 0");
        }

        if (floors == null || floors.isEmpty()) {
            throw new IllegalArgumentException("Floors list cannot be null or empty");
        }

        this.elevators = new ArrayList<>(elevators);
        this.totalFloors = totalFloors;
        if (floors.size() != totalFloors) {
            throw new IllegalArgumentException("Number of floors provided does not match totalFloors");
        }
        List<Floor> floorsByNumber = new ArrayList<>(Collections.nCopies(totalFloors, null));
        for (Floor floor : floors) {
            Objects.requireNonNull(floor, "Floor cannot be null");
            int floorNumber = floor.getFloorNumber();
            if (floorNumber < 0 || floorNumber >= totalFloors || floorsByNumber.get(floorNumber) != null) {
                throw new IllegalArgumentException("Floor numbers must be unique and within the building");
            }
            floorsByNumber.set(floorNumber, floor);
        }
        this.floors = Collections.unmodifiableList(floorsByNumber);
        for (Elevator elevator : this.elevators) {
            Objects.requireNonNull(elevator, "Elevator cannot be null");
            int currentFloor = elevator.getCurrentFloor().getFloorNumber();
            if (currentFloor < 0 || currentFloor >= totalFloors) {
                throw new IllegalArgumentException("Elevator current floor is outside the building");
            }
            for (int stop : elevator.getStops()) {
                if (stop >= totalFloors) {
                    throw new IllegalArgumentException("Elevator stop is outside the building");
                }
            }
            if (elevatorMap.putIfAbsent(elevator.getElevatorNumber(), elevator) != null) {
                throw new IllegalArgumentException("Elevator numbers must be unique");
            }
        }
    }

    public int bookElevator(int requestedFloor, int destinationFloor) {
        if (requestedFloor < 0 || requestedFloor >= totalFloors) {
            throw new IllegalArgumentException("Invalid requested floor: " + requestedFloor);
        }
        if (destinationFloor < 0 || destinationFloor >= totalFloors) {
            throw new IllegalArgumentException("Invalid destination floor: " + destinationFloor);
        }

        if (requestedFloor == destinationFloor) {
            throw new IllegalArgumentException("Requested floor and destination floor cannot be the same");
        }

        Direction requestDirection = (destinationFloor > requestedFloor) ? Direction.UP : Direction.DOWN;
        Elevator selectedElevator = null;
        int shortestDistance = Integer.MAX_VALUE;
        for (Elevator elevator : elevators) {
            if (canServeInCurrentDirection(elevator, requestDirection, requestedFloor)) {
                int distance = Math.abs(elevator.getCurrentFloor().getFloorNumber() - requestedFloor);
                if (distance < shortestDistance) {
                    selectedElevator = elevator;
                    shortestDistance = distance;
                }
            }
        }

        if (selectedElevator == null) {
            for (Elevator elevator : elevators) {
                int distance = Math.abs(elevator.getCurrentFloor().getFloorNumber() - requestedFloor);
                if (distance < shortestDistance) {
                    selectedElevator = elevator;
                    shortestDistance = distance;
                }
            }
        }

        if (selectedElevator == null) {
            return -1;
        }
        selectedElevator.assignRequest(requestedFloor, destinationFloor);
        return selectedElevator.getElevatorNumber();
    }

    public Elevator getElevator(int elevatorNumber) {
        Elevator elevator = elevatorMap.get(elevatorNumber);
        if (elevator == null) {
            throw new IllegalArgumentException("Unknown elevator number: " + elevatorNumber);
        }
        return elevator;
    }

    private boolean canServeInCurrentDirection(Elevator elevator, Direction requestDirection, int requestedFloor) {
        if (elevator.getDirection() == Direction.IDLE) {
            return true;
        }
        int elevatorFloor = elevator.getCurrentFloor().getFloorNumber();
        return (elevator.getDirection() == Direction.UP
                && requestDirection == Direction.UP && elevatorFloor <= requestedFloor)
            || (elevator.getDirection() == Direction.DOWN
                && requestDirection == Direction.DOWN && elevatorFloor >= requestedFloor);
    }

    public void advanceOneStep() {
        for (Elevator elevator : elevators) {
            if (elevator.getDoorState() == DoorState.CLOSED) {
                elevator.advanceOneFloor(floors);
            }
        }
    }

    public static void main(String[] args) {
        ElevatorSystem elevatorSystem = new ElevatorSystem(
            List.of(
                new Elevator("E1", new Floor("F1", 0), Direction.UP, DoorState.CLOSED, 1, List.of(), List.of()),
                new Elevator("E2", new Floor("F2", 5), Direction.IDLE, DoorState.CLOSED, 2, List.of(), List.of()),
                new Elevator("E3", new Floor("F3", 2), Direction.IDLE, DoorState.CLOSED, 3, List.of(), List.of()),
                new Elevator("E4", new Floor("F4", 3), Direction.UP, DoorState.CLOSED, 4, List.of(), List.of()),
                new Elevator("E5", new Floor("F5", 1), Direction.DOWN, DoorState.CLOSED, 5, List.of(), List.of())
            ),  
            10,
            List.of(
                new Floor("F1", 0),
                new Floor("F2", 1),
                new Floor("F3", 2),
                new Floor("F4", 3),
                new Floor("F5", 4),
                new Floor("F6", 5),
                new Floor("F7", 6),
                new Floor("F8", 7),
                new Floor("F9", 8),
                new Floor("F10", 9)
            )
        );

        elevatorSystem.bookElevator(2, 5);
    }   
}
