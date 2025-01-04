package com.example.DynamicPricingAPI.Service;

import com.example.DynamicPricingAPI.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product getProductById(Long id); //test
    List<Product> getAllProducts();
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    List<Product> searchProductByName(String name);
}
