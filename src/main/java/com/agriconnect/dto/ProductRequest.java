package com.agriconnect.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;
    private String unit;
    private Integer quantity;
    private BigDecimal unitPrice;
    private String description;
    private String category;
    private String harvestDate;
    private String location;
    private String imageUrl;
}