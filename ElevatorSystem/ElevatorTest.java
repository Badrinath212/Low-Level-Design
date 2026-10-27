package ElevatorSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

class ElevatorTest {
    private Elevator elevator;

    @BeforeEach
    void setUp() {
        elevator = new Elevator(5, 5);
    }
    
    @AfterEach
    void close() {
        System.out.println("Closing Elevator");
    }

    @Test
    void shouldCreateElevator() {
        assertEquals(5, elevator.getElevatorNumber());
        assertEquals(Direction.IDLE, elevator.getDirection());
        assertEquals(DoorState.CLOSED, elevator.getDoorState());
    }

    @Test
    void shouldSelectNearestElevator() {
        Elevator elevator1 = new Elevator(2, 4);
        Elevator elevator2 = new Elevator(8, 0);

        ElevatorSystem elevatorSystem = new ElevatorSystem(
            List.of(elevator1, elevator2),
            10,
            List.of(
                    new Floor("F0", 0),
                    new Floor("F2", 1), 
                    new Floor("F3", 2), 
                    new Floor("F4", 3), 
                    new Floor("F5", 4),
                    new Floor("F6", 5), 
                    new Floor("F7", 6), 
                    new Floor("F8", 7), 
                    new Floor("F9", 8), 
                    new Floor("F10", 9)
        ));

        int selectedElevator = elevatorSystem.bookElevator(3, 4);
        System.out.println("Selected Elevator: " + selectedElevator);

        assertEquals(2, selectedElevator);
    }

    @Test
    void shouldStartWithClosedDoors() {
        assertEquals(DoorState.CLOSED, elevator.getDoorState());
    }
    
}