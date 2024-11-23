package dev.sule.ecommerce.controller;

import dev.sule.ecommerce.dto.OrderRequestDTO;
import dev.sule.ecommerce.dto.OrderResponseDTO;
import dev.sule.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Create a new order based on the cart.
     *
     * @param orderRequestDTO the order request containing cartCode, customerId, and other details.
     * @return the created order as a response DTO.
     */
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO createdOrder = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.ok(createdOrder);
    }

    /**
     * Get all orders for a specific customer.
     *
     * @param customerId the ID of the customer.
     * @return a list of orders for the given customer.
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByCustomer(@PathVariable Long customerId) {
        List<OrderResponseDTO> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get details of a specific order by its order code.
     *
     * @param orderCode the unique code of the order.
     * @return the details of the specified order.
     */
    @GetMapping("/{orderCode}")
    public ResponseEntity<OrderResponseDTO> getOrderByCode(@PathVariable String orderCode) {
        OrderResponseDTO order = orderService.getOrderByCode(orderCode);
        return ResponseEntity.ok(order);
    }
}