package com.example.DynamicPricingAPI.Repository;

import com.example.DynamicPricingAPI.model.Category;
import com.example.DynamicPricingAPI.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);
    List<Product> findByOrderByBasePriceAsc();
    List<Product> findByOrderByBasePriceDesc();
    List<Product> findByNameContainingIgnoreCase(String keyword);
}
