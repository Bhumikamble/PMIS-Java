// A truck adds one fixed insurance charge to the normal daily rent.
public class Truck extends Vehicle {
    // A constant because this charge is the same for every truck rental.
    private static final double INSURANCE_CHARGE = 500.0;

    public Truck(String id, String model, double dailyRate) {
        super(id, model, dailyRate);
    }

    @Override
    public double calculateRent(int days) {
        validateDays(days);
        // Insurance is charged once per rental, not once per day.
        return getDailyRate() * days + INSURANCE_CHARGE;
    }

    @Override
    public String getType() {
        return "Truck";
    }
}
