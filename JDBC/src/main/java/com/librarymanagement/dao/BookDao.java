package com.librarymanagement.dao;

import com.librarymanagement.config.DatabaseConnection;
import com.librarymanagement.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDao {
    public boolean add(Book book) throws SQLException {
        String sql = """
                INSERT INTO Books (book_id, title, author, category, total_copies, available_copies)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setBookFields(statement, book);
            return statement.executeUpdate() > 0;
        }
    }

    public List<Book> findAll() throws SQLException {
        String sql = """
                SELECT book_id, title, author, category, total_copies, available_copies
                FROM Books ORDER BY book_id
                """;
        List<Book> books = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {
            while (results.next()) {
                books.add(mapBook(results));
            }
        }
        return books;
    }

    public Optional<Book> findById(int id) throws SQLException {
        String sql = """
                SELECT book_id, title, author, category, total_copies, available_copies
                FROM Books WHERE book_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet results = statement.executeQuery()) {
                return results.next() ? Optional.of(mapBook(results)) : Optional.empty();
            }
        }
    }

    public boolean update(Book book) throws SQLException {
        String sql = """
                UPDATE Books
                SET title = ?, author = ?, category = ?, total_copies = ?, available_copies = ?
                WHERE book_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setBookFields(statement, book);
            statement.setInt(6, book.bookId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Books WHERE book_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    private static void setBookFields(PreparedStatement statement, Book book) throws SQLException {
        statement.setString(1, book.title());
        statement.setString(2, book.author());
        statement.setString(3, book.category());
        statement.setInt(4, book.totalCopies());
        statement.setInt(5, book.availableCopies());
    }

    private static Book mapBook(ResultSet results) throws SQLException {
        return new Book(
                results.getInt("book_id"),
                results.getString("title"),
                results.getString("author"),
                results.getString("category"),
                results.getInt("total_copies"),
                results.getInt("available_copies")
        );
    }
}
