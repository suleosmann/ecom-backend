package dev.sule.ecommerce.service.impl;

import dev.sule.ecommerce.dto.CartRequestDTO;
import dev.sule.ecommerce.model.Cart;
import dev.sule.ecommerce.model.Product;
import dev.sule.ecommerce.repository.CartRepository;
import dev.sule.ecommerce.repository.ProductRepository;
import dev.sule.ecommerce.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Cart create(CartRequestDTO cartRequestDTO) {
        // Check if the product exists
        Product product = productRepository.findById(cartRequestDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Validate the requested quantity does not exceed available stock
        if (cartRequestDTO.getQuantity() > product.getQuantity()) {
            throw new IllegalArgumentException("Requested quantity exceeds available stock");
        }

        // Check if the product is already in the cart with the same cartCode
        Cart existingCartItem = cartRepository.findByCartCodeAndProductId(
                cartRequestDTO.getCartCode(),
                cartRequestDTO.getProductId()
        );

        if (existingCartItem != null) {
            // Overwrite the quantity with the new value
            existingCartItem.setQuantity(cartRequestDTO.getQuantity());
            return cartRepository.save(existingCartItem);
        } else {
            // Create a new cart item if the product is not already in the cart
            Cart cart = new Cart();
            cart.setCartCode(cartRequestDTO.getCartCode());
            cart.setProduct(product);
            cart.setQuantity(cartRequestDTO.getQuantity());
            return cartRepository.save(cart);
        }
    }


    @Override
    public List<Cart> getCartItems(String cartCode) {
        return cartRepository.findAllByCartCode(cartCode);
    }

    @Override
    @Transactional
    public void removeCartItem(String cartCode, Long productId) {
        Cart cartItem = cartRepository.findByCartCodeAndProductId(cartCode, productId);
        if (cartItem != null) {
            cartRepository.delete(cartItem);
        } else {
            throw new IllegalArgumentException("Cart item not found");
        }
    }

    @Override
    @Transactional
    public void clearCart(String cartCode) {
        List<Cart> cartItems = cartRepository.findAllByCartCode(cartCode);
        if (!cartItems.isEmpty()) {
            cartRepository.deleteAll(cartItems);
        }
    }
}
