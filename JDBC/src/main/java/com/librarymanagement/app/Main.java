package com.librarymanagement.app;

import com.librarymanagement.dao.BookDao;
import com.librarymanagement.dao.BorrowRecordDao;
import com.librarymanagement.dao.UserDao;
import com.librarymanagement.model.Book;
import com.librarymanagement.model.BorrowRecord;
import com.librarymanagement.model.BorrowStatus;
import com.librarymanagement.model.LibraryUser;
import com.librarymanagement.model.UserRole;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final BookDao BOOK_DAO = new BookDao();
    private static final UserDao USER_DAO = new UserDao();
    private static final BorrowRecordDao BORROW_RECORD_DAO = new BorrowRecordDao();

    public static void main(String[] args) {
        while (true) {
            System.out.println("""

                    === Library Management ===
                    1. Manage books
                    2. Manage users
                    3. Manage borrow records
                    0. Exit
                    """);
            switch (readLine("Choose an option: ")) {
                case "1" -> bookMenu();
                case "2" -> userMenu();
                case "3" -> borrowRecordMenu();
                case "0" -> {
                    System.out.println("Goodbye.");
                    return;
                }
                default -> System.out.println("Choose a number from 0 to 3.");
            }
        }
    }

    private static void bookMenu() {
        while (true) {
            printEntityMenu("Books");
            switch (readLine("Choose an option: ")) {
                case "1" -> runDatabaseAction(Main::addBook);
                case "2" -> runDatabaseAction(Main::listBooks);
                case "3" -> runDatabaseAction(Main::findBook);
                case "4" -> runDatabaseAction(Main::updateBook);
                case "5" -> runDatabaseAction(Main::deleteBook);
                case "0" -> { return; }
                default -> System.out.println("Choose a number from 0 to 5.");
            }
        }
    }

    private static void userMenu() {
        while (true) {
            printEntityMenu("Users");
            switch (readLine("Choose an option: ")) {
                case "1" -> runDatabaseAction(Main::addUser);
                case "2" -> runDatabaseAction(Main::listUsers);
                case "3" -> runDatabaseAction(Main::findUser);
                case "4" -> runDatabaseAction(Main::updateUser);
                case "5" -> runDatabaseAction(Main::deleteUser);
                case "0" -> { return; }
                default -> System.out.println("Choose a number from 0 to 5.");
            }
        }
    }

    private static void borrowRecordMenu() {
        while (true) {
            printEntityMenu("Borrow Records");
            switch (readLine("Choose an option: ")) {
                case "1" -> runDatabaseAction(Main::addBorrowRecord);
                case "2" -> runDatabaseAction(Main::listBorrowRecords);
                case "3" -> runDatabaseAction(Main::findBorrowRecord);
                case "4" -> runDatabaseAction(Main::updateBorrowRecord);
                case "5" -> runDatabaseAction(Main::deleteBorrowRecord);
                case "0" -> { return; }
                default -> System.out.println("Choose a number from 0 to 5.");
            }
        }
    }

    private static void printEntityMenu(String entity) {
        System.out.printf("""

                === %s ===
                1. Add
                2. List all
                3. Find by ID
                4. Update
                5. Delete
                0. Back
                """, entity);
    }

    private static void addBook() throws SQLException {
        if (BOOK_DAO.add(readBookFields(readInt("Book ID: ")))) {
            System.out.println("Book added.");
        }
    }

    private static void listBooks() throws SQLException {
        List<Book> books = BOOK_DAO.findAll();
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        books.forEach(System.out::println);
    }

    private static void findBook() throws SQLException {
        int id = readInt("Book ID: ");
        BOOK_DAO.findById(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("No book found with ID " + id + ".")
        );
    }

    private static void updateBook() throws SQLException {
        int id = readInt("Book ID to update: ");
        if (BOOK_DAO.findById(id).isEmpty()) {
            System.out.println("No book found with ID " + id + ".");
            return;
        }
        System.out.println(BOOK_DAO.update(readBookFields(id)) ? "Book updated." : "Book was not updated.");
    }

    private static void deleteBook() throws SQLException {
        int id = readInt("Book ID to delete: ");
        System.out.println(BOOK_DAO.delete(id) ? "Book deleted." : "No book found with ID " + id + ".");
    }

    private static Book readBookFields(int id) {
        String title = readLine("Title: ");
        String author = readLine("Author: ");
        String category = readLine("Category (leave blank if none): ");
        int totalCopies = readInt("Total copies: ");
        int availableCopies = readInt("Available copies: ");
        return new Book(id, title, author, category.isEmpty() ? null : category, totalCopies, availableCopies);
    }

    private static void addUser() throws SQLException {
        if (USER_DAO.add(readUserFields(readInt("User ID: ")))) {
            System.out.println("User added.");
        }
    }

    private static void listUsers() throws SQLException {
        List<LibraryUser> users = USER_DAO.findAll();
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        users.forEach(System.out::println);
    }

    private static void findUser() throws SQLException {
        int id = readInt("User ID: ");
        USER_DAO.findById(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("No user found with ID " + id + ".")
        );
    }

    private static void updateUser() throws SQLException {
        int id = readInt("User ID to update: ");
        if (USER_DAO.findById(id).isEmpty()) {
            System.out.println("No user found with ID " + id + ".");
            return;
        }
        System.out.println(USER_DAO.update(readUserFields(id)) ? "User updated." : "User was not updated.");
    }

    private static void deleteUser() throws SQLException {
        int id = readInt("User ID to delete: ");
        System.out.println(USER_DAO.delete(id) ? "User deleted." : "No user found with ID " + id + ".");
    }

    private static LibraryUser readUserFields(int id) {
        String name = readLine("Name: ");
        String email = readLine("Email: ");
        UserRole role = readEnum("Role (LIBRARIAN or MEMBER): ", UserRole.class);
        return new LibraryUser(id, name, email, role);
    }

    private static void addBorrowRecord() throws SQLException {
        if (BORROW_RECORD_DAO.add(readBorrowRecordFields(readInt("Record ID: ")))) {
            System.out.println("Borrow record added.");
        }
    }

    private static void listBorrowRecords() throws SQLException {
        List<BorrowRecord> records = BORROW_RECORD_DAO.findAll();
        if (records.isEmpty()) {
            System.out.println("No borrow records found.");
            return;
        }
        records.forEach(System.out::println);
    }

    private static void findBorrowRecord() throws SQLException {
        int id = readInt("Record ID: ");
        BORROW_RECORD_DAO.findById(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("No borrow record found with ID " + id + ".")
        );
    }

    private static void updateBorrowRecord() throws SQLException {
        int id = readInt("Record ID to update: ");
        if (BORROW_RECORD_DAO.findById(id).isEmpty()) {
            System.out.println("No borrow record found with ID " + id + ".");
            return;
        }
        System.out.println(BORROW_RECORD_DAO.update(readBorrowRecordFields(id))
                ? "Borrow record updated."
                : "Borrow record was not updated.");
    }

    private static void deleteBorrowRecord() throws SQLException {
        int id = readInt("Record ID to delete: ");
        System.out.println(BORROW_RECORD_DAO.delete(id)
                ? "Borrow record deleted."
                : "No borrow record found with ID " + id + ".");
    }

    private static BorrowRecord readBorrowRecordFields(int id) {
        int memberId = readInt("Member user ID: ");
        int bookId = readInt("Book ID: ");
        LocalDate borrowDate = readDate("Borrow date (YYYY-MM-DD): ", false);
        LocalDate dueDate = readDate("Due date (YYYY-MM-DD): ", false);
        LocalDate returnDate = readDate("Return date (YYYY-MM-DD, blank if not returned): ", true);
        BorrowStatus status = readEnum("Status (BORROWED or RETURNED): ", BorrowStatus.class);
        return new BorrowRecord(id, memberId, bookId, borrowDate, dueDate, returnDate, status);
    }

    private static LocalDate readDate(String prompt, boolean allowBlank) {
        while (true) {
            String value = readLine(prompt);
            if (allowBlank && value.isEmpty()) {
                return null;
            }
            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException exception) {
                System.out.println("Enter a valid date in YYYY-MM-DD format.");
            }
        }
    }

    private static <E extends Enum<E>> E readEnum(String prompt, Class<E> enumType) {
        while (true) {
            String value = readLine(prompt).toUpperCase(Locale.ROOT);
            try {
                return Enum.valueOf(enumType, value);
            } catch (IllegalArgumentException exception) {
                System.out.println("Enter one of: " + String.join(", ",
                        java.util.Arrays.stream(enumType.getEnumConstants()).map(Enum::name).toList()));
            }
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            String value = readLine(prompt);
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException exception) {
                System.out.println("Enter a whole number.");
            }
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return INPUT.nextLine().trim();
    }

    private static void runDatabaseAction(DatabaseAction action) {
        try {
            action.run();
        } catch (SQLException exception) {
            System.err.println("Database operation failed: " + exception.getMessage());
        }
    }

    @FunctionalInterface
    private interface DatabaseAction {
        void run() throws SQLException;
    }
}
