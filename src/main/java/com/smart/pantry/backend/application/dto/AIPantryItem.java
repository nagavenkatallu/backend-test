package com.smart.pantry.backend.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class AIPantryItem {

    @JsonProperty("itemName")
    private String name;

    private BigDecimal quantity;
    private String unit;

    // 💡 FIX #1: Add a no-argument constructor for Jackson to use.
    public AIPantryItem() {
    }

    public AIPantryItem(String name, BigDecimal quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
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
}
