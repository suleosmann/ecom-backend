package dev.sule.ecommerce.service;

import dev.sule.ecommerce.dto.CustomerDTO;
import dev.sule.ecommerce.dto.CustomerUpdateDTO;
import dev.sule.ecommerce.dto.LoginRequestDTO;
import dev.sule.ecommerce.dto.LoginResponseDTO;
import dev.sule.ecommerce.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    void createCustomer(CustomerDTO customerDTO);
    List<Customer> findAll();
    Optional<Customer> findById(Long id);
    void updateCustomer(Long id, CustomerUpdateDTO customerUpdateDTO);
    void deleteById(Long id);
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
    void changePassword(Long id, String oldPassword, String newPassword);

}
