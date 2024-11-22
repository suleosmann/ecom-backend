package dev.sule.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseDTO {

    private Long id; // Unique ID of the category

    private String name; // Name of the category

    private String description; // Description of the category

    private CategoryResponseDTO parentCategory; // Parent category (nested for simplicity)
}
