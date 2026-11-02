
package com.example.demo.service;

import com.example.demo.dao.VehicleDao;
import com.example.demo.dao.ServiceRecordDao;
import com.example.demo.model.Vehicle;
import com.example.demo.model.ServiceRecord;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleDao vehicleDao;
    private final ServiceRecordDao serviceRecordDao;

    public VehicleService(
            VehicleDao vehicleDao,
            ServiceRecordDao serviceRecordDao) {

        this.vehicleDao = vehicleDao;
        this.serviceRecordDao = serviceRecordDao;
    }

    // Register vehicle with its baseline service record
    @Transactional
    public void registerVehicle(
            String regNumber,
            String model,
            int initialOdometer,
            BigDecimal serviceCost,
            LocalDate serviceDate) {

        if (initialOdometer < 0 || serviceCost.signum() < 0) {
            throw new IllegalArgumentException(
                    "Odometer and service cost cannot be negative.");
        }

        Vehicle vehicle = new Vehicle(
                regNumber,
                model,
                initialOdometer,
                initialOdometer,
                "ACTIVE"
        );

        vehicleDao.saveVehicle(vehicle);

        ServiceRecord record = new ServiceRecord(
                0,
                regNumber,
                "INITIAL_SERVICE",
                serviceCost,
                initialOdometer,
                serviceDate
        );

        serviceRecordDao.saveServiceRecord(record);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleDao.findAll();
    }

    public Optional<Vehicle> getVehicle(String regNumber) {
        return vehicleDao.findByRegNumber(regNumber);
    }
}
