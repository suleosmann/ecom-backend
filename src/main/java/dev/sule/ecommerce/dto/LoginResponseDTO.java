package dev.sule.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

    private String message;
    private Long customerId;
    private String email;
}
