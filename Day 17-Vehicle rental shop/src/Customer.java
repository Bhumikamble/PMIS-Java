import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a customer and keeps the rentals they have made.
public class Customer {
    private final String name;
    // A customer can have many completed or active rentals in their history.
    private final List<Rental> rentalHistory = new ArrayList<>();

    public Customer(String name) {
        // A customer must have a usable name.
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Return a read-only copy so callers cannot change the customer's history.
    public List<Rental> getRentalHistory() {
        return Collections.unmodifiableList(new ArrayList<>(rentalHistory));
    }

    // Package-private: the shop adds a rental when it successfully rents a vehicle.
    void addRental(Rental rental) {
        rentalHistory.add(rental);
    }
}
