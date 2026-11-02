
package com.example.demo.dao;

import com.example.demo.model.Vehicle;
import com.example.demo.rowmapper.VehicleRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VehicleDao {

    private final JdbcTemplate jdbcTemplate;

    public VehicleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Register a new vehicle
    public int saveVehicle(Vehicle vehicle) {

        String sql = """
                INSERT INTO vehicles
                (reg_number, model, current_odometer,
                 last_service_odometer, status)
                VALUES (?, ?, ?, ?, ?)
                """;

        return jdbcTemplate.update(
                sql,
                vehicle.getRegNumber(),
                vehicle.getModel(),
                vehicle.getCurrentOdometer(),
                vehicle.getLastServiceOdometer(),
                vehicle.getStatus()
        );
    }

    // Display all vehicles
    public List<Vehicle> findAll() {

        String sql = "SELECT * FROM vehicles ORDER BY reg_number";

        return jdbcTemplate.query(sql, new VehicleRowMapper());
    }

    // Find vehicle by registration number
    public Optional<Vehicle> findByRegNumber(String regNumber) {

        String sql = """
                SELECT * FROM vehicles
                WHERE reg_number = ?
                """;

        List<Vehicle> vehicles = jdbcTemplate.query(
                sql, new VehicleRowMapper(), regNumber);

        return vehicles.stream().findFirst();
    }

    // Update current odometer
    public int updateCurrentOdometer(
            String regNumber, int odometer) {

        String sql = """
                UPDATE vehicles
                SET current_odometer = ?
                WHERE reg_number = ?
                """;

        return jdbcTemplate.update(sql, odometer, regNumber);
    }

    // Update last service odometer
    public int updateLastServiceOdometer(
            String regNumber, int odometer) {

        String sql = """
                UPDATE vehicles
                SET last_service_odometer = ?
                WHERE reg_number = ?
                """;

        return jdbcTemplate.update(sql, odometer, regNumber);
    }

    // Find vehicles requiring maintenance
    public List<Vehicle> findVehiclesDueForService() {

        String sql = """
                SELECT * FROM vehicles
                WHERE current_odometer - last_service_odometer >= 5000
                AND status != 'DECOMMISSIONED'
                """;

        return jdbcTemplate.query(sql, new VehicleRowMapper());
    }
}
