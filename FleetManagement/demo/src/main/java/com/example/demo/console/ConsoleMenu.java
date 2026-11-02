
package com.example.demo.console;

import com.example.demo.model.Vehicle;
import com.example.demo.model.ServiceRecord;
import com.example.demo.service.VehicleService;
import com.example.demo.service.FuelService;
import com.example.demo.service.MaintenanceService;
import com.example.demo.service.AuditService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class ConsoleMenu implements CommandLineRunner {

    private final VehicleService vehicleService;
    private final FuelService fuelService;
    private final MaintenanceService maintenanceService;
    private final AuditService auditService;

    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(
            VehicleService vehicleService,
            FuelService fuelService,
            MaintenanceService maintenanceService,
            AuditService auditService) {

        this.vehicleService = vehicleService;
        this.fuelService = fuelService;
        this.maintenanceService = maintenanceService;
        this.auditService = auditService;
    }

    @Override
    public void run(String... args) {

        int choice;

        do {
            System.out.println("\n====================================");
            System.out.println(" FLEET MAINTENANCE & FUEL AUDIT");
            System.out.println("====================================");
            System.out.println("1. Register Vehicle");
            System.out.println("2. View Fleet Dashboard");
            System.out.println("3. Add Fuel Entry");
            System.out.println("4. Calculate Fuel Mileage");
            System.out.println("5. Add Service Record");
            System.out.println("6. View Maintenance Alerts");
            System.out.println("7. View Cost Audit");
            System.out.println("8. View Service History");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> registerVehicle();
                    case 2 -> viewFleet();
                    case 3 -> addFuel();
                    case 4 -> calculateMileage();
                    case 5 -> addService();
                    case 6 -> maintenanceAlerts();
                    case 7 -> costAudit();
                    case 8 -> serviceHistory();
                    case 0 -> System.out.println("Application closed.");
                    default -> System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                choice = -1;
            }

        } while (choice != 0);
    }

    // Option 1: Register vehicle
    private void registerVehicle() {

        System.out.print("Enter registration number: ");
        String regNumber = scanner.nextLine().trim().toUpperCase();

        System.out.print("Enter vehicle model: ");
        String model = scanner.nextLine().trim();

        System.out.print("Enter initial odometer reading: ");
        int odometer = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter initial service cost: ");
        BigDecimal cost = new BigDecimal(scanner.nextLine());

        System.out.print("Enter initial service date (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        vehicleService.registerVehicle(
                regNumber, model, odometer, cost, date);

        System.out.println("Vehicle registered successfully!");
    }

    // Option 2: Display all vehicles
    private void viewFleet() {

        List<Vehicle> vehicles = vehicleService.getAllVehicles();

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles registered.");
            return;
        }

        System.out.println("\nFLEET DASHBOARD");

        for (Vehicle v : vehicles) {

            int distanceSinceService =
                    v.getCurrentOdometer()
                            - v.getLastServiceOdometer();

            String alert = distanceSinceService >= 5000
                    ? "DUE_FOR_SERVICE"
                    : "NOT_DUE";

            System.out.println("------------------------------------");
            System.out.println("Registration: " + v.getRegNumber());
            System.out.println("Model: " + v.getModel());
            System.out.println("Current Odometer: "
                    + v.getCurrentOdometer());
            System.out.println("Last Service Odometer: "
                    + v.getLastServiceOdometer());
            System.out.println("Status: " + v.getStatus());
            System.out.println("Maintenance: " + alert);
        }
    }

    // Option 3: Add fuel entry
    private void addFuel() {

        System.out.print("Enter vehicle registration: ");
        String regNumber = scanner.nextLine().trim().toUpperCase();

        System.out.print("Enter litres: ");
        BigDecimal litres = new BigDecimal(scanner.nextLine());

        System.out.print("Enter fuel cost: ");
        BigDecimal cost = new BigDecimal(scanner.nextLine());

        System.out.print("Enter current odometer reading: ");
        int odometer = Integer.parseInt(scanner.nextLine());

        fuelService.addFuelEntry(
                regNumber, litres, cost, odometer);

        System.out.println("Fuel entry added successfully!");
        System.out.println("Vehicle odometer updated successfully!");
    }

    // Option 4: Calculate mileage
    private void calculateMileage() {

        System.out.print("Enter vehicle registration: ");
        String regNumber = scanner.nextLine().trim().toUpperCase();

        double mileage = fuelService.calculateMileage(regNumber);

        System.out.printf("Fuel Mileage: %.2f km/litre%n", mileage);

        if (fuelService.isLowMileage(mileage)) {
            System.out.println("WARNING: Low mileage detected!");
        } else {
            System.out.println("Mileage is normal.");
        }
    }

    // Option 5: Add service record
    private void addService() {

        System.out.print("Enter vehicle registration: ");
        String regNumber = scanner.nextLine().trim().toUpperCase();

        System.out.print("Enter service type: ");
        String type = scanner.nextLine().trim().toUpperCase();

        System.out.print("Enter service cost: ");
        BigDecimal cost = new BigDecimal(scanner.nextLine());

        System.out.print("Enter service odometer: ");
        int odometer = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter service date (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        maintenanceService.addService(
                regNumber, type, cost, odometer, date);

        System.out.println("Service record added successfully!");
    }

    // Option 6: Maintenance alerts
    private void maintenanceAlerts() {

        List<Vehicle> vehicles =
                maintenanceService.getMaintenanceAlerts();

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles are due for service.");
            return;
        }

        System.out.println("\nVEHICLES DUE FOR SERVICE");

        for (Vehicle v : vehicles) {
            System.out.println(
                    v.getRegNumber() + " | "
                            + v.getModel() + " | "
                            + v.getCurrentOdometer() + " km | "
                            + "DUE_FOR_SERVICE"
            );
        }
    }

    // Option 7: Cost audit
    private void costAudit() {

        List<Map<String, Object>> summary =
                auditService.getCostSummary();

        System.out.println("\nVEHICLE COST AUDIT");

        for (Map<String, Object> row : summary) {

            BigDecimal fuelCost =
                    (BigDecimal) row.get("total_fuel_cost");

            BigDecimal serviceCost =
                    (BigDecimal) row.get("total_service_cost");

            BigDecimal totalCost =
                    fuelCost.add(serviceCost);

            System.out.println("------------------------------------");
            System.out.println("Registration: " + row.get("reg_number"));
            System.out.println("Model: " + row.get("model"));
            System.out.println("Fuel Expenditure: Rs. " + fuelCost);
            System.out.println("Service Expenditure: Rs. " + serviceCost);
            System.out.println("Total Expenditure: Rs. " + totalCost);
        }
    }

    // Option 8: View service history
    private void serviceHistory() {

        System.out.print("Enter vehicle registration: ");
        String regNumber = scanner.nextLine().trim().toUpperCase();

        List<ServiceRecord> records =
                maintenanceService.getServiceHistory(regNumber);

        if (records.isEmpty()) {
            System.out.println("No service records found.");
            return;
        }

        for (ServiceRecord record : records) {

            System.out.println("------------------------------------");
            System.out.println("Service ID: " + record.getServiceId());
            System.out.println("Type: " + record.getServiceType());
            System.out.println("Cost: Rs. " + record.getServiceCost());
            System.out.println("Odometer: "
                    + record.getServiceOdometer());
            System.out.println("Date: " + record.getServiceDate());
        }
    }
}
