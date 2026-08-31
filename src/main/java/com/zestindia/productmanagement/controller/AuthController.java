package com.zestindia.productmanagement.controller;

import com.zestindia.productmanagement.dto.AuthResponse;
import com.zestindia.productmanagement.dto.LoginRequest;
import com.zestindia.productmanagement.security.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestParam String username,
            @RequestParam String password) {

        authService.register(username, password);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @RequestParam String refreshToken) {

        return ResponseEntity.ok(
                authService.refreshToken(refreshToken)
        );
    }
}