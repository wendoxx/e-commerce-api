package org.example.ecommerce.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.ecommerce.dto.request.LoginRequestDTO;
import org.example.ecommerce.dto.request.RegisterRequestDTO;
import org.example.ecommerce.dto.response.LoginResponseDTO;
import org.example.ecommerce.dto.response.RegisterResponseDTO;
import org.example.ecommerce.infra.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Auth Controller", description = "This controller is responsible for handling auth related operations.")
public class AuthController {

    @Autowired
    AuthService authenticationService;

    @Operation(
            method = "POST",
            summary = "Log in a user",
            description = "This endpoint logs a user"
    )
    @ApiResponse(responseCode = "200", description = "Logged successfully")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login (@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authenticationService.login(loginRequestDTO));
    }

    @Operation(
            method = "POST",
            summary = "Register a user",
            description = "This endpoint register a user"
    )
    @ApiResponse(responseCode = "201", description = "Registered successfully")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> login (@RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok(authenticationService.register(registerRequestDTO));
    }
}
