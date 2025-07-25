package com.agriconnect.controllers;

import com.agriconnect.dto.ApiResponse;
import com.agriconnect.dto.ProductRequest;
import com.agriconnect.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://agri-linkk.netlify.app")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping("/listing")
    @PreAuthorize("hasRole('FARMER')")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductRequest request,
                                                  @RequestParam("farmerId") Long farmerId) {
        ApiResponse response = productService.addProduct(request, farmerId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/publish/{productId}")
    @PreAuthorize("hasRole('FARMER')")
    public ResponseEntity<ApiResponse> publishProduct(@PathVariable("productId") Long productId) {
        ApiResponse response = productService.publishProduct(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/published")
    public ResponseEntity<ApiResponse> getPublishedProducts() {
        ApiResponse response = productService.getAllPublishedProducts();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductWithFarmer(@PathVariable("productId") Long productId) {
        ApiResponse response = productService.getProductById(productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-products")
    @PreAuthorize("hasRole('FARMER')")
    public ResponseEntity<ApiResponse> getFarmerProducts(@RequestParam("farmerId") Long farmerId) {
        ApiResponse response = productService.getProductsByFarmer(farmerId);
        return ResponseEntity.ok(response);
    }
}
