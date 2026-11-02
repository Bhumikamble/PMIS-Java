
package com.example.demo.model;

public class Vehicle {

    private String regNumber;
    private String model;
    private int currentOdometer;
    private int lastServiceOdometer;
    private String status;

    public Vehicle() {
    }

    public Vehicle(String regNumber, String model,
                   int currentOdometer, int lastServiceOdometer,
                   String status) {
        this.regNumber = regNumber;
        this.model = model;
        this.currentOdometer = currentOdometer;
        this.lastServiceOdometer = lastServiceOdometer;
        this.status = status;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCurrentOdometer() {
        return currentOdometer;
    }

    public void setCurrentOdometer(int currentOdometer) {
        this.currentOdometer = currentOdometer;
    }

    public int getLastServiceOdometer() {
        return lastServiceOdometer;
    }

    public void setLastServiceOdometer(int lastServiceOdometer) {
        this.lastServiceOdometer = lastServiceOdometer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
