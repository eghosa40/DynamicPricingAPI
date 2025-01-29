package com.example.DynamicPricingAPI.Service.Implementation;

import com.example.DynamicPricingAPI.Repository.CartItemRepository;
import com.example.DynamicPricingAPI.Repository.CartRepository;
import com.example.DynamicPricingAPI.Service.CartService;
import com.example.DynamicPricingAPI.model.Cart;
import com.example.DynamicPricingAPI.model.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartServiceImpl implements CartService {
    //Construction injection
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository){
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
    }
    @Override
    public Cart createCart(Cart cart) {
        if (cart.getUser() == null) {
            throw new IllegalArgumentException("Cart must be associated with a user.");
        }

        // Initialize cart fields
        cart.setStatus("ACTIVE");
        return cartRepository.save(cart);    }

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
}
