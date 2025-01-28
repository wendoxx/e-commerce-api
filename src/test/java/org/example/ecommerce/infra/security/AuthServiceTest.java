package org.example.ecommerce.infra.security;

import org.example.ecommerce.dto.request.RegisterRequestDTO;
import org.example.ecommerce.dto.response.RegisterResponseDTO;
import org.example.ecommerce.model.User;
import org.example.ecommerce.reporitory.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    //UUID userID = UUID.randomUUID();
    RegisterRequestDTO registerRequestDTO;

    User savedUser;

    @InjectMocks
    AuthService authService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {

    registerRequestDTO = new RegisterRequestDTO("username1", "password1", UserRole.ADMIN);

    savedUser = new User();
    savedUser.setUsername("username1");
    savedUser.setRole(UserRole.ADMIN);

    lenient().when(userRepository.save(any(User.class))).thenReturn(savedUser);
    }

    @Test
    void shouldRegisterAnUser() {
        RegisterResponseDTO result = authService.register(registerRequestDTO);

        assertEquals(result.getUsername(), savedUser.getUsername());
        assertEquals(result.getRole(), savedUser.getRole());
    }
}
