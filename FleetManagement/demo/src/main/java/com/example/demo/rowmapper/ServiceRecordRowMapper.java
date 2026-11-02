
package com.example.demo.rowmapper;

import com.example.demo.model.ServiceRecord;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceRecordRowMapper
        implements RowMapper<ServiceRecord> {

    @Override
    public ServiceRecord mapRow(ResultSet rs, int rowNum)
            throws SQLException {

        ServiceRecord record = new ServiceRecord();

        record.setServiceId(rs.getInt("service_id"));
        record.setRegNumber(rs.getString("reg_number"));
        record.setServiceType(rs.getString("service_type"));
        record.setServiceCost(rs.getBigDecimal("service_cost"));
        record.setServiceOdometer(
                rs.getInt("service_odometer"));
        record.setServiceDate(
                rs.getDate("service_date").toLocalDate());

        return record;
    }
}
