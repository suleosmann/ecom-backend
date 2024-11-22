package dev.sule.ecommerce.service.impl;

import dev.sule.ecommerce.dto.CategoryRequestDTO;
import dev.sule.ecommerce.model.Categories;
import dev.sule.ecommerce.repository.CategoriesRepository;
import dev.sule.ecommerce.service.CategoriesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesServiceImpl implements CategoriesService {


    private CategoriesRepository categoriesRepository;

    public CategoriesServiceImpl( CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }


    @Override
    public void createCategories(CategoryRequestDTO categoryRequestDTO) {
        Optional<Categories> existingCategory = categoriesRepository.findByName(categoryRequestDTO.getName());
        if (existingCategory.isPresent()) {
            throw new IllegalArgumentException("Category already exists");
        }

        Categories categories = new Categories();
        categories.setName(categoryRequestDTO.getName());
        categories.setDescription(categoryRequestDTO.getDescription());

        // Fetch parent category if parentCategoryId is provided
        if (categoryRequestDTO.getParentCategoryId() != null) {
            Categories parentCategory = categoriesRepository.findById(categoryRequestDTO.getParentCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent category not found"));
            categories.setParentCategory(parentCategory);
        } else {
            categories.setParentCategory(null); // No parent category (top-level category)
        }

        categoriesRepository.save(categories);
    }

    @Override
    public void updateCategories(Long id, CategoryRequestDTO categoryRequestDTO){
        Optional<Categories> existingCategory = categoriesRepository.findById(id);
        if (existingCategory.isEmpty()){
            throw new IllegalArgumentException("Category not Found");
        }

        Categories categories = existingCategory.get();

        categories.setName(categoryRequestDTO.getName());
        categories.setDescription(categories.getDescription());

        if(categoryRequestDTO.getParentCategoryId() != null){
            Categories parentCategory =  categoriesRepository.findById(categoryRequestDTO.getParentCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent category not found"));
            categories.setParentCategory(parentCategory);
        } else  {
            categories.setParentCategory(null);
        }

        categoriesRepository.save(categories);
    }

    @Override
    public List<Categories> findAll() {
        return categoriesRepository.findAll();
    }

    @Override
    public Optional<Categories> findById(Long id) {
        return categoriesRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        if(!categoriesRepository.existsById(id)){
            throw new IllegalArgumentException("Category not Found");
        }
        categoriesRepository.deleteById(id);
    }


}
