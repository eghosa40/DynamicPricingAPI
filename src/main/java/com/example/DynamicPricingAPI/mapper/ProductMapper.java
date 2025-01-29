package com.example.DynamicPricingAPI.mapper;

import com.example.DynamicPricingAPI.model.Product;
import com.example.DynamicPricingAPI.model.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getBasePrice());
        dto.setStock(product.getStock());
        dto.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : null);
        return dto;
    }

    public Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setBasePrice(dto.getPrice());
        product.setStock(dto.getStock());
        // Handle category mapping if needed
        return product;
    }
}
