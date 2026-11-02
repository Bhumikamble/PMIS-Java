// One rental record connects a customer, a vehicle, and the rental duration.
public class Rental {
    private final Vehicle vehicle;
    private final Customer customer;
    private final int days;
    // Save the calculated price so this rental record retains its original cost.
    private final double totalCost;

    Rental(Vehicle vehicle, Customer customer, int days) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.days = days;
        // The vehicle type determines the pricing rule (discount, insurance, etc.).
        this.totalCost = vehicle.calculateRent(days);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }

    public double getTotalCost() {
        return totalCost;
    }
}

