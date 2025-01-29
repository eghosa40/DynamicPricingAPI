package com.example.DynamicPricingAPI.Service;

import com.example.DynamicPricingAPI.Repository.ProductRepository;
import com.example.DynamicPricingAPI.Service.Implementation.ProductServiceImpl;
import com.example.DynamicPricingAPI.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Already in your code
class ProductServiceTest {

    @Mock // Mocks the repository without involving actual database
    private ProductRepository productRepository;

    @InjectMocks // Automatically injects mocked dependencies into ProductServiceImpl
    private ProductServiceImpl productService;

    @Test
    void testGetProductById_Success() {
        // Arrange
        Long productId = 1L;
        Product mockProduct = new Product(productId, "Test Product", "Description", 100.0, 10, null, null);
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // Act
        Product result = productService.getProductById(productId);

        // Assert
        assertNotNull(result); // Ensure result is not null
        assertEquals("Test Product", result.getName()); // Verify the product name
        verify(productRepository, times(1)).findById(productId); // Ensure findById was called once
    }

    @Test
    void testGetProductById_NotFound() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> productService.getProductById(productId));
        assertEquals("Product not found with ID: 1", exception.getMessage());
        verify(productRepository, times(1)).findById(productId); // Ensure findById was called once
    }
}



