package com.smart.pantry.backend.application.dto;

import com.smart.pantry.backend.application.model.PantryItem;
import com.smart.pantry.backend.application.model.PantryItemStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PantryItemDTO {

    private Long id;
    private String name;
    private BigDecimal quantity;
    private String unit;
    private LocalDate purchaseDate;
    private LocalDate expiryDate;
    private PantryItemStatus status;

    // Constructor to map from the entity to the DTO
    public PantryItemDTO(PantryItem entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.quantity = entity.getQuantity();
        this.unit = entity.getUnit();
        this.purchaseDate = entity.getPurchaseDate();
        this.expiryDate = entity.getExpiryDate();
        this.status = entity.getStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public PantryItemStatus getStatus() {
        return status;
    }

    public void setStatus(PantryItemStatus status) {
        this.status = status;
    }
}
