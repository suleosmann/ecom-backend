package dev.sule.ecommerce.repository;

import dev.sule.ecommerce.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    Optional<Categories> findByName(String name); // Specify the parameter type (String)
}
