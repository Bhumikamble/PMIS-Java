// Holds information and shared behavior used by every kind of vehicle.
// Abstract means the shop should create a Car, Bike, or Truck, not a plain Vehicle.
public abstract class Vehicle implements Rentable {
    // These details cannot be changed after the vehicle is created.
    private final String id;
    private final String model;
    private final double dailyRate;

    // New vehicles start out ready to rent.
    private boolean available = true;

    protected Vehicle(String id, String model, double dailyRate) {
        // Reject invalid vehicle information before storing it.
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle id cannot be empty");
        }
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle model cannot be empty");
        }
        if (dailyRate < 0) {
            throw new IllegalArgumentException("Daily rate cannot be negative");
        }

        this.id = id;
        this.model = model;
        this.dailyRate = dailyRate;
    }

    // Getters allow other classes to read the vehicle's details safely.
    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    // All vehicle types use the same availability flag.
    @Override
    public boolean isAvailable() {
        return available;
    }

    // Each concrete vehicle type supplies its own display name.
    public abstract String getType();

    // Shared validation used before calculating any vehicle's rent.
    protected void validateDays(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Rental days must be greater than zero");
        }
    }

    // The shop calls these when a rental starts or ends.
    final void markRented() {
        available = false;
    }

    final void markReturned() {
        available = true;
    }
}
