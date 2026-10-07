package com.motorbit.service;

import com.motorbit.dto.request.LoginRequest;
import com.motorbit.dto.request.RegistroRequest;
import com.motorbit.dto.response.AuthResponse;

public interface IAuthService {
    AuthResponse registrar(RegistroRequest registroRequest);
    AuthResponse login(LoginRequest loginRequest);
}
