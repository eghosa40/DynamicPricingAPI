package com.example.DynamicPricingAPI.Service.Implementation;


import com.example.DynamicPricingAPI.Repository.ProductRepository;
import com.example.DynamicPricingAPI.Service.ProductService;
import com.example.DynamicPricingAPI.model.Product;
import com.example.DynamicPricingAPI.model.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Convert Product to ProductDTO
    private ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getBasePrice());
        dto.setStock(product.getStock());
        dto.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : null);
        return dto;
    }

    // Convert ProductDTO to Product
    private Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setBasePrice(dto.getPrice());
        product.setStock(dto.getStock());
        // Note: Handle setting Category entity if needed.
        return product;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = toEntity(productDTO); // Convert DTO to entity
        Product savedProduct = productRepository.save(product); // Save entity
        return toDTO(savedProduct); // Convert saved entity back to DTO
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        // Fetch the existing product
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));

        // Update fields
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setBasePrice(productDTO.getPrice());
        existingProduct.setStock(productDTO.getStock());
        // Update category if necessary

        Product updatedProduct = productRepository.save(existingProduct);
        return toDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));
        return toDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toDTO) // Convert each product to DTO
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> searchProductByName(String query) {
        return productRepository.findByNameContainingIgnoreCase(query)
                .stream()
                .map(this::toDTO) // Convert each product to DTO
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> filterProducts(String category, Double minPrice, Double maxPrice) {
        return productRepository.findAll() // Fetch all products and filter them
                .stream()
                .filter(product -> (category == null || (product.getCategory() != null && product.getCategory().getName().equals(category))) &&
                        (minPrice == null || product.getBasePrice() >= minPrice) &&
                        (maxPrice == null || product.getBasePrice() <= maxPrice))
                .map(this::toDTO) // Convert each filtered product to DTO
                .collect(Collectors.toList());
    }
}

