import java.util.Objects;

public final class Vehicle {
    private final VehicleType vehicleType;
    private final String vehicleNumber;
    private final String driverName;
    private final String driverNumber;

    public Vehicle(VehicleType vehicleType, String vehicleNumber, String driverName, String driverNumber) {
        this.vehicleType = Objects.requireNonNull(vehicleType, "Vehicle type cannot be null");
        this.vehicleNumber = requireText(vehicleNumber, "Vehicle number");
        this.driverName = requireText(driverName, "Driver name");
        this.driverNumber = requireText(driverNumber, "Driver number");
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
        return value;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDriverNumber() {
        return driverNumber;
    }

    @Override
    public String toString() {
        return vehicleType + " " + vehicleNumber + " (" + driverName + ")";
    }
}
