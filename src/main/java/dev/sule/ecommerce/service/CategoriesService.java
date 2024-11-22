package dev.sule.ecommerce.service;

import dev.sule.ecommerce.dto.CategoryRequestDTO;
import dev.sule.ecommerce.model.Categories;

import java.util.List;
import java.util.Optional;

public interface CategoriesService {
    void createCategories(CategoryRequestDTO categoryRequestDTO);
    void updateCategories(Long id, CategoryRequestDTO categoryRequestDTO);
    List<Categories> findAll();
    Optional<Categories> findById(Long id);
    void deleteById(Long id);
}
