package com.motorbit.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.motorbit.dto.request.LoginRequest;
import com.motorbit.dto.request.RegistroRequest;
import com.motorbit.dto.response.AuthResponse;
import com.motorbit.response.ApiResponse;
import com.motorbit.service.IAuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController 
@RequestMapping("/api/auth")
@RequiredArgsConstructor 
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<ApiResponse<AuthResponse>> registrar(@Valid @RequestBody RegistroRequest registroRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                            .body(ApiResponse.created("Usuario registrado correctamente.", 
                                                        authService.registrar(registroRequest)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>>  login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(ApiResponse.ok("Inicio de sesión exitoso.", 
                                            authService.login(loginRequest)));
    } 
}
