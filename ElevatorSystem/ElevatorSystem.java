package ElevatorSystem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ElevatorSystem {
    List<Elevator> elevators;
    List<Floor> floors;
    int totalFloors;
    Map<Integer, Elevator> elevatorMap = new HashMap<>();

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

        this .elevators = elevators;
        this.totalFloors = totalFloors;
        this.floors = floors;
        for (Elevator elevator : elevators) {
            elevatorMap.put(elevator.getElevatorNumber(), elevator);
        }
    }

    public int bookElevator(int requestedFloor, int destinationFloor) {
        if (destinationFloor < 0 || destinationFloor >= totalFloors) {
            throw new IllegalArgumentException("Invalid destination floor: " + destinationFloor);
        }

        // Determine the direction of the request
        Direction requestDirection = (destinationFloor > requestedFloor) ? Direction.UP : Direction.DOWN;
        // Elevator number and distance(in floors) to requested floor
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        for (Elevator elevator : elevators) {
            if (elevator.getDirection() == Direction.IDLE || elevator.getDirection() == requestDirection) {
                int distance = Math.abs(elevator.getCurrentFloor().getFloorNumber() - requestedFloor);
                pq.offer(new int[]{elevator.getElevatorNumber(), distance});
            }
        }

        if (pq.isEmpty()) {
            return -1;
        }
        int selectedElevatorNumber = pq.poll()[0];
        Elevator selectedElevator = elevatorMap.get(selectedElevatorNumber);
        selectedElevator.addRequestedStop(destinationFloor);
        return selectedElevatorNumber;
    }
    public static void main(String[] args) {
        ElevatorSystem elevatorSystem = new ElevatorSystem(
            List.of(
                new Elevator("E1", new Floor("F1", 0), Direction.UP, DoorState.CLOSED, 1, List.of(), List.of()),
                new Elevator("E2", new Floor("F2", 5), Direction.IDLE, DoorState.CLOSED, 2, List.of(), List.of()),
                new Elevator("E3", new Floor("F3", 2), Direction.IDLE, DoorState.CLOSED, 3, List.of(), List.of()),
                new Elevator("E4", new Floor("F4", 3), Direction.UP, DoorState.CLOSED, 4, List.of(), List.of()),
                new Elevator("E5", new Floor("F5", 1), Direction.DOWN, DoorState.CLOSED, 5, List.of(), List.of()),
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
