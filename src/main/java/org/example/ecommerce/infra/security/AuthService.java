package org.example.ecommerce.infra.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ecommerce.dto.request.LoginRequestDTO;
import org.example.ecommerce.dto.request.RegisterRequestDTO;
import org.example.ecommerce.dto.response.LoginResponseDTO;
import org.example.ecommerce.dto.response.RegisterResponseDTO;
import org.example.ecommerce.infra.config.exception.UsernameIsNotAvailableException;
import org.example.ecommerce.reporitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.example.ecommerce.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    private final Logger LOGGER = LogManager.getLogger();

    public LoginResponseDTO login (LoginRequestDTO loginRequestDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = this.tokenService.generateToken((User) auth.getPrincipal());
        LOGGER.info("Logging user...");
        return new LoginResponseDTO(token);
    }

    public RegisterResponseDTO register (RegisterRequestDTO registerRequestDTO) {
        if (this.userRepository.findByUsername(registerRequestDTO.getUsername()) != null) {
            LOGGER.error("This username isn't available.");
            throw new UsernameIsNotAvailableException("This username isn't available.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequestDTO.getPassword());
        User user = new User(registerRequestDTO.getUsername(), encryptedPassword, registerRequestDTO.getRole());

        User savedUser = this.userRepository.save(user);
        LOGGER.info("Registering user...");
        return new RegisterResponseDTO(savedUser.getUsername(), savedUser.getRole());
    }
}