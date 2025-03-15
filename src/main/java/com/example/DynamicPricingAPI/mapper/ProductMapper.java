package com.example.DynamicPricingAPI.mapper;

import com.example.DynamicPricingAPI.model.Product;
import com.example.DynamicPricingAPI.model.ProductDTO;
import com.example.DynamicPricingAPI.model.Category;
import com.example.DynamicPricingAPI.Service.CategoryService;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final CategoryService categoryService;

    public ProductMapper(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setBasePrice(product.getBasePrice());
        dto.setStock(product.getStock());
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null); // ✅ Now stores categoryId
        dto.setImageUrl(product.getImageUrl());
        return dto;
    }

    public Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setBasePrice(dto.getBasePrice());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());

        // ✅ Set Category if categoryId is provided
        if (dto.getCategoryId() != null) {
            Category category = categoryService.findById(dto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found for ID: " + dto.getCategoryId()));
            product.setCategory(category);
        }
        return product;
    }
}

