import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public final class Ticket {
    private final String ticketID;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final Instant entryTime;
    private Instant exitTime;
    private Long billableMinutes;
    private BigDecimal fare;

    public Ticket(String ticketID, Vehicle vehicle, ParkingSpot spot, Instant entryTime) {
        if (ticketID == null || ticketID.isBlank()) {
            throw new IllegalArgumentException("Ticket ID cannot be blank");
        }
        this.ticketID = ticketID;
        this.vehicle = Objects.requireNonNull(vehicle, "Vehicle cannot be null");
        this.spot = Objects.requireNonNull(spot, "Parking spot cannot be null");
        this.entryTime = Objects.requireNonNull(entryTime, "Entry time cannot be null");
    }

    public String getTicketID() {
        return ticketID;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public Instant getEntryTime() {
        return entryTime;
    }

    public Optional<Instant> getExitTime() {
        return Optional.ofNullable(exitTime);
    }

    public Optional<Long> getBillableMinutes() {
        return Optional.ofNullable(billableMinutes);
    }

    public Optional<BigDecimal> getFare() {
        return Optional.ofNullable(fare);
    }

    public boolean isClosed() {
        return exitTime != null;
    }

    void completeCheckout(Instant exitTime, long billableMinutes, BigDecimal fare) {
        if (isClosed()) {
            throw new IllegalStateException("Ticket has already been checked out");
        }
        this.exitTime = Objects.requireNonNull(exitTime, "Exit time cannot be null");
        this.billableMinutes = billableMinutes;
        this.fare = Objects.requireNonNull(fare, "Fare cannot be null");
    }

    @Override
    public String toString() {
        String checkoutDetails = isClosed()
                ? ", Minutes: " + billableMinutes + ", Fare: $" + fare
                : ", Status: ACTIVE";
        return "Ticket: " + ticketID + ", Vehicle: " + vehicle.getVehicleNumber()
                + ", Spot: " + spot.getFloorID() + "-" + spot.getSpotID() + checkoutDetails;
    }
}
