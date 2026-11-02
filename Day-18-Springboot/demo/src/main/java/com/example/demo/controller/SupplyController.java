
package com.example.demo.controller;

import com.example.demo.model.Supply;
import com.example.demo.service.SupplyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplies")
public class SupplyController {

    private final SupplyService supplyService;

    public SupplyController(SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    // Add or restock supplies
    @PostMapping
    public String addSupply(@RequestBody Supply supply) {
        supplyService.addSupply(supply);
        return "Supply added or restocked successfully";
    }

    // Get all supplies
    @GetMapping
    public List<Supply> getAllSupplies() {
        return supplyService.getAllSupplies();
    }
}
