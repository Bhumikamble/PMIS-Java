
package com.example.demo.rowmapper;

import com.example.demo.model.FuelEntry;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FuelEntryRowMapper implements RowMapper<FuelEntry> {

    @Override
    public FuelEntry mapRow(ResultSet rs, int rowNum)
            throws SQLException {

        FuelEntry entry = new FuelEntry();

        entry.setEntryId(rs.getInt("entry_id"));
        entry.setRegNumber(rs.getString("reg_number"));
        entry.setLitres(rs.getBigDecimal("litres"));
        entry.setCost(rs.getBigDecimal("cost"));
        entry.setOdometerReading(
                rs.getInt("odometer_reading"));

        if (rs.getTimestamp("fuel_date") != null) {
            entry.setFuelDate(
                    rs.getTimestamp("fuel_date").toLocalDateTime());
        }

        return entry;
    }
}
