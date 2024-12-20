package com.example.DynamicPricingAPI.Repository;

import com.example.DynamicPricingAPI.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ProductRepository extends JpaRepository<Product, Long> {

}
