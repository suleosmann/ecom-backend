package dev.sule.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonPropertyOrder({"orderCode", "customerId", "totalAmount", "status", "items", "createdAt"})
public class OrderResponseDTO {

    @JsonProperty("orderCode")
    private String orderCode;

    @JsonProperty("customerId")
    private Long customerId;

    @JsonProperty("totalAmount")
    private Double totalAmount;

    @JsonProperty("status")
    private String status;

    @JsonProperty("items")
    private List<OrderItemResponseDTO> items;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;
}
