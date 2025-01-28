package org.example.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.ecommerce.infra.security.UserRole;

@AllArgsConstructor
@Data
public class RegisterRequestDTO {
    private String username;
    private String password;
    private UserRole role;
}
