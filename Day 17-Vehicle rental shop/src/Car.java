// A car charges only its daily rate multiplied by the number of days. daily rate * number of days. It has no discounts or extra charges.
public class Car extends Vehicle {
    public Car(String id, String model, double dailyRate) {
        // Initialize the shared vehicle fields in the parent class.
        super(id, model, dailyRate);
    }

    @Override
    public double calculateRent(int days) {
        validateDays(days);
        // Car rent has no discount or extra charge.
        return getDailyRate() * days;
    }

    @Override
    public String getType() {
        return "Car";
    }
}
