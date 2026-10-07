package com.motorbit.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.motorbit.dto.request.LoginRequest;
import com.motorbit.dto.request.RegistroRequest;
import com.motorbit.dto.response.AuthResponse;
import com.motorbit.exception.RecursoExistenteException;
import com.motorbit.mapper.AuthMapper;
import com.motorbit.model.Usuario;
import com.motorbit.repository.UsuarioRepository;
import com.motorbit.security.CustomUserDetails;
import com.motorbit.security.JwtService;
import com.motorbit.service.IAuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Service 
@RequiredArgsConstructor 
public class AuthServiceImpl implements IAuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthMapper authMapper;

    @Override
    public AuthResponse registrar(RegistroRequest registroRequest) {
        String username = registroRequest.username();

        log.debug("Iniciando registro de usuario con username {}", username);
        
        if (usuarioRepository.existsByUsername(username)) {
            throw new RecursoExistenteException("Usuario", "username", username);
        }
    
        Usuario nuevoUsuario = authMapper.toEntity(registroRequest);

        nuevoUsuario.setPassword(passwordEncoder.encode(registroRequest.password()));

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        log.info(
                "Usuario registrado correctamente con id {} y username {}",
                usuarioGuardado.getId(),
                usuarioGuardado.getUsername()
        );

        CustomUserDetails userDetails = CustomUserDetails.createFromUsuario(usuarioGuardado);

        String token = jwtService.generateToken(userDetails);
        
        return authMapper.toResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        String username = loginRequest.username();

        log.debug("Iniciando autenticación para usuario {}", username);

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.username(),
             loginRequest.password()
        ));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        log.info(
                "Inicio de sesión exitoso para usuario {}",
                userDetails.getUsername()
        );
        
        String token = jwtService.generateToken(userDetails);

        return authMapper.toResponse(token);
    }

}
