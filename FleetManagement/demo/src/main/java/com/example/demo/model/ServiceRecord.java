
package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ServiceRecord {

    private int serviceId;
    private String regNumber;
    private String serviceType;
    private BigDecimal serviceCost;
    private int serviceOdometer;
    private LocalDate serviceDate;

    public ServiceRecord() {
    }

    public ServiceRecord(int serviceId, String regNumber,
                         String serviceType, BigDecimal serviceCost,
                         int serviceOdometer, LocalDate serviceDate) {
        this.serviceId = serviceId;
        this.regNumber = regNumber;
        this.serviceType = serviceType;
        this.serviceCost = serviceCost;
        this.serviceOdometer = serviceOdometer;
        this.serviceDate = serviceDate;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public BigDecimal getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(BigDecimal serviceCost) {
        this.serviceCost = serviceCost;
    }

    public int getServiceOdometer() {
        return serviceOdometer;
    }

    public void setServiceOdometer(int serviceOdometer) {
        this.serviceOdometer = serviceOdometer;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }
}
