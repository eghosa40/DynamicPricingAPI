package com.example.DynamicPricingAPI.Service.Implementation;

import com.example.DynamicPricingAPI.Repository.ProductRepository;
import com.example.DynamicPricingAPI.Service.CategoryService;
import com.example.DynamicPricingAPI.Service.ProductService;
import com.example.DynamicPricingAPI.model.Category;
import com.example.DynamicPricingAPI.model.Product;
import com.example.DynamicPricingAPI.model.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    // Convert Product to ProductDTO
    private ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setBasePrice(product.getBasePrice());
        dto.setStock(product.getStock());
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null); // ✅ Store category as ID
        dto.setImageUrl(product.getImageUrl());
        return dto;
    }

    // Convert ProductDTO to Product
    private Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setBasePrice(dto.getBasePrice());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());

        // ✅ Set category if provided
        if (dto.getCategoryId() != null) {
            Category category = categoryService.findById(dto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found for ID: " + dto.getCategoryId()));
            product.setCategory(category);
        }
        return product;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        System.out.println("[DEBUG] Creating Product: " + productDTO);

        Product product = toEntity(productDTO);
        Product savedProduct = productRepository.save(product);

        System.out.println("[DEBUG] Product Created: " + savedProduct);
        return toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        System.out.println("[DEBUG] Updating Product ID: " + id);

        // Fetch the existing product
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + id));

        // Update fields
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setBasePrice(productDTO.getBasePrice());
        existingProduct.setStock(productDTO.getStock());
        existingProduct.setImageUrl(productDTO.getImageUrl());

        // ✅ Update category if necessary
        if (productDTO.getCategoryId() != null) {
            Category category = categoryService.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found for ID: " + productDTO.getCategoryId()));
            existingProduct.setCategory(category);
        }

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


