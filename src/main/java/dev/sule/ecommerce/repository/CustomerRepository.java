package dev.sule.ecommerce.repository;

import dev.sule.ecommerce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email); // Additional method to check if a user already exists
    Optional<Customer> findByEmail(String email);
}
