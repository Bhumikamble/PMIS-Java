
package com.example.demo.controller;

import com.example.demo.model.SupplyRequest;
import com.example.demo.service.RequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    // Create a supply request
    @PostMapping
    public SupplyRequest createRequest(
            @RequestBody SupplyRequest request) {
        return requestService.createRequest(request);
    }

    // Get all supply requests
    @GetMapping
    public List<SupplyRequest> getAllRequests() {
        return requestService.getAllRequests();
    }
}
