package org.example.ecommerce.dto.request;

import lombok.Data;
import org.example.ecommerce.infra.security.UserRole;

@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private UserRole role;
}
