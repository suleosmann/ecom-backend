package dev.sule.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartResponseDTO {
    private String cartCode;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private Double totalPrice;


}
