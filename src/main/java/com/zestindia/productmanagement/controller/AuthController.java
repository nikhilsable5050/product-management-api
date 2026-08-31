package com.zestindia.productmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.zestindia.productmanagement.dto.AuthResponse;
import com.zestindia.productmanagement.dto.LoginRequest;
import com.zestindia.productmanagement.security.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Authentication",
        description = "APIs for user registration, login and token refresh"
)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Register user",
            description = "Creates a new user account"
    )
    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestParam String username,
            @RequestParam String password) {

        authService.register(username, password);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Login",
            description = "Authenticates a user and returns access and refresh tokens"
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(
            summary = "Refresh token",
            description = "Generates a new access token using a refresh token"
    )
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @RequestParam String refreshToken) {

        return ResponseEntity.ok(
                authService.refreshToken(refreshToken)
        );
    }
}