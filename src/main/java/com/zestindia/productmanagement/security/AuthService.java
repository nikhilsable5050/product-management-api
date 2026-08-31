package com.zestindia.productmanagement.security;

import com.zestindia.productmanagement.dto.AuthResponse;
import com.zestindia.productmanagement.dto.LoginRequest;
import com.zestindia.productmanagement.entity.RefreshToken;
import com.zestindia.productmanagement.entity.Role;
import com.zestindia.productmanagement.entity.User;
import com.zestindia.productmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public void register(String username, String password) {

        User user = new User();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid username or password");
        }

        String accessToken =
                jwtService.generateToken(user.getUsername());

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        return new AuthResponse(
                accessToken,
                refreshToken.getToken()
        );
    }

    public AuthResponse refreshToken(String token) {

        RefreshToken refreshToken =
                refreshTokenService.findByToken(token);

        if (!refreshTokenService.isValid(refreshToken)) {
            throw new RuntimeException("Refresh token is expired or revoked");
        }

        User user = refreshToken.getUser();

        String accessToken =
                jwtService.generateToken(user.getUsername());

        refreshTokenService.revokeToken(refreshToken);

        RefreshToken newRefreshToken =
                refreshTokenService.createRefreshToken(user);

        return new AuthResponse(
                accessToken,
                newRefreshToken.getToken()
        );
    }
}