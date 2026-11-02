
package com.example.demo.service;

import com.example.demo.model.Supply;
import com.example.demo.repository.SupplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyService {

    private final SupplyRepository supplyRepository;

    public SupplyService(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    // Add a new supply or restock an existing supply
    public int addSupply(Supply supply) {

        if (supply.getItemName() == null
                || supply.getItemName().isBlank()) {
            throw new IllegalArgumentException(
                    "Item name cannot be empty"
            );
        }

        if (supply.getQuantity() <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero"
            );
        }

        supply.setItemName(supply.getItemName().trim());

        return supplyRepository.addSupply(supply);
    }

    // Get all supplies
    public List<Supply> getAllSupplies() {
        return supplyRepository.getAllSupplies();
    }
}
