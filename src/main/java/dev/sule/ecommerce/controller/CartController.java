package dev.sule.ecommerce.controller;

import dev.sule.ecommerce.dto.CartRequestDTO;
import dev.sule.ecommerce.dto.CartResponseDTO;
import dev.sule.ecommerce.model.Cart;
import dev.sule.ecommerce.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Add or update a product in the cart.
     */
    @PostMapping
    public ResponseEntity<CartResponseDTO> addOrUpdateCartItem(@RequestBody @Valid CartRequestDTO cartRequestDTO) {
        Cart createdCart = cartService.create(cartRequestDTO);

        // Map the Cart entity to CartResponseDTO
        CartResponseDTO responseDTO = new CartResponseDTO(
                createdCart.getCartCode(),
                createdCart.getProduct().getId(),
                createdCart.getProduct().getName(),
                createdCart.getQuantity(),
                createdCart.getProduct().getPrice().doubleValue(),
                createdCart.getQuantity() * createdCart.getProduct().getPrice().doubleValue()
        );

        return ResponseEntity.ok(responseDTO);
    }


    /**
     * Retrieve all items in the cart by cartCode.
     */
    @GetMapping("/{cartCode}")
    public ResponseEntity<List<CartResponseDTO>> getCartItems(@PathVariable String cartCode) {
        List<Cart> cartItems = cartService.getCartItems(cartCode);

        // Map Cart entities to CartResponseDTO
        List<CartResponseDTO> response = cartItems.stream()
                .map(cart -> new CartResponseDTO(
                        cart.getCartCode(),
                        cart.getProduct().getId(),
                        cart.getProduct().getName(),
                        cart.getQuantity(),
                        cart.getProduct().getPrice().doubleValue(),
                        cart.getQuantity() * cart.getProduct().getPrice().doubleValue()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * Remove a specific product from the cart.
     */
    @DeleteMapping("/{cartCode}/product/{productId}")
    public ResponseEntity<String> removeCartItem(@PathVariable String cartCode, @PathVariable Long productId) {
        cartService.removeCartItem(cartCode, productId);
        return ResponseEntity.ok("Product removed from the cart successfully.");
    }

    /**
     * Clear all items in the cart by cartCode.
     */
    @DeleteMapping("/{cartCode}")
    public ResponseEntity<String> clearCart(@PathVariable String cartCode) {
        cartService.clearCart(cartCode);
        return ResponseEntity.ok("Cart cleared successfully.");
    }
}
