package dev.sule.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"name", "phoneNumber", "address", "city"})
public class CustomerUpdateDTO {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @JsonPropertyOrder("name")
    private String name;

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
