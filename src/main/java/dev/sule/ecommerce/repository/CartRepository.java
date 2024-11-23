package dev.sule.ecommerce.repository;

import dev.sule.ecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByCartCodeAndProductId(String cartCode, Long productId);
    List<Cart> findAllByCartCode(String cartCode);
}
