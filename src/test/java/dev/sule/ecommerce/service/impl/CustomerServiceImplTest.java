package dev.sule.ecommerce.service.impl;

import dev.sule.ecommerce.dto.CustomerDTO;
import dev.sule.ecommerce.model.Customer;
import dev.sule.ecommerce.repository.CustomerRepository;
import dev.sule.ecommerce.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    private CustomerService customerService;
    private CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        customerRepository = mock(CustomerRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);

        // Inject mocks into the service
        customerService = new CustomerServiceImpl(customerRepository, passwordEncoder);
    }

    @Test
    void createCustomer_SuccessfulCreation() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("John Doe");
        customerDTO.setEmail("johndoe@example.com");
        customerDTO.setPassword("password123");
        customerDTO.setPhoneNumber("+1234567890");
        customerDTO.setAddress("123 Main Street");
        customerDTO.setCity("Springfield");

        when(customerRepository.existsByEmail(customerDTO.getEmail())).thenReturn(false); // Email doesn't exist
        when(passwordEncoder.encode(customerDTO.getPassword())).thenReturn("hashedPassword"); // Mock password hashing
        when(customerRepository.save(any(Customer.class))).thenReturn(new Customer()); // Mock save behavior

        // Act
        customerService.createCustomer(customerDTO);

        // Assert
        verify(customerRepository, times(1)).save(any(Customer.class)); // Ensure save was called once
        verify(passwordEncoder, times(1)).encode(customerDTO.getPassword()); // Ensure password was encoded
    }

    @Test
    void createCustomer_EmailAlreadyExists_ThrowsException() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmail("johndoe@example.com");

        when(customerRepository.existsByEmail(customerDTO.getEmail())).thenReturn(true); // Email already exists

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(customerDTO), "Email is already registered");

        // Verify that save was never called
        verify(customerRepository, never()).save(any(Customer.class));
    }
}
