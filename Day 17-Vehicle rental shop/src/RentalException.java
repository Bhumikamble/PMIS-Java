// Checked exception used for shop operations that cannot be completed,
// such as renting a missing vehicle or returning one that is not rented.
public class RentalException extends Exception {
    public RentalException(String message) {
        super(message);
    }
}
