// A bike gives a 10% discount when rented for at least seven days.
public class Bike extends Vehicle {
    public Bike(String id, String model, double dailyRate) {
        super(id, model, dailyRate);
    }

    @Override
    public double calculateRent(int days) {
        validateDays(days);
        double rent = getDailyRate() * days;
        // 0.9 means the customer pays 90% of the original price.
        return days >= 7 ? rent * 0.9 : rent;
    }

    @Override
    public String getType() {
        return "Bike";
    }
}
