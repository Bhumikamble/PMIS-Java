
package com.example.demo.rowmapper;

import com.example.demo.model.Vehicle;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleRowMapper implements RowMapper<Vehicle> {

    @Override
    public Vehicle mapRow(ResultSet rs, int rowNum)
            throws SQLException {

        Vehicle vehicle = new Vehicle();

        vehicle.setRegNumber(rs.getString("reg_number"));
        vehicle.setModel(rs.getString("model"));
        vehicle.setCurrentOdometer(rs.getInt("current_odometer"));
        vehicle.setLastServiceOdometer(
                rs.getInt("last_service_odometer"));
        vehicle.setStatus(rs.getString("status"));

        return vehicle;
    }
}

