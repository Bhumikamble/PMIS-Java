
package com.example.demo.model;

public class SupplyRequest {

    private Long id;
    private String itemName;
    private int quantity;
    private String status;
    private String requestedBy;

    public SupplyRequest() {
    }

    public SupplyRequest(Long id, String itemName,
                         int quantity, String status,
                         String requestedBy) {
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.status = status;
        this.requestedBy = requestedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }
}
