package com.agriconnect.services;

import com.agriconnect.dto.ApiResponse;
import com.agriconnect.dto.ProductRequest;
import com.agriconnect.models.Product;
import com.agriconnect.models.User;
import com.agriconnect.repositories.ProductRepository;
import com.agriconnect.repositories.UserRepository;
import com.agriconnect.util.ProductStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Transactional
    public ApiResponse addProduct(ProductRequest request,Long userId) {
        User farmer = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Farmer not found"));

        Product product = Product.builder()
                .name(request.getName())
                .unit(request.getUnit())
                .quantity(request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .description(request.getDescription())
                .category(request.getCategory())
                .harvestDate(request.getHarvestDate())
                .location(request.getLocation())
                .imageBase64(request.getImageUrl())
                .status(ProductStatus.LISTED)
                .farmer(farmer)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(60))
                .build();

       productRepository.save(product);
        return ApiResponse.success("Product added successfully", product.getId());
    }
    @Transactional
    public ApiResponse publishProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStatus(ProductStatus.PUBLISHED);
        productRepository.save(product);
        return ApiResponse.success("Product published successfully", product.getId());
    }
    @Transactional
    public ApiResponse getAllPublishedProducts() {
        List<Product> products = productRepository.findByStatus(ProductStatus.PUBLISHED);
        return ApiResponse.success("Published products fetched", products);
    }
    @Transactional
    public ApiResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return ApiResponse.success("Product fetched", product);
    }
    @Transactional
    public ApiResponse getProductsByFarmer(Long farmerId) {
        List<Product> products = productRepository.findByFarmerId(farmerId);
        return ApiResponse.success("Farmer products fetched", products);
    }


}