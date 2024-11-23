package dev.sule.ecommerce.service;

import dev.sule.ecommerce.dto.OrderRequestDTO;
import dev.sule.ecommerce.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    /**
     * Create an order based on the given request.
     *
     * @param orderRequestDTO the order request containing cartCode and customer details.
     * @return the created order's details as a response DTO.
     */
    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO);

    /**
     * Retrieve all orders for a given customer.
     *
     * @param customerId the customer's ID.
     * @return a list of orders for the customer.
     */
    List<OrderResponseDTO> getOrdersByCustomerId(Long customerId);

    /**
     * Retrieve a specific order by its order code.
     *
     * @param orderCode the unique order code.
     * @return the order details as a response DTO.
     */
    OrderResponseDTO getOrderByCode(String orderCode);
}
