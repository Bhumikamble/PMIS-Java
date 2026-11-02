
package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FuelEntry {

    private int entryId;
    private String regNumber;
    private BigDecimal litres;
    private BigDecimal cost;
    private int odometerReading;
    private LocalDateTime fuelDate;

    public FuelEntry() {
    }

    public FuelEntry(int entryId, String regNumber,
                     BigDecimal litres, BigDecimal cost,
                     int odometerReading, LocalDateTime fuelDate) {
        this.entryId = entryId;
        this.regNumber = regNumber;
        this.litres = litres;
        this.cost = cost;
        this.odometerReading = odometerReading;
        this.fuelDate = fuelDate;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public BigDecimal getLitres() {
        return litres;
    }

    public void setLitres(BigDecimal litres) {
        this.litres = litres;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getOdometerReading() {
        return odometerReading;
    }

    public void setOdometerReading(int odometerReading) {
        this.odometerReading = odometerReading;
    }

    public LocalDateTime getFuelDate() {
        return fuelDate;
    }

    public void setFuelDate(LocalDateTime fuelDate) {
        this.fuelDate = fuelDate;
    }
}
