package dev.sule.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String price;
    private String discountPrice;
    private Long quantity;
    private Long remainingQuantity;
    private String slug;
    private String status;
    private Long categoryId;
    private List<String> imageUrls;
}
