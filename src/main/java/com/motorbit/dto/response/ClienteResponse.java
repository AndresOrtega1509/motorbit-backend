package com.motorbit.dto.response;

public record ClienteResponse(
        Long id,
        String nombre,
        String cedula,
        String telefono,
        String email
) {}