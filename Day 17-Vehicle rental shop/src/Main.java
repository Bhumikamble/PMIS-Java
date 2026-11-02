import java.util.Locale;

// Program entry point: creates sample data and demonstrates shop operations.
public class Main {
    public static void main(String[] args) {
        // Create the shop and add its vehicles.
        RentalShop shop = new RentalShop();
        shop.addVehicle(new Car("C1", "Swift", 2000));
        shop.addVehicle(new Bike("B1", "Activa", 400));
        shop.addVehicle(new Truck("T1", "Tata Ace", 2500));

        // Create customers who will rent from the shop.
        Customer asha = new Customer("Asha");
        Customer ravi = new Customer("Ravi");

        try {
            // These rentals demonstrate the bike discount and truck insurance.
            printRental(shop.rent("B1", asha, 8));
            printRental(shop.rent("T1", ravi, 2));
            // This should fail because Asha has already rented the bike.
            shop.rent("B1", ravi, 1);
        } catch (RentalException exception) {
            // Show the reason the shop could not complete the rental.
            System.out.println("Error: " + exception.getMessage());
        }

        try {
            // Return the bike so it appears in the available-vehicles listing.
            shop.giveBack("B1");
        } catch (RentalException exception) {
            System.out.println("Error: " + exception.getMessage());
        }

        shop.showAvailable();
    }

    // Print one rental in the format required by the example output.
    private static void printRental(Rental rental) {
        Vehicle vehicle = rental.getVehicle();
        System.out.printf(
                Locale.US,
                "%s rented %s for %d days = %.2f%n",
                rental.getCustomer().getName(),
                vehicle.getType() + " " + vehicle.getModel() + " (" + vehicle.getId() + ")",
                rental.getDays(),
                rental.getTotalCost());
    }
}
