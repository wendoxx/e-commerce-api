package org.example.ecommerce.infra.security;

import org.example.ecommerce.dto.request.LoginRequestDTO;
import org.example.ecommerce.dto.request.RegisterRequestDTO;
import org.example.ecommerce.dto.response.LoginResponseDTO;
import org.example.ecommerce.dto.response.RegisterResponseDTO;
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

    public LoginResponseDTO login (LoginRequestDTO loginRequestDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = this.tokenService.generateToken((User) auth.getPrincipal());

        return new LoginResponseDTO(token);
    }

    public RegisterResponseDTO register (RegisterRequestDTO registerRequestDTO) {
        if (this.userRepository.findByUsername(registerRequestDTO.getUsername()) != null) {
            throw new RuntimeException("This username isn't available.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequestDTO.getPassword());
        User user = new User(registerRequestDTO.getUsername(), encryptedPassword, registerRequestDTO.getRole());

        User savedUser = this.userRepository.save(user);

        return new RegisterResponseDTO(savedUser.getUsername(), savedUser.getRole());
    }
}