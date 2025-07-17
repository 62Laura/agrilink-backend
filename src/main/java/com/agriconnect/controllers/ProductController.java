package com.agriconnect.controllers;

import com.agriconnect.dto.ApiResponse;
import com.agriconnect.dto.ProductRequest;
import com.agriconnect.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    ProductService productService;
    @PostMapping("/listing")
    @PreAuthorize("hasRole('FARMER')")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductRequest request, Long farmerId) {
        ApiResponse response = productService.addProduct(request, farmerId);
        return ResponseEntity.ok(response);
    }

}