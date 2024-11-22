package dev.sule.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponseDTO {
    private Long id;          // Unique identifier for the customer
    private String name;      // Customer's name
    private String email;     // Customer's email
    private String phoneNumber; // Customer's phone number
    private String address;   // Customer's address
    private String city;      // Customer's city
    private Boolean isActive; // Indicates if the customer is active
}
