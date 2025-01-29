package org.example.ecommerce.infra.security;

import org.example.ecommerce.dto.request.LoginRequestDTO;
import org.example.ecommerce.dto.request.RegisterRequestDTO;
import org.example.ecommerce.dto.response.RegisterResponseDTO;
import org.example.ecommerce.infra.config.exception.UsernameIsNotAvailableException;
import org.example.ecommerce.model.User;
import org.example.ecommerce.reporitory.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {


    @InjectMocks
    AuthService authService;

    @Mock
    UserRepository userRepository;

    @Mock
    RegisterRequestDTO registerRequestDTO;

    @Mock
    TokenService tokenService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    LoginRequestDTO loginRequestDTO;

    User savedUser;

    @BeforeEach
    public void setUp() {

    registerRequestDTO = new RegisterRequestDTO("username1", "password1", UserRole.ADMIN);
    loginRequestDTO = new LoginRequestDTO("username1", "password1");

    savedUser = new User();
    savedUser.setUsername("username1");
    savedUser.setRole(UserRole.ADMIN);

    lenient().when(userRepository.save(any(User.class))).thenReturn(savedUser);
    }

    @Test
    @DisplayName("This test should register a user")
    void shouldRegisterAUser() {
        RegisterResponseDTO result = authService.register(registerRequestDTO);

        assertEquals(result.getUsername(), savedUser.getUsername());
        assertEquals(result.getRole(), savedUser.getRole());
    }

    @Test
    @DisplayName("This test should throws an exception when the username isn't null")
    void shouldThrowsAnExceptionWhenTheUsernameIsNotNullInRegister() {
        when(userRepository.findByUsername(registerRequestDTO.getUsername())).thenReturn(savedUser);

        assertEquals("username1", registerRequestDTO.getUsername());
        assertThrows(UsernameIsNotAvailableException.class, () -> authService.register(registerRequestDTO));
    }

}
