package com.example.DynamicPricingAPI.Controller;

import com.example.DynamicPricingAPI.Repository.ProductRepository;
import com.example.DynamicPricingAPI.model.Product;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository repository;

    public ProductController() {
        this.repository = new ProductRepository();
    }

    @PostMapping
    @Valid
    public Product createProduct(@RequestBody Product product) {
        return repository.save(product);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @GetMapping
    public Map<Long, Product> getAllProducts() {
        return repository.findAll();
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id); // Ensure the product has the correct ID
        return repository.update(product);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        boolean wasDeleted = repository.deleteById(id);
        if (wasDeleted) {
            return "Product with ID " + id + " was deleted successfully.";
        } else {
            return "Product with ID " + id + " not found.";
        }
    }
}
