package dev.sule.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"name", "description", "parentCategoryId"})
public class CategoryRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    private String name;

    @Size(max = 255, message = "Description must not exceed 255 characters") // Optional description
    private String description;

    private Long parentCategoryId; // Optional parent category
}
