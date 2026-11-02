import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Manages the shop's vehicles and the rentals that are currently in progress.
public class RentalShop {
    // LinkedHashMap keeps vehicles in the order they were added.
    private final Map<String, Vehicle> vehicles = new LinkedHashMap<>();
    // Each active rental is indexed by the rented vehicle's ID.
    private final Map<String, Rental> activeRentals = new LinkedHashMap<>();

    public RentalShop() {
    }

    // Convenience constructor for loading an initial group of vehicles.
    public RentalShop(Collection<? extends Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            addVehicle(vehicle);
        }
    }

    // Add a vehicle to the shop, requiring each ID to be unique.
    public void addVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        if (vehicles.containsKey(vehicle.getId())) {
            throw new IllegalArgumentException("A vehicle with id " + vehicle.getId() + " already exists");
        }
        vehicles.put(vehicle.getId(), vehicle);
    }

    // Find a vehicle, verify it can be rented, and record the new rental.
    public Rental rent(String vehicleId, Customer customer, int days) throws RentalException {
        Vehicle vehicle = vehicles.get(vehicleId);
        if (vehicle == null) {
            throw new RentalException("Vehicle " + vehicleId + " was not found");
        }
        if (!vehicle.isAvailable()) {
            throw new RentalException(vehicleDescription(vehicle) + " is already rented");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        // Rental construction calculates the price and validates the number of days.
        Rental rental = new Rental(vehicle, customer, days);
        // Update all related state only after the rental record was created successfully.
        vehicle.markRented();
        activeRentals.put(vehicleId, rental);
        customer.addRental(rental);
        return rental;
    }

    // End an active rental and make its vehicle available again.
    public Rental giveBack(String vehicleId) throws RentalException {
        Vehicle vehicle = vehicles.get(vehicleId);
        if (vehicle == null) {
            throw new RentalException("Vehicle " + vehicleId + " was not found");
        }
        Rental rental = activeRentals.remove(vehicleId);
        if (rental == null) {
            throw new RentalException(vehicleDescription(vehicle) + " is not currently rented");
        }

        vehicle.markReturned();
        return rental;
    }

    // Print every vehicle that is currently available to rent.
    public void showAvailable() {
        System.out.println("Available vehicles:");
        for (Vehicle vehicle : getAvailableVehicles()) {
            System.out.println("  " + vehicleDescription(vehicle));
        }
    }

    // Build a list of available vehicles without exposing the internal map.
    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> availableVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles.values()) {
            if (vehicle.isAvailable()) {
                availableVehicles.add(vehicle);
            }
        }
        return availableVehicles;
    }

    // Keep the vehicle's display format consistent in messages and listings.
    private String vehicleDescription(Vehicle vehicle) {
        return vehicle.getType() + " " + vehicle.getModel() + " (" + vehicle.getId() + ")";
    }
}
