
package com.example.demo.repository;

import com.example.demo.model.SupplyRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Statement;
import java.util.List;

@Repository
public class RequestRepository {

    private final JdbcTemplate jdbcTemplate;

    public RequestRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Converts a database row into a SupplyRequest object
    private final RowMapper<SupplyRequest> requestRowMapper =
            (rs, rowNum) -> new SupplyRequest(
                    rs.getLong("id"),
                    rs.getString("item_name"),
                    rs.getInt("quantity"),
                    rs.getString("status"),
                    rs.getString("requested_by")
            );

    // Save a new supply request
    public int saveRequest(SupplyRequest request) {

        String sql = """
                INSERT INTO requests
                (item_name, quantity, status, requested_by)
                VALUES (?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator statementCreator = connection -> {
            var statement = connection.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, request.getItemName());
            statement.setInt(2, request.getQuantity());
            statement.setString(3, request.getStatus());
            statement.setString(4, request.getRequestedBy());
            return statement;
        };

        int rowsInserted = jdbcTemplate.update(statementCreator, keyHolder);
        Number generatedId = keyHolder.getKey();
        if (generatedId == null) {
            throw new IllegalStateException("Database did not return a request ID");
        }
        request.setId(generatedId.longValue());
        return rowsInserted;
    }

    // Retrieve all supply requests
    public List<SupplyRequest> getAllRequests() {

        String sql = """
                SELECT id, item_name, quantity, status, requested_by
                FROM requests
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, requestRowMapper);
    }

    // Find a request using its ID
    public SupplyRequest findById(Long id) {

        String sql = """
                SELECT id, item_name, quantity, status, requested_by
                FROM requests
                WHERE id = ?
                """;

        List<SupplyRequest> requests = jdbcTemplate.query(
                sql,
                requestRowMapper,
                id
        );

        return requests.isEmpty() ? null : requests.get(0);
    }
}

