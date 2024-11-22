package dev.sule.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"name", "email", "password", "phoneNumber", "address", "city"})
public class CustomerDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @JsonPropertyOrder("name")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    @JsonProperty("password")
    private String password;


    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid phone number format")
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    @JsonProperty("address")
    private String address;

    @Size(max = 100, message = "City must not exceed 100 characters")
    @JsonProperty("city")
    private String city;
}
