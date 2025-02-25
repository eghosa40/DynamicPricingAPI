package com.example.DynamicPricingAPI.Controller;

import com.example.DynamicPricingAPI.Repository.CartRepository;
import com.example.DynamicPricingAPI.Service.CartService;
import com.example.DynamicPricingAPI.model.Cart;
import com.example.DynamicPricingAPI.model.CartItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        return ResponseEntity.ok(cartService.createCart(cart));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getCartId(id));
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getAllCartItems(id));
    }

    @PostMapping("/{cartId}/item")
    public ResponseEntity<CartItem> addCartItem(@PathVariable Long cartId, @RequestBody CartItem cartItem) {
        Cart cart = cartService.getCartId(cartId);
        cartItem.setCart(cart);
        return ResponseEntity.ok(cartService.addCartItem(cartId, cartItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody Cart cart) {
        return ResponseEntity.ok(cartService.updateCart(id, cart));
    }

    @DeleteMapping("/{cartId}/item/{itemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartId, @PathVariable Long itemId) {
        cartService.removeCartItem(cartId, itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/total")
    public ResponseEntity<Double> calculateCartTotal(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.calculateCartTotal(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }

}
