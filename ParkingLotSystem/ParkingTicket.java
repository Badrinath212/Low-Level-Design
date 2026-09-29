import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

/** Applies the per-minute rates and rounds each started minute up. */
public class ParkingTicket {
    private static final BigDecimal CAR_RATE = new BigDecimal("1.00");
    private static final BigDecimal BIKE_RATE = new BigDecimal("0.50");
    private static final BigDecimal TRUCK_RATE = new BigDecimal("2.00");

    public long calculateBillableMinutes(Instant entryTime, Instant exitTime) {
        Objects.requireNonNull(entryTime, "Entry time cannot be null");
        Objects.requireNonNull(exitTime, "Exit time cannot be null");
        if (exitTime.isBefore(entryTime)) {
            throw new IllegalArgumentException("Exit time cannot be before entry time");
        }

        Duration duration = Duration.between(entryTime, exitTime);
        long wholeMinutes = duration.toMinutes();
        if (!duration.minusMinutes(wholeMinutes).isZero()) {
            wholeMinutes++;
        }
        return Math.max(1, wholeMinutes);
    }

    public BigDecimal calculateFare(VehicleType vehicleType, long billableMinutes) {
        Objects.requireNonNull(vehicleType, "Vehicle type cannot be null");
        if (billableMinutes <= 0) {
            throw new IllegalArgumentException("Billable minutes must be greater than zero");
        }

        BigDecimal rate = switch (vehicleType) {
            case CAR -> CAR_RATE;
            case BIKE -> BIKE_RATE;
            case TRUCK -> TRUCK_RATE;
        };
        return rate.multiply(BigDecimal.valueOf(billableMinutes))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
