
package com.example.demo.service;

import com.example.demo.dao.FuelEntryDao;
import com.example.demo.dao.VehicleDao;
import com.example.demo.model.FuelEntry;
import com.example.demo.model.Vehicle;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FuelService {

    // Example threshold; problem statement does not specify one
    private static final double LOW_MILEAGE_LIMIT = 5.0;

    private final FuelEntryDao fuelEntryDao;
    private final VehicleDao vehicleDao;

    public FuelService(
            FuelEntryDao fuelEntryDao,
            VehicleDao vehicleDao) {

        this.fuelEntryDao = fuelEntryDao;
        this.vehicleDao = vehicleDao;
    }

    // Add fuel and update vehicle odometer
    @Transactional
    public void addFuelEntry(
            String regNumber,
            BigDecimal litres,
            BigDecimal cost,
            int odometer) {

        if (litres.signum() <= 0 || cost.signum() < 0) {
            throw new IllegalArgumentException(
                    "Litres must be positive and cost cannot be negative.");
        }

        Vehicle vehicle = vehicleDao.findByRegNumber(regNumber)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Vehicle not found."));

        if (odometer < vehicle.getCurrentOdometer()) {
            throw new IllegalArgumentException(
                    "New odometer cannot be less than current odometer.");
        }

        FuelEntry entry = new FuelEntry();
        entry.setRegNumber(regNumber);
        entry.setLitres(litres);
        entry.setCost(cost);
        entry.setOdometerReading(odometer);

        fuelEntryDao.saveFuelEntry(entry);

        vehicleDao.updateCurrentOdometer(regNumber, odometer);
    }

    // Calculate mileage between consecutive fuel logs
    public double calculateMileage(String regNumber) {

        List<FuelEntry> entries =
                fuelEntryDao.findLatestTwo(regNumber);

        if (entries.size() < 2) {
            throw new IllegalStateException(
                    "At least two fuel entries are required.");
        }

        FuelEntry latest = entries.get(0);
        FuelEntry previous = entries.get(1);

        int distance =
                latest.getOdometerReading()
                        - previous.getOdometerReading();

        if (distance <= 0) {
            throw new IllegalStateException(
                    "Odometer readings must increase.");
        }

        return distance / latest.getLitres().doubleValue();
    }

    public boolean isLowMileage(double mileage) {
        return mileage < LOW_MILEAGE_LIMIT;
    }
}
