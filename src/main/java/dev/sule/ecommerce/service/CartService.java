package dev.sule.ecommerce.service;

import dev.sule.ecommerce.dto.CartRequestDTO;
import dev.sule.ecommerce.model.Cart;

import java.util.List;

public interface CartService {
    Cart create(CartRequestDTO cartRequestDTO);
    List<Cart> getCartItems(String cartCode);
    void removeCartItem(String cartCode, Long productId);
    void clearCart(String cartCode);
}
