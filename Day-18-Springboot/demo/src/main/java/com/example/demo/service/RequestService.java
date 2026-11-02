
package com.example.demo.service;

import com.example.demo.model.SupplyRequest;
import com.example.demo.repository.RequestRepository;
import com.example.demo.repository.SupplyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final SupplyRepository supplyRepository;

    public RequestService(
            RequestRepository requestRepository,
            SupplyRepository supplyRepository) {
        this.requestRepository = requestRepository;
        this.supplyRepository = supplyRepository;
    }

    // Process a request and update inventory in one transaction
    @Transactional
    public SupplyRequest createRequest(SupplyRequest request) {

        if (request.getItemName() == null
                || request.getItemName().isBlank()) {
            throw new IllegalArgumentException(
                    "Item name cannot be empty"
            );
        }

        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException(
                    "Requested quantity must be greater than zero"
            );
        }

        if (request.getRequestedBy() == null
                || request.getRequestedBy().isBlank()) {
            throw new IllegalArgumentException(
                    "Requester name cannot be empty"
            );
        }

        String itemName = request.getItemName().trim();
        request.setItemName(itemName);

        int updatedRows = supplyRepository.deductAvailableQuantity(
                itemName,
                request.getQuantity()
        );
        if (updatedRows == 0) {
            if (supplyRepository.findByItemName(itemName) == null) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Supply item not found: " + itemName
                );
            }
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Insufficient stock available"
            );
        }

        request.setStatus("APPROVED");

        requestRepository.saveRequest(request);

        return request;
    }

    // Retrieve all requests
    public List<SupplyRequest> getAllRequests() {
        return requestRepository.getAllRequests();
    }
}
