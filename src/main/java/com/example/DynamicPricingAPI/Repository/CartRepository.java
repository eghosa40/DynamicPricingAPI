package com.example.DynamicPricingAPI.Repository;

import com.example.DynamicPricingAPI.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
