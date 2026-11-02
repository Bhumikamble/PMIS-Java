package com.librarymanagement.dao;

import com.librarymanagement.config.DatabaseConnection;
import com.librarymanagement.model.BorrowRecord;
import com.librarymanagement.model.BorrowStatus;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BorrowRecordDao {
    public boolean add(BorrowRecord record) throws SQLException {
        String sql = """
                INSERT INTO BorrowRecords
                    (record_id, member_id, book_id, borrow_date, due_date, return_date, status)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setRecordFields(statement, record);
            return statement.executeUpdate() > 0;
        }
    }

    public List<BorrowRecord> findAll() throws SQLException {
        String sql = """
                SELECT record_id, member_id, book_id, borrow_date, due_date, return_date, status
                FROM BorrowRecords ORDER BY record_id
                """;
        List<BorrowRecord> records = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet results = statement.executeQuery()) {
            while (results.next()) {
                records.add(mapRecord(results));
            }
        }
        return records;
    }

    public Optional<BorrowRecord> findById(int id) throws SQLException {
        String sql = """
                SELECT record_id, member_id, book_id, borrow_date, due_date, return_date, status
                FROM BorrowRecords WHERE record_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet results = statement.executeQuery()) {
                return results.next() ? Optional.of(mapRecord(results)) : Optional.empty();
            }
        }
    }

    public boolean update(BorrowRecord record) throws SQLException {
        String sql = """
                UPDATE BorrowRecords
                SET member_id = ?, book_id = ?, borrow_date = ?, due_date = ?, return_date = ?, status = ?
                WHERE record_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setRecordFields(statement, record, false);
            statement.setInt(7, record.recordId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM BorrowRecords WHERE record_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    private static void setRecordFields(PreparedStatement statement, BorrowRecord record) throws SQLException {
        setRecordFields(statement, record, true);
    }

    private static void setRecordFields(PreparedStatement statement, BorrowRecord record, boolean includeId)
            throws SQLException {
        int index = 1;
        if (includeId) {
            statement.setInt(index++, record.recordId());
        }
        statement.setInt(index++, record.memberId());
        statement.setInt(index++, record.bookId());
        statement.setDate(index++, Date.valueOf(record.borrowDate()));
        statement.setDate(index++, Date.valueOf(record.dueDate()));
        if (record.returnDate() == null) {
            statement.setNull(index++, java.sql.Types.DATE);
        } else {
            statement.setDate(index++, Date.valueOf(record.returnDate()));
        }
        statement.setString(index, record.status().name());
    }

    private static BorrowRecord mapRecord(ResultSet results) throws SQLException {
        Date returnDate = results.getDate("return_date");
        return new BorrowRecord(
                results.getInt("record_id"),
                results.getInt("member_id"),
                results.getInt("book_id"),
                results.getDate("borrow_date").toLocalDate(),
                results.getDate("due_date").toLocalDate(),
                returnDate == null ? null : returnDate.toLocalDate(),
                BorrowStatus.valueOf(results.getString("status"))
        );
    }
}
