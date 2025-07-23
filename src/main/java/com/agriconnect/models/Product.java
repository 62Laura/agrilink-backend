package com.agriconnect.models;

import com.agriconnect.util.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_id", referencedColumnName = "id")
    private User farmer;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @NotBlank(message = "Unit is required")
    private String unit;

    @DecimalMin(value = "0.0", message = "Unit price must be positive")
    @Column(precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String category;
    private String harvestDate;
    private String location;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ProductStatus status = ProductStatus.LISTED;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    @Lob
    @Column(name = "image_base64")
    private String imageBase64;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}