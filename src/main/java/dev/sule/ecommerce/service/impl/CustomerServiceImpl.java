package dev.sule.ecommerce.service.impl;

import dev.sule.ecommerce.dto.CustomerDTO;
import dev.sule.ecommerce.dto.CustomerUpdateDTO;
import dev.sule.ecommerce.dto.LoginRequestDTO;
import dev.sule.ecommerce.dto.LoginResponseDTO;
import dev.sule.ecommerce.exceptions.EmailAlreadyExistsException;
import dev.sule.ecommerce.model.Customer;
import dev.sule.ecommerce.repository.CustomerRepository;
import dev.sule.ecommerce.service.CustomerService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void createCustomer(CustomerDTO customerDTO) {

        if (customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }


        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setAddress(customerDTO.getAddress());
        customer.setCity(customerDTO.getCity());
        customer.setIsActive(true);

        customerRepository.save(customer);

    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public void updateCustomer(Long id, CustomerUpdateDTO customerUpdateDTO) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isEmpty()) {
            throw new IllegalArgumentException("Customer not found");
        }

        Customer customer = existingCustomer.get();

        // Only update allowed fields
        customer.setName(customerUpdateDTO.getName());
        customer.setPhoneNumber(customerUpdateDTO.getPhoneNumber());
        customer.setAddress(customerUpdateDTO.getAddress());
        customer.setCity(customerUpdateDTO.getCity());

        customerRepository.save(customer); // Save updated customer
    }


    @Override
    public void deleteById(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("Customer not found");
        }

        customerRepository.deleteById(id);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        // Find the customer by email
        Customer customer = customerRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        // Check if the provided password matches the hashed password
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), customer.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Build and return the response
        LoginResponseDTO response = new LoginResponseDTO();
        response.setMessage("Login successful");
        response.setCustomerId(customer.getId());
        response.setEmail(customer.getEmail());

        return response;
    }

    @Override
    public void changePassword(Long id, String oldPassword, String newPassword) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        if (!passwordEncoder.matches(oldPassword, customer.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        customer.setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(customer);
    }



}
