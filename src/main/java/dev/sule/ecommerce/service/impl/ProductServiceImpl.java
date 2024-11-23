package dev.sule.ecommerce.service.impl;

import dev.sule.ecommerce.dto.ProductRequestDTO;
import dev.sule.ecommerce.model.Categories;
import dev.sule.ecommerce.model.Product;
import dev.sule.ecommerce.model.ProductImage;
import dev.sule.ecommerce.model.ProductStatus;
import dev.sule.ecommerce.repository.CategoriesRepository;
import dev.sule.ecommerce.repository.ProductRepository;
import dev.sule.ecommerce.service.ProductService;
import dev.sule.ecommerce.util.FileStorageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoriesRepository categoriesRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoriesRepository categoriesRepository) {
        this.productRepository = productRepository;
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public Product create(ProductRequestDTO productRequestDTO) {
        // Initialize the uploads directory if not already done
        FileStorageUtil.initializeDirectory();

        // Map the product request DTO to the product entity
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setDiscountPrice(productRequestDTO.getDiscountPrice());
        product.setQuantity(productRequestDTO.getQuantity());
        product.setRemainingQuantity(productRequestDTO.getRemainingQuantity());
        product.setSlug(productRequestDTO.getSlug());
        product.setStatus(ProductStatus.valueOf(productRequestDTO.getStatus().toUpperCase()));

        // Fetch the category
        Categories category = categoriesRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        product.setCategories(category);

        // Save each uploaded file to the directory and create ProductImage entities
        List<ProductImage> images = productRequestDTO.getImages().stream()
                .map(FileStorageUtil::saveImage) // Save each file and get the file name
                .map(fileName -> new ProductImage(null, "/uploads/" + fileName, product)) // Create ProductImage entity
                .collect(Collectors.toList());
        product.setImages(images);

        // Save the product to the database
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Override
    public Product updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        Product product = getProductById(id);

        // Update fields
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setDiscountPrice(productRequestDTO.getDiscountPrice());
        product.setQuantity(productRequestDTO.getQuantity());
        product.setRemainingQuantity(productRequestDTO.getRemainingQuantity());
        product.setSlug(productRequestDTO.getSlug());
        product.setStatus(ProductStatus.valueOf(productRequestDTO.getStatus().toUpperCase()));

        // Update category
        Categories category = categoriesRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        product.setCategories(category);

        // Update images (if provided)
        if (productRequestDTO.getImages() != null) {
            // Clear existing images
            product.getImages().clear();

            // Save new images and add them to the product
            List<ProductImage> images = productRequestDTO.getImages().stream()
                    .map(FileStorageUtil::saveImage)
                    .map(fileName -> new ProductImage(null, "/uploads/" + fileName, product))
                    .collect(Collectors.toList());
            product.getImages().addAll(images);
        }

        return productRepository.save(product);
    }


    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}
