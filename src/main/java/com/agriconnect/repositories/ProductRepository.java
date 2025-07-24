package com.agriconnect.repositories;

import com.agriconnect.models.Product;
import com.agriconnect.util.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByStatus(ProductStatus status);
    List<Product> findByFarmerId(Long farmerId);

}