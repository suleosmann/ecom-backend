package dev.sule.ecommerce.controller;

import dev.sule.ecommerce.dto.ProductRequestDTO;
import dev.sule.ecommerce.dto.ProductResponseDTO;
import dev.sule.ecommerce.model.Product;
import dev.sule.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@ModelAttribute @Valid ProductRequestDTO productRequestDTO) {
        Product product = productService.create(productRequestDTO);

        // Map product entity to ProductResponseDTO
        ProductResponseDTO response = mapToResponseDTO(product);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        ProductResponseDTO response = mapToResponseDTO(product);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @ModelAttribute @Valid ProductRequestDTO productRequestDTO) {
        Product updatedProduct = productService.updateProduct(id, productRequestDTO);
        ProductResponseDTO response = mapToResponseDTO(updatedProduct);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    private ProductResponseDTO mapToResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice().toString(),
                product.getDiscountPrice() != null ? product.getDiscountPrice().toString() : null,
                product.getQuantity(),
                product.getRemainingQuantity(),
                product.getSlug(),
                product.getStatus().toString(),
                product.getCategories().getId(),
                product.getImages().stream().map(image -> image.getUrl()).collect(Collectors.toList())
        );
    }
}
