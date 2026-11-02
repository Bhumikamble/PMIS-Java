
package com.example.demo.repository;

import com.example.demo.model.Supply;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SupplyRepository {

    private final JdbcTemplate jdbcTemplate;

    public SupplyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Converts a database row into a Supply object
    private final RowMapper<Supply> supplyRowMapper = (rs, rowNum) ->
            new Supply(
                    rs.getLong("id"),
                    rs.getString("item_name"),
                    rs.getInt("quantity")
            );

    // Add a new supply or restock an existing supply
    public int addSupply(Supply supply) {

        Supply existing = findByItemName(supply.getItemName());

        if (existing == null) {
            String sql = """
                    INSERT INTO supplies (item_name, quantity)
                    VALUES (?, ?)
                    """;

            return jdbcTemplate.update(
                    sql,
                    supply.getItemName(),
                    supply.getQuantity()
            );
        }

        String sql = """
                UPDATE supplies
                SET quantity = quantity + ?
                WHERE item_name = ?
                """;

        return jdbcTemplate.update(
                sql,
                supply.getQuantity(),
                supply.getItemName()
        );
    }

    // Retrieve all supplies
    public List<Supply> getAllSupplies() {

        String sql = """
                SELECT id, item_name, quantity
                FROM supplies
                ORDER BY id
                """;

        return jdbcTemplate.query(sql, supplyRowMapper);
    }

    // Find a supply using its name
    public Supply findByItemName(String itemName) {

        String sql = """
                SELECT id, item_name, quantity
                FROM supplies
                WHERE item_name = ?
                """;

        List<Supply> supplies = jdbcTemplate.query(
                sql,
                supplyRowMapper,
                itemName
        );

        if (supplies.isEmpty()) {
            return null;
        }

        return supplies.get(0);
    }

    public int deductAvailableQuantity(String itemName, int requestedQuantity) {
        String sql = """
                UPDATE supplies
                SET quantity = quantity - ?
                WHERE item_name = ? AND quantity >= ?
                """;

        return jdbcTemplate.update(
                sql,
                requestedQuantity,
                itemName,
                requestedQuantity
        );
    }
}
