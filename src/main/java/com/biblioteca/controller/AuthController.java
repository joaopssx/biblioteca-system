package com.biblioteca.controller;

import com.biblioteca.config.JwtUtil;
import com.biblioteca.dto.request.LoginRequest;
import com.biblioteca.dto.response.ApiResponse;
import com.biblioteca.dto.response.LoginResponse;
import com.biblioteca.model.Bibliotecario;
import com.biblioteca.repository.BibliotecarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final BibliotecarioRepository bibliotecarioRepository;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getSenha())
        );

        Bibliotecario bibliotecario = bibliotecarioRepository.findByUsername(request.getUsername())
                .orElseThrow();

        String token = jwtUtil.generateToken(bibliotecario.getUsername());

        LoginResponse response = LoginResponse.builder()
                .token(token)
                .username(bibliotecario.getUsername())
                .nome(bibliotecario.getNome())
                .build();

        return ApiResponse.ok(response);
    }
}
