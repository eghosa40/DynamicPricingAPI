package com.example.DynamicPricingAPI.Service;

import com.example.DynamicPricingAPI.model.Cart;
import com.example.DynamicPricingAPI.model.CartItem;

import java.util.List;

public interface CartService {

    Cart createCart(Cart Cart);
    Cart getCartId(Long Id);
    List<CartItem> getAllCartItems(Long cartId);

    Cart updateCart(Long id, Cart cart);

    double calculateCartTotal(Long cartId);

    void deleteCart(Long id);

    CartItem addCartItem(Long cartId, CartItem cartItem);

    void removeCartItem(Long cartId, Long itemId);


}
