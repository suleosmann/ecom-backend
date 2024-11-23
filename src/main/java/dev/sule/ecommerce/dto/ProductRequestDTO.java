package dev.sule.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@JsonPropertyOrder({
        "name",
        "description",
        "price",
        "discount_price",
        "quantity",
        "remaining_quantity",
        "slug",
        "status",
        "category_id",
        "images"
})
public class ProductRequestDTO {
    @NotBlank(message = "Product name is required")
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("discount_price")
    private BigDecimal discountPrice;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than 0")
    @JsonProperty("quantity")
    private Long quantity;

    @NotNull(message = "Remaining quantity is required")
    @Positive(message = "Remaining quantity must be greater than 0")
    @JsonProperty("remaining_quantity")
    private Long remainingQuantity;

    @NotBlank(message = "Slug is required")
    @JsonProperty("slug")
    private String slug;

    @NotNull(message = "Status is required")
    @JsonProperty("status")
    private String status;

    @NotNull(message = "Category ID is required")
    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("images")
    private List<MultipartFile> images; // Accept uploaded files
}
