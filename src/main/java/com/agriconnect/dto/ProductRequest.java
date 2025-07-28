package com.agriconnect.dto;

import lombok.Data;

import java.math.BigDecimal;
/**
 * Product Request
 */
@Data
public class ProductRequest {
    private String name;
    private Integer quantity;
    private BigDecimal unitPrice;
    private String imageUrl;
}