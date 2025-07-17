package com.agriconnect.repositories;

import com.agriconnect.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Long, Product> {
}