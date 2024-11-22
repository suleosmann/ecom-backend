package dev.sule.ecommerce.controller;

import dev.sule.ecommerce.dto.CategoryRequestDTO;
import dev.sule.ecommerce.model.Categories;
import dev.sule.ecommerce.service.CategoriesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    private final CategoriesService categoriesService;

    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    // Create Category
    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {
        categoriesService.createCategories(categoryRequestDTO);
        return ResponseEntity.status(201).body("Category created successfully");
    }

    // Update Category
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequestDTO categoryRequestDTO) {
        categoriesService.updateCategories(id, categoryRequestDTO);
        return ResponseEntity.ok("Category updated successfully");
    }

    // Get All Categories
    @GetMapping
    public ResponseEntity<List<Categories>> getAllCategories() {
        List<Categories> categoriesList = categoriesService.findAll();
        return ResponseEntity.ok(categoriesList);
    }

    // Get Category by ID
    @GetMapping("/{id}")
    public ResponseEntity<Categories> getCategoryById(@PathVariable Long id) {
        return categoriesService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    // Delete Category
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoriesService.deleteById(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
}