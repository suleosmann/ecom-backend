package dev.sule.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"cartCode", "productId", "quantity"})
public class CartRequestDTO {

    @NotBlank(message = "Cart code is required")
    @JsonProperty("cartCode")
    private String cartCode;

    @NotNull(message = "Product ID is required")
    @JsonProperty("productId")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @JsonProperty("quantity")
    private Integer quantity;
}
