
package com.example.demo.dao;

import com.example.demo.model.ServiceRecord;
import com.example.demo.rowmapper.ServiceRecordRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServiceRecordDao {

    private final JdbcTemplate jdbcTemplate;

    public ServiceRecordDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Insert service record
    public int saveServiceRecord(ServiceRecord record) {

        String sql = """
                INSERT INTO service_records
                (reg_number, service_type, service_cost,
                 service_odometer, service_date)
                VALUES (?, ?, ?, ?, ?)
                """;

        return jdbcTemplate.update(
                sql,
                record.getRegNumber(),
                record.getServiceType(),
                record.getServiceCost(),
                record.getServiceOdometer(),
                record.getServiceDate()
        );
    }

    // Get all service records for one vehicle
    public List<ServiceRecord> findByVehicle(String regNumber) {

        String sql = """
                SELECT * FROM service_records
                WHERE reg_number = ?
                ORDER BY service_date DESC
                """;

        return jdbcTemplate.query(
                sql, new ServiceRecordRowMapper(), regNumber);
    }

    // Cost audit using SQL aggregate functions
    public List<java.util.Map<String, Object>> getCostSummary() {

        String sql = """
                SELECT
                    v.reg_number,
                    v.model,
                    COALESCE(
                        (SELECT SUM(f.cost)
                         FROM fuel_entries f
                         WHERE f.reg_number = v.reg_number), 0
                    ) AS total_fuel_cost,
                    COALESCE(
                        (SELECT SUM(s.service_cost)
                         FROM service_records s
                         WHERE s.reg_number = v.reg_number), 0
                    ) AS total_service_cost
                FROM vehicles v
                ORDER BY v.reg_number
                """;

        return jdbcTemplate.queryForList(sql);
    }
}
