import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class ParkingLot {
    private final List<ParkingFloor> floors;
    private final ParkingTicket pricing;
    private final Map<String, Ticket> activeTickets = new HashMap<>();

    public ParkingLot(List<ParkingFloor> floors) {
        this(floors, new ParkingTicket());
    }

    public ParkingLot(List<ParkingFloor> floors, ParkingTicket pricing) {
        if (floors == null || floors.isEmpty()) {
            throw new IllegalArgumentException("A parking lot must have at least one floor");
        }
        this.floors = List.copyOf(floors);
        this.pricing = Objects.requireNonNull(pricing, "Pricing service cannot be null");
    }

    /** Reserves a compatible spot and returns the ticket needed for checkout. */
    public synchronized Ticket parkVehicle(Vehicle vehicle) {
        Objects.requireNonNull(vehicle, "Vehicle cannot be null");
        String ticketID = UUID.randomUUID().toString();

        for (ParkingFloor floor : floors) {
            Optional<ParkingSpot> spot = floor.reserveSpot(vehicle.getVehicleType(), ticketID);
            if (spot.isPresent()) {
                Ticket ticket = new Ticket(ticketID, vehicle, spot.get(), Instant.now());
                activeTickets.put(ticketID, ticket);
                return ticket;
            }
        }
        throw new IllegalStateException("No compatible parking spot is available");
    }

    /** Charges the session and releases the spot associated with the ticket. */
    public synchronized Ticket checkout(String ticketID) {
        if (ticketID == null || ticketID.isBlank()) {
            throw new IllegalArgumentException("Ticket ID cannot be blank");
        }
        Ticket ticket = activeTickets.get(ticketID);
        if (ticket == null) {
            throw new IllegalArgumentException("No active parking session for ticket: " + ticketID);
        }

        Instant exitTime = Instant.now();
        long minutes = pricing.calculateBillableMinutes(ticket.getEntryTime(), exitTime);
        ticket.getSpot().release(ticketID);
        ticket.completeCheckout(exitTime, minutes,
                pricing.calculateFare(ticket.getVehicle().getVehicleType(), minutes));
        activeTickets.remove(ticketID);
        return ticket;
    }

    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot(List.of(
                new ParkingFloor("F0", 3, 3, 4),
                new ParkingFloor("F1", 3, 3, 4),
                new ParkingFloor("F2", 3, 3, 4)));

        Vehicle vehicle = new Vehicle(VehicleType.CAR, "ABC-123", "John", "123456789");
        Ticket ticket = parkingLot.parkVehicle(vehicle);
        System.out.println("Issued: " + ticket);

        Ticket receipt = parkingLot.checkout(ticket.getTicketID());
        System.out.println("Checkout: " + receipt);
    }
}
