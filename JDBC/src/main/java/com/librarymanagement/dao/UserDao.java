package com.librarymanagement.dao;

import com.librarymanagement.config.DatabaseConnection;
import com.librarymanagement.model.LibraryUser;
import com.librarymanagement.model.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {
    public boolean add(LibraryUser user) throws SQLException {
        String sql = "INSERT INTO Users (user_id, name, email, role) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setUserFields(statement, user);
            return statement.executeUpdate() > 0;
        }
    }

    public List<LibraryUser> findAll() throws SQLException {
        String sql = "SELECT user_id, name, email, role FROM Users ORDER BY user_id";
        List<LibraryUser> users = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {
            while (results.next()) {
                users.add(mapUser(results));
            }
        }
        return users;
    }

    public Optional<LibraryUser> findById(int id) throws SQLException {
        String sql = "SELECT user_id, name, email, role FROM Users WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet results = statement.executeQuery()) {
                return results.next() ? Optional.of(mapUser(results)) : Optional.empty();
            }
        }
    }

    public boolean update(LibraryUser user) throws SQLException {
        String sql = "UPDATE Users SET name = ?, email = ?, role = ? WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.name());
            statement.setString(2, user.email());
            statement.setString(3, user.role().name());
            statement.setInt(4, user.userId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM Users WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    private static void setUserFields(PreparedStatement statement, LibraryUser user) throws SQLException {
        statement.setInt(1, user.userId());
        statement.setString(2, user.name());
        statement.setString(3, user.email());
        statement.setString(4, user.role().name());
    }

    private static LibraryUser mapUser(ResultSet results) throws SQLException {
        return new LibraryUser(
                results.getInt("user_id"),
                results.getString("name"),
                results.getString("email"),
                UserRole.valueOf(results.getString("role"))
        );
    }
}
