package dev.sule.ecommerce.service.impl;

import dev.sule.ecommerce.dto.OrderItemResponseDTO;
import dev.sule.ecommerce.dto.OrderRequestDTO;
import dev.sule.ecommerce.dto.OrderResponseDTO;
import dev.sule.ecommerce.model.Cart;
import dev.sule.ecommerce.model.Customer;
import dev.sule.ecommerce.model.Order;
import dev.sule.ecommerce.model.OrderItem;
import dev.sule.ecommerce.repository.CartRepository;
import dev.sule.ecommerce.repository.CustomerRepository;
import dev.sule.ecommerce.repository.OrderRepository;
import dev.sule.ecommerce.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            CustomerRepository customerRepository,
                            CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        // Validate customer if provided
        Customer customer = null;
        if (orderRequestDTO.getCustomerId() != null) {
            customer = customerRepository.findById(orderRequestDTO.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        }

        // Retrieve all cart items for the cart code
        List<Cart> cartItems = cartRepository.findAllByCartCode(orderRequestDTO.getCartCode());
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty. Cannot create an order.");
        }

        // Use an array to calculate total amount (mutable state)
        final double[] totalAmount = {0.0};

        // Map cart items to order items and calculate total amount
        List<OrderItem> orderItems = cartItems.stream().map(cart -> {
            double itemTotal = cart.getQuantity() * cart.getProduct().getPrice().doubleValue();
            totalAmount[0] += itemTotal; // Accumulate the total amount

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cart.getProduct());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setPrice(cart.getProduct().getPrice().doubleValue());
            return orderItem;
        }).collect(Collectors.toList());

        // Create and save the order
        Order order = new Order();
        order.setOrderCode(UUID.randomUUID().toString());
        order.setCustomer(customer);
        order.setTotalAmount(totalAmount[0]); // Use accumulated total amount
        order.setStatus(Order.Status.PENDING);
        order.setItems(orderItems);
        order.setCreatedAt(LocalDateTime.now());

        // Link order items to the order
        orderItems.forEach(item -> item.setOrder(order));
        Order savedOrder = orderRepository.save(order);

        // Clear the cart
        cartRepository.deleteAll(cartItems);

        // Map the order to OrderResponseDTO and return
        return mapToOrderResponseDTO(savedOrder);
    }


    @Override
    public List<OrderResponseDTO> getOrdersByCustomerId(Long customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        return orders.stream()
                .map(this::mapToOrderResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO getOrderByCode(String orderCode) {
        Order order = orderRepository.findByOrderCode(orderCode);
        if (order == null) {
            throw new IllegalArgumentException("Order not found with code: " + orderCode);
        }
        return mapToOrderResponseDTO(order);
    }

    private OrderResponseDTO mapToOrderResponseDTO(Order order) {
        List<OrderItemResponseDTO> items = order.getItems().stream()
                .map(item -> new OrderItemResponseDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .collect(Collectors.toList());

        return new OrderResponseDTO(
                order.getOrderCode(),
                order.getCustomer() != null ? order.getCustomer().getId() : null,
                order.getTotalAmount(),
                order.getStatus().toString(),
                items,
                order.getCreatedAt()
        );
    }
}
