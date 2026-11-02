
package com.example.demo.dao;

import com.example.demo.model.FuelEntry;
import com.example.demo.rowmapper.FuelEntryRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FuelEntryDao {

    private final JdbcTemplate jdbcTemplate;

    public FuelEntryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Insert fuel entry
    public int saveFuelEntry(FuelEntry entry) {

        String sql = """
                INSERT INTO fuel_entries
                (reg_number, litres, cost, odometer_reading)
                VALUES (?, ?, ?, ?)
                """;

        return jdbcTemplate.update(
                sql,
                entry.getRegNumber(),
                entry.getLitres(),
                entry.getCost(),
                entry.getOdometerReading()
        );
    }

    // Get the latest two fuel entries
    public List<FuelEntry> findLatestTwo(String regNumber) {

        String sql = """
                SELECT * FROM fuel_entries
                WHERE reg_number = ?
                ORDER BY fuel_date DESC, entry_id DESC
                LIMIT 2
                """;

        return jdbcTemplate.query(
                sql, new FuelEntryRowMapper(), regNumber);
    }
}
