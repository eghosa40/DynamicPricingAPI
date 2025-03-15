package com.example.DynamicPricingAPI.Service.Implementation;

import com.example.DynamicPricingAPI.Repository.CartItemRepository;
import com.example.DynamicPricingAPI.Repository.CartRepository;
import com.example.DynamicPricingAPI.Repository.ProductRepository;
import com.example.DynamicPricingAPI.Service.CartService;
import com.example.DynamicPricingAPI.model.Cart;
import com.example.DynamicPricingAPI.model.CartItem;
import com.example.DynamicPricingAPI.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartServiceImpl implements CartService {
    //Construction injection
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }
    @Override
    public Cart createCart(Cart cart) {
        // Remove the user validation
        cart.setStatus("ACTIVE");
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartId(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + id));
    }

    @Override
    public List<CartItem> getAllCartItems(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

        // Fetch items associated with the cart
        return cartItemRepository.findByCart(cart);
    }

    @Override
    public Cart updateCart(Long id, Cart cart) {
        // Find the existing cart
        Cart existingCart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + id));

        // Update the cart's status or other fields
        if (cart.getStatus() != null && !cart.getStatus().isEmpty()) {
            existingCart.setStatus(cart.getStatus());
        }

        return cartRepository.save(existingCart);
    }

    @Override
    public double calculateCartTotal(Long cartId) {
        // Ensure the cart exists
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

        // Fetch items and calculate the total price
        List<CartItem> items = cartItemRepository.findByCart(cart);
        return items.stream().mapToDouble(item -> item.getQuantity() * item.getProduct().getBasePrice()).sum();
    }

    @Override
    public void deleteCart(Long id) {
        if (!cartRepository.existsById(id)) {
            throw new RuntimeException("Cart not found with ID: " + id);
        }
        cartRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CartItem addCartItem(Long cartId, CartItem cartItem) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

        Product product = productRepository.findById(cartItem.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + cartItem.getProduct().getId()));

        if (cartItem.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        if (product.getStock() < cartItem.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock available.");
        }

        // Check if item already exists in cart
        List<CartItem> existingItems = cartItemRepository.findByCart(cart);
        for (CartItem existingItem : existingItems) {
            if (existingItem.getProduct().getId().equals(product.getId())) {
                existingItem.setQuantity(existingItem.getQuantity() + cartItem.getQuantity());
                existingItem.setCalculatedPrice(existingItem.getQuantity() * product.getBasePrice());
                return cartItemRepository.save(existingItem);
            }
        }

        // Create new cart item
        cartItem.setCart(cart);
        cartItem.setCalculatedPrice(cartItem.getQuantity() * product.getBasePrice());
        return cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public void removeCartItem(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with ID: " + itemId));

        if (!cartItem.getCart().getId().equals(cartId)) {
            throw new IllegalArgumentException("Cart item does not belong to this cart.");
        }

        cartItemRepository.delete(cartItem);
    }
}
