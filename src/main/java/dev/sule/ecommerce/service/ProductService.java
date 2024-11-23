package dev.sule.ecommerce.service;

import dev.sule.ecommerce.dto.ProductRequestDTO;
import dev.sule.ecommerce.model.Product;

import java.util.List;

public interface ProductService {
    Product create(ProductRequestDTO productRequestDTO);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product updateProduct(Long id, ProductRequestDTO productRequestDTO);
    void deleteProduct(Long id);
}
