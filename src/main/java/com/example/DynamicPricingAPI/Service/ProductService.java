package com.example.DynamicPricingAPI.Service;

import com.example.DynamicPricingAPI.model.ProductDTO;
import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
    ProductDTO getProductById(Long id);
    List<ProductDTO> getAllProducts();
    List<ProductDTO> searchProductByName(String query);
    List<ProductDTO> filterProducts(String category, Double minPrice, Double maxPrice);
}

