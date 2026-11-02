
package com.example.demo.service;

import com.example.demo.dao.ServiceRecordDao;
import com.example.demo.dao.VehicleDao;
import com.example.demo.model.ServiceRecord;
import com.example.demo.model.Vehicle;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class MaintenanceService {

    private final VehicleDao vehicleDao;
    private final ServiceRecordDao serviceRecordDao;

    public MaintenanceService(
            VehicleDao vehicleDao,
            ServiceRecordDao serviceRecordDao) {

        this.vehicleDao = vehicleDao;
        this.serviceRecordDao = serviceRecordDao;
    }

    // Add a service record and update last service odometer
    @Transactional
    public void addService(
            String regNumber,
            String serviceType,
            BigDecimal cost,
            int odometer,
            LocalDate date) {

        Vehicle vehicle = vehicleDao.findByRegNumber(regNumber)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Vehicle not found."));

        if (cost.signum() < 0) {
            throw new IllegalArgumentException(
                    "Service cost cannot be negative.");
        }

        if (odometer < vehicle.getLastServiceOdometer()
                || odometer > vehicle.getCurrentOdometer()) {
            throw new IllegalArgumentException(
                    "Service odometer must be between the last "
                            + "service and current odometer.");
        }

        ServiceRecord record = new ServiceRecord(
                0,
                regNumber,
                serviceType,
                cost,
                odometer,
                date
        );

        serviceRecordDao.saveServiceRecord(record);

        vehicleDao.updateLastServiceOdometer(regNumber, odometer);
    }

    // Get vehicles due for service
    public List<Vehicle> getMaintenanceAlerts() {
        return vehicleDao.findVehiclesDueForService();
    }

    // Get service history
    public List<ServiceRecord> getServiceHistory(String regNumber) {
        return serviceRecordDao.findByVehicle(regNumber);
    }
}
