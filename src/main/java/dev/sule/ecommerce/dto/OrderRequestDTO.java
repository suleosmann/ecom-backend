package dev.sule.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonPropertyOrder({"cartCode", "customerId", "shippingAddress", "paymentMethod"})
public class OrderRequestDTO {

    @NotBlank(message = "Cart code is required")
    @JsonProperty("cartCode")
    private String cartCode;

    @JsonProperty("customerId")
    private Long customerId; // Null for guest users

    @NotBlank(message = "Shipping address is required")
    @JsonProperty("shippingAddress")
    private String shippingAddress;

    @NotBlank(message = "Payment method is required")
    @JsonProperty("paymentMethod")
    private String paymentMethod; // e.g., CARD, PAYPAL, etc.
}
