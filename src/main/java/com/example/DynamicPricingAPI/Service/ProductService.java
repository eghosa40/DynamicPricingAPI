package com.example.DynamicPricingAPI.Service;

import com.example.DynamicPricingAPI.model.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO getProductById(Long id); //test
    List<ProductDTO> getAllProducts();
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
    List<ProductDTO> searchProductByName(String name);
    List<ProductDTO> filterProducts(String category, Double minPrice, Double maxPrice);
}
