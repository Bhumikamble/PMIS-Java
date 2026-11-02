// Describes the operations every vehicle available for rent must provide.
public interface Rentable {
    // Returns the cost of renting this item for the requested number of days.
    double calculateRent(int days);

    // Tells the shop whether this item can currently be rented.
    boolean isAvailable();
}
