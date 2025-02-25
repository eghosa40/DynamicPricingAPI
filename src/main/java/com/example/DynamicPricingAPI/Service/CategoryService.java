package com.example.DynamicPricingAPI.Service;

import com.example.DynamicPricingAPI.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(String name, String description);
    Optional<Category> findByName(String name);
    List<Category> getAllCategories();
}
